/*
 * Copyright 2016-2018 mayanjun.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mayanjun.mybatisx.dal.dao;

import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.annotation.Annotation;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.ognl.MemberAccess;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.session.SqlSession;
import org.mayanjun.mybatisx.api.annotation.Index;
import org.mayanjun.mybatisx.api.annotation.IndexColumn;
import org.mayanjun.mybatisx.api.annotation.Table;
import org.mayanjun.mybatisx.api.entity.DeletableEntity;
import org.mayanjun.mybatisx.api.entity.EditableEntity;
import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.enums.IndexType;
import org.mayanjun.mybatisx.api.enums.QueryDeletedMode;
import org.mayanjun.mybatisx.api.query.EquivalentComparator;
import org.mayanjun.mybatisx.api.query.LogicalOperator;
import org.mayanjun.mybatisx.api.query.Query;
import org.mayanjun.mybatisx.api.query.QueryBuilder;
import org.mayanjun.mybatisx.dal.Assert;
import org.mayanjun.mybatisx.dal.Sharding;
import org.mayanjun.mybatisx.dal.ShardingEntityAccessor;
import org.mayanjun.mybatisx.dal.generator.AnnotationHelper;
import org.mayanjun.mybatisx.dal.generator.AnnotationHolder;
import org.mayanjun.mybatisx.dal.parser.PreparedQueryParser;
import org.mayanjun.mybatisx.dal.parser.QueryParser;
import org.mayanjun.mybatisx.dal.parser.SQLParameter;
import org.mayanjun.mybatisx.dal.sharding.StaticSharding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A default implementation of DAO
 * @author mayanjun(6/24/16)
 */
