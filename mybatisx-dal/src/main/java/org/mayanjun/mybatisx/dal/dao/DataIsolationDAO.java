package org.mayanjun.mybatisx.dal.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.query.EquivalentComparator;
import org.mayanjun.mybatisx.api.query.Query;
import org.mayanjun.mybatisx.dal.Assert;
import org.mayanjun.mybatisx.dal.Sharding;
import org.mayanjun.mybatisx.dal.generator.AnnotationHelper;
import org.mayanjun.mybatisx.dal.generator.AnnotationHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataIsolationDAO extends BasicDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DataIsolationDAO.class);

    private String isolationField;
    private DataIsolationValueProvider valueProvider;

    public DataIsolationDAO(String isolationField, DataIsolationValueProvider valueProvider) {
        Assert.notBlank(isolationField, "isolationField can not be blank");
        this.isolationField = isolationField;
        this.valueProvider = valueProvider;
    }

    private void isolate(Query<?> query) {
        Object value = valueProvider.value();
        if (value == null) return;

        AnnotationHolder holder = AnnotationHelper.getAnnotationHolder(isolationField, query.getBeanType());
        if (holder != null) {
            query.addComparator(new EquivalentComparator(isolationField, value));
        }
    }

    private void setIsolationValue(Entity entity) {
        if (entity == null) return;
        Object value = valueProvider.value();
        if (value == null) return;

        AnnotationHolder holder = AnnotationHelper.getAnnotationHolder(isolationField, entity.getClass());
        if (holder != null) {
            try {
                BeanUtils.setProperty(entity, isolationField, value);
            } catch (Exception e) {
                LOG.error("Can't set isolation field value:" + entity, e);
            }
        }
    }

    @Override
    public <T extends Entity> List<T> query(Query<T> query, Sharding sharding) {
        isolate(query);
        return super.query(query, sharding);
    }

    @Override
    public long count(Query<?> query, Sharding sharding) {
        isolate(query);
        return super.count(query, sharding);
    }

    @Override
    public int update(Entity bean, Sharding sharding, Query<? extends Entity> query) {
        isolate(query);
        return super.update(bean, sharding, query);
    }

    @Override
    public int delete(Query<? extends Entity> query, Sharding sharding) {
        isolate(query);
        return super.delete(query, sharding);
    }

    @Override
    public int update(Entity bean, Sharding sharding) {
        setIsolationValue(bean);
        return super.update(bean, sharding);
    }

    @Override
    public int save(Entity bean, Sharding sharding, boolean isAutoIncrementId) {
        setIsolationValue(bean);
        return super.save(bean, sharding, isAutoIncrementId);
    }

    @Override
    public int saveOrUpdate(Entity bean, Sharding sharding, boolean isAutoIncrementId) {
        setIsolationValue(bean);
        return super.saveOrUpdate(bean, sharding, isAutoIncrementId);
    }
}