public abstract class DynamicDAO implements DataBaseRouteAccessor, ShardingEntityAccessor, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicDAO.class);
    private static final MemberAccess OGNL_MEMBER_ACCESS = new DefaultOgnlMemberAccess();

    private DatabaseRouter router;
    private Map<Class<?>, Class<?>> entityMapperClassesCache = new ConcurrentHashMap<Class<?>, Class<?>>();

    private QueryParser parser = new PreparedQueryParser(DynamicMapper.PARAM_NAME);
    private BeanUpdatePostHandler beanUpdatePostHandler;

    public void setBeanUpdatePostHandler(BeanUpdatePostHandler beanUpdatePostHandler) {
        this.beanUpdatePostHandler = beanUpdatePostHandler;
    }

    /**
     * 执行主数据库事务
     * @param action
     * @param <T>
     * @return
     */
    public <T> T executeTransaction(TransactionCallback<T> action) {
        return databaseRouter().getDatabaseSession(defaultSharding(), null).transaction().execute(action);
    }

    /**
     * 执行主数据库事务
     * @param action
     * @param <T>
     * @return
     */
    public <T> T executeTransaction(String datasourceName, TransactionCallback<T> action) {
        DatabaseSession session = databaseRouter().getDatabaseSession(datasourceName);
        if (session != null) {
            return session.transaction().execute(action);
        } else {
            LOG.warn("Can't execute database transaction: no database session found with name '{}'", datasourceName);
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(router, "router must be set");
    }

    public void setRouter(DatabaseRouter router) {
        this.router = router;
    }

    @Override
    public <T extends Entity> List<T> query(Query<T> query) {
        return query(query, defaultSharding());
    }

    @Override
    public <T extends Entity> T queryOne(Query<T> query) {
        return queryOne(query, defaultSharding());
    }

    @Override
    public int update(Entity bean) {
        return update(bean, defaultSharding());
    }

    @Override
    public int update(Entity bean, Query<? extends Entity> query) {
        return update(bean, defaultSharding(), query);
    }

    @Override
    public int save(Entity bean, boolean isAutoIncrementId) {
        return save(bean, defaultSharding(), isAutoIncrementId);
    }

    @Override
    public int save(Entity bean) {
        return save(bean, defaultSharding());
    }

    @Override
    public int saveOrUpdate(Entity bean) {
        return saveOrUpdate(bean, defaultSharding());
    }

    @Override
    public int saveOrUpdate(Entity bean, boolean isAutoIncrementId) {
        return saveOrUpdate(bean);
    }

    @Override
    public int delete(Entity bean) {
        return delete(bean, defaultSharding());
    }

    @Override
    public int delete(Query<? extends Entity> query) {
        return delete(query, defaultSharding());
    }


    @Override
    public <T extends Entity> T getExclude(Entity bean, String... excludeFields) {
        return getExclude(bean, defaultSharding(), excludeFields);
    }

    @Override
    public <T extends Entity> T getInclude(Entity bean, String... includeFields) {
        return getInclude(bean, defaultSharding(), includeFields);
    }

    @Override
    public <T extends Entity> T getExclude(Entity bean, boolean forUpdate, String... excludeFields) {
        return getExclude(bean, defaultSharding(), false, excludeFields);
    }

    @Override
    public <T extends Entity> T getInclude(Entity bean, boolean forUpdate, String... includeFields) {
        return getInclude(bean, defaultSharding(), false, includeFields);
    }


    @Override
    public <T extends Entity> List<T> query(Query<T> query, Sharding sharding) {
        setDeletableQuery(query);
        SQLParameter<T> parameter = parser.parse(query);
        SqlSession sqlSession = getSqlSession(sharding, parameter).sqlSession();
        DynamicMapper<T> mapper = (DynamicMapper<T>) getMapper(query.getBeanType(), sqlSession);
        return mapper.query(parameter, sharding);
    }

    @Override
    public long count(Query<?> query, Sharding sharding) {
        setDeletableQuery(query);
        SQLParameter<?> parameter = parser.parse(query);
        SqlSession sqlSession = getSqlSession(sharding, parameter).sqlSession();
        DynamicMapper<?> mapper = getMapper(query.getBeanType(), sqlSession);
        return mapper.count(parameter, sharding);
    }

    @Override
    public long count(Query<?> query) {
        return count(query, defaultSharding());
    }

    @Override
    public <T extends Entity> T queryOne(Query<T> query, Sharding sharding) {
        //setDeletableQuery(query);
        if(query.getLimit() > 1) query.setLimit(1);
        List<T> list = query(query, sharding);
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Set query
     * @param query
     */
    private  void setDeletableQuery(Query<?> query) {
        Class<?> beanType = query.getBeanType();
        if(DeletableEntity.class.isAssignableFrom(beanType)) {
            QueryDeletedMode mode = query.getQueryDeletedMode();
            if (mode == null) {
                query.addComparator(new EquivalentComparator("deleted", false, LogicalOperator.AND));
                return;
            }

            switch (mode) {
                case ONLY_DELETED:
                    query.addComparator(new EquivalentComparator("deleted", true, LogicalOperator.AND));
                    break;
                case WITHOUT_DELETED:
                    query.addComparator(new EquivalentComparator("deleted", false, LogicalOperator.AND));
                    break;
                default:
                    break;
            }
        }
    }

    private DatabaseSession getSqlSession(Sharding sharding, Object source) {
        //SqlSession sqlSession = getDataBaseRouter().getMasterDataBaseSqlSession(); // forced to use MASTER DB
        if (sharding == null) {
            sharding = defaultSharding();
        }
        DatabaseSession databaseSession = databaseRouter().getDatabaseSession(sharding, source);
        Assert.notNull(databaseSession, "Can not get SqlSession");
        return databaseSession;
    }

    @Override
    public int update(Entity bean, Sharding sharding) {
        if (bean instanceof EditableEntity) {
            ((EditableEntity) bean).setCreatedTime(null);
            ((EditableEntity) bean).setModifiedTime(new Date());
        }
        SqlSession sqlSession = getSqlSession(sharding, bean).sqlSession();
        DynamicMapper<Entity> mapper = getMapper(bean.getClass(), sqlSession);

        int ret = mapper.update(bean, sharding);
        executePostUpdate(ret, bean);
        return ret;
    }

    private void executePostUpdate(int ret, Entity entity) {
        if (entity == null) return;
        if (ret > 0) {
            try {
                this.beanUpdatePostHandler.postUpdate(entity);
            } catch (Throwable e) {
                LOG.warn(
                        "Execute BeanUpdatePostHandler.postUpdate() error: class=" + entity.getClass() + ", id=" + entity.getId(),
                        e
                );
            }
        }
    }

    @Override
    public int update(Entity bean, Sharding sharding, Query<? extends Entity> query) {
        if (bean instanceof EditableEntity) {
            ((EditableEntity) bean).setCreatedTime(null);
            ((EditableEntity) bean).setModifiedTime(new Date());
        }
        SQLParameter<Entity> parameter = (SQLParameter<Entity>)parser.parse(query);
        parameter.setEntity(bean);
        SqlSession sqlSession = getSqlSession(sharding, bean).sqlSession();
        DynamicMapper<Entity> mapper = getMapper(bean.getClass(), sqlSession);
        int ret = mapper.queryUpdate(parameter, sharding);
        executePostUpdate(ret, bean);
        return ret;
    }

    @Override
    public int save(Entity bean, Sharding sharding) {
        return save(bean, sharding, isAutoIncrement(bean));
    }

    /**
     * 确定实体是不是自增ID
     * @param bean
     * @return
     */
    private boolean isAutoIncrement(Entity bean) {
        Table table = bean.getClass().getAnnotation(Table.class);
        Assert.notNull(table, "No @Table annotation found");
        return table.autoIncrement() > -1;
    }

    @Override
    public int save(Entity bean, Sharding sharding, boolean isAutoIncrementId) {
        if (bean instanceof EditableEntity) {
            Date now = new Date();
            EditableEntity eb = (EditableEntity) bean;
            if(eb.getCreatedTime() == null) eb.setCreatedTime(now);
            if(eb.getModifiedTime() == null) eb.setModifiedTime(now);
        }

        DatabaseSession session = getSqlSession(sharding, bean);

        Serializable id = bean.getId();

        if (id == null) { // id为null时，需要考虑ID的生成方式
            if (!isAutoIncrementId) {
                bean.setId(session.idGenerator().next());
            }
        }

        DynamicMapper<Entity> mapper = getMapper(bean.getClass(), session.sqlSession());

        return mapper.insert(bean, sharding);
    }

    @Override
    public int saveOrUpdate(final Entity bean, final Sharding sharding) {
        return saveOrUpdate(bean, sharding, isAutoIncrement(bean));
    }

    @Override
    public int saveOrUpdate(final Entity bean, final Sharding sharding, final boolean isAutoIncrementId) {
        Serializable originId = bean.getId();
        try {
            return save(bean, sharding, isAutoIncrementId);
        } catch (DuplicateKeyException e) {
            try {
                bean.setId(originId);
                final Query<Entity> query = createUniqueQuery(bean);
                if (query == null) throw new IllegalArgumentException("Can not create query for unique-query: bean=" + bean);

                TransactionTemplate transactionTemplate = databaseRouter().getDatabaseSession(sharding, bean).transaction();
                if (transactionTemplate != null) {
                    return transactionTemplate.execute(new TransactionCallback<Integer>() {
                        @Override
                        public Integer doInTransaction(TransactionStatus transactionStatus) {
                            query.setForUpdate(true);
                            return saveOrUpdateUpdate(query, bean, sharding);
                        }
                    });
                } else {
                    // UNSAFE 有可能会导致更新丢失问题
                    return saveOrUpdateUpdate(query, bean, sharding);
                }
            } catch (Exception e1) {
                throw new IllegalArgumentException(e1);
            }
        }
    }

    private int saveOrUpdateUpdate(Query<Entity> query, Entity bean, Sharding sharding) {
        Entity b = queryOne(query, sharding);
        if (b != null) {
            bean.setId(b.getId());
            return update(bean, sharding);
        } else {
            throw new IllegalArgumentException("Can not update bean for unique-query: bean=" + bean + ", id=" + bean.getId());
        }
    }


    public <T extends Entity> Query<T> createUniqueQuery(T bean) throws Exception {
        Class<T> c = (Class<T>)  bean.getClass();
        QueryBuilder<T> builder = QueryBuilder.custom(c);
        boolean valueSet = false;

        if (bean.getId() != null) {
            valueSet = true;
            builder.andEquivalent("id", bean.getId());
        }

        Table table = c.getAnnotation(Table.class);
        Index indexes[] = table.indexes();
        for (Index index : indexes) {
            IndexType type = index.type();
            if (type == IndexType.UNIQUE) {
                IndexColumn columns[] = index.columns();
                for (IndexColumn ic : columns) {
                    String name = ic.value();
                    AnnotationHolder holder = AnnotationHelper.getAnnotationHolder(name, bean.getClass());
                    if (holder != null && holder.getColumn() != null) {
                        String ref = holder.getColumn().referenceField();
                        if (StringUtils.isNotBlank(ref)) {
                            name += "." + ref;
                        }
                    }

                    //Object value = BeanUtilsBean2.getInstance().getPropertyUtils().getProperty(bean, ic.value());
                    Object value = null;
                    try {
                        OgnlContext context = new OgnlContext(null, null, OGNL_MEMBER_ACCESS);
                        value = Ognl.getValue(name, context, bean);
                    } catch (Exception e) {}

                    if (value != null) {
                        valueSet = true;
                        builder.andEquivalent(name, value);
                    }
                }
            }
        }
        if (valueSet) return builder.build();
        return null;
    }

    @Override
    public int delete(Entity bean, Sharding sharding) {
        SqlSession sqlSession = getSqlSession(sharding, bean).sqlSession();
        DynamicMapper<Entity> mapper = getMapper(bean.getClass(), sqlSession);
        if(bean instanceof DeletableEntity) {
            // logical delete
            try {
                Serializable id = bean.getId();
                DeletableEntity updateBean = (DeletableEntity)bean.getClass().getConstructor(id.getClass()).newInstance(id);
                updateBean.setDeleted(true);
                return mapper.update(updateBean, sharding);
            } catch (Exception e) {
                throw new RuntimeException("Can not create instance for class:" + bean.getClass(), e);
            }
        } else {
            return mapper.delete(bean, sharding);
        }
    }

    @Override
    public int delete(Query<? extends Entity> query, Sharding sharding) {
        Class<? extends Entity> beanType = query.getBeanType();
        if(DeletableEntity.class.isAssignableFrom(beanType)) { // process logical delete
            try {
                DeletableEntity updateBean = (DeletableEntity)beanType.newInstance();
                updateBean.setDeleted(true);
                return update(updateBean, query);
            } catch (Exception e) {
                throw new RuntimeException("Can not create instance for class:" + beanType, e);
            }
        } else {
            SQLParameter<Entity> parameter = (SQLParameter<Entity>)parser.parse(query);
            SqlSession sqlSession = getSqlSession(sharding, parameter).sqlSession();
            DynamicMapper<Entity> mapper = getMapper(beanType, sqlSession);
            return mapper.queryDelete(parameter, sharding);
        }

    }


    @Override
    public <T extends Entity> T getExclude(Entity bean, Sharding sharding, String... excludeFields) {
        return getExclude(bean, sharding, false, excludeFields);
    }

    @Override
    public <T extends Entity> T getInclude(Entity bean, Sharding sharding, String... includeFields) {
        return getInclude(bean, sharding, false, includeFields);
    }

    @Override
    public <T extends Entity> T getExclude(Entity bean, Sharding sharding, boolean forUpdate, String... excludeFields) {
        Query<T> query = QueryBuilder.custom((Class<T>) bean.getClass()).andEquivalent("id", bean.getId()).excludeFields(excludeFields).limit(1).build();
        query.setForUpdate(forUpdate);
        return queryOneInternal(bean, query, sharding);
    }

    @Override
    public <T extends Entity> T getInclude(Entity bean, Sharding sharding, boolean forUpdate, String... includeFields) {
        Query<T> query = QueryBuilder.custom((Class<T>) bean.getClass()).andEquivalent("id", bean.getId()).includeFields(includeFields).limit(1).build();
        query.setForUpdate(forUpdate);
        return queryOneInternal(bean, query, sharding);
    }

    private <T extends Entity> T queryOneInternal(Entity bean, Query<T> query, Sharding sharding) {
        StaticSharding staticSharding = null;
        if(sharding != null) staticSharding = new StaticSharding(sharding.getDatabaseName(bean), sharding.getTableName(bean));
        List<T> list = this.query(query, staticSharding);
        if (list != null && list.size() > 0) return list.get(0);
        return null;
    }


    public Class<?> createDynamicMapperClass(Class<?> beanType) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        ClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);
        CtClass superClass = pool.get(DynamicMapper.class.getName());
        CtClass ctClass = pool.makeInterface(beanType.getName() + "GeneratedMapper", superClass);

        SignatureAttribute.ClassSignature cs = new SignatureAttribute.ClassSignature(null, null,
                // Set interface and its generic params
                new SignatureAttribute.ClassType[]{new SignatureAttribute.ClassType(DynamicMapper.class.getName(),
                        new SignatureAttribute.TypeArgument[]{new SignatureAttribute.TypeArgument(new SignatureAttribute.ClassType(beanType.getName()))}
                )});
        ctClass.setGenericSignature(cs.encode());

        ClassFile ccFile = ctClass.getClassFile();
        ConstPool constPool = ccFile.getConstPool();
        // add annotation
        AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation mapperAnno = new Annotation(Mapper.class.getName(), constPool);
        bodyAttr.addAnnotation(mapperAnno);
        ccFile.addAttribute(bodyAttr);

        /*byte[] byteArr = ctClass.toBytecode();
        FileOutputStream fos = new FileOutputStream(new File("/Users/mayanjun/Desktop/StudentMapper.class"));
        fos.write(byteArr);
        fos.close();*/

        return ctClass.toClass();
    }


    public DynamicMapper<Entity> getMapper(Class<?> beanType, SqlSession sqlSession) {
        Class<?> mapperClass = entityMapperClassesCache.get(beanType);
        try {
            if (mapperClass == null) {
                synchronized (beanType) {
                    Class<?> mapperClassGen = entityMapperClassesCache.get(beanType);
                    if (mapperClassGen == null) {
                        mapperClassGen = createDynamicMapperClass(beanType);
                        // register to dao
                        mapperClass = mapperClassGen;
                        sqlSession.getConfiguration().addMapper(mapperClass);
                        entityMapperClassesCache.put(beanType, mapperClass);
                        LOG.info("Mapper class generated for class(double check) {} <===> {}", beanType, mapperClass);
                    } else {
                        mapperClass = mapperClassGen;
                    }
                }
            }
            Assert.notNull(mapperClass, "Mapper class not found");
            DynamicMapper<Entity> mapper = (DynamicMapper<Entity>) sqlSession.getMapper(mapperClass);
            return mapper;
        } catch (BindingException e) {
            try {
                if (mapperClass != null) sqlSession.getConfiguration().addMapper(mapperClass);
            } catch (Exception ex) {}

            DynamicMapper<Entity> mapper = (DynamicMapper<Entity>) sqlSession.getMapper(mapperClass);
            return mapper;
        } catch (Throwable e) {
            String message = "No mapper interface found for bean type " + beanType;
            RuntimeException exception = new RuntimeException(message, e);
            LOG.error(message, exception);
            throw exception;
        }
    }

    @Override
    public DatabaseRouter databaseRouter() {
        return router;
    }

    protected abstract Sharding defaultSharding();
}
