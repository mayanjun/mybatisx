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

package org.mayanjun.mybatisx.starter;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mayanjun.core.Assert;
import org.mayanjun.mybatisx.dal.IdGenerator;
import org.mayanjun.mybatisx.dal.converter.PropertiesFactoryBean;
import org.mayanjun.mybatisx.dal.dao.BasicDAO;
import org.mayanjun.mybatisx.dal.dao.DatabaseRouter;
import org.mayanjun.mybatisx.dal.dao.DatabaseSession;
import org.mayanjun.mybatisx.dal.dao.ThreadLocalDatabaseRouter;
import org.mayanjun.mybatisx.dal.generator.SnowflakeIDGenerator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicDaoFactoryBean implements FactoryBean<BasicDAO>, ApplicationRunner, ResourceLoaderAware {

    private static final Logger LOG = LoggerFactory.getLogger(BasicDaoFactoryBean.class);

    private MybatisxConfig config;
    private List<SqlSessionTemplate> sqlSessionTemplates = new ArrayList<SqlSessionTemplate>();

    private ResourceLoader resourceLoader;

    private Map<Class<? extends IdGenerator>, IdGenerator> reusableIdGenerators = new HashMap<Class<? extends IdGenerator>, IdGenerator>();
    private volatile IdGenerator defaultIdGenerator;

    public BasicDaoFactoryBean(@Autowired(required = false) MybatisxConfig config) {
        this.config = config;
    }

    /**
     *
     * @param dataSourceConfig
     * @return
     */
    private IdGenerator newIdGenerator(DataSourceConfig dataSourceConfig) {
        IdGenerator generator = null;
        String type = dataSourceConfig.getIdGeneratorType();

        if (StringUtils.isNotBlank(type)) {
            try {
                synchronized (this) {
                    Class<? extends IdGenerator> cls = (Class<? extends IdGenerator>) Class.forName(type);
                    generator = reusableIdGenerators.get(cls);
                    if (generator == null) {
                        generator = cls.newInstance();
                        reusableIdGenerators.put(cls, generator);
                    }
                }
            } catch (Exception e) {
                LOG.error("Can't Instantiate ID generator for class: " + type, e);
            }
        }

        if (generator == null) {
            if (defaultIdGenerator == null) {
                synchronized (this) {
                    defaultIdGenerator = new SnowflakeIDGenerator();
                }
            }
            generator = defaultIdGenerator;
        }
        return generator;
    }

    public DatabaseRouter newDataBaseRouter(MybatisxConfig config) throws Exception {
        List<DataSourceConfig> dataSourceConfigList = config.getDataSources();

        if (dataSourceConfigList == null || dataSourceConfigList.isEmpty()) {
            dataSourceConfigList = new ArrayList<DataSourceConfig>();
            dataSourceConfigList.add(new DataSourceConfig());
            config.setDataSources(dataSourceConfigList);
        }
        Assert.notEmpty(config.getDataSources(), "Datasource config(s) can not be empty");

        ThreadLocalDatabaseRouter databaseRouter = new ThreadLocalDatabaseRouter();

        for (DataSourceConfig dataSourceConfig : config.getDataSources()) {
            DataSource dataSource = createDataSource(dataSourceConfig);

            SqlSessionFactoryBean sqlSessionFactoryBean = createSqlSessionFactoryBean(dataSource, dataSourceConfig);
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

            // create sql session template
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
            sqlSessionTemplates.add(template);

            // create transactionTemplate
            DataSourceTransactionManager manager = new DataSourceTransactionManager();
            manager.setDataSource(dataSource);
            manager.afterPropertiesSet();

            TransactionTemplate transactionTemplate = new TransactionTemplate();
            transactionTemplate.setIsolationLevelName(dataSourceConfig.getIsolationLevelName());
            transactionTemplate.setPropagationBehaviorName(dataSourceConfig.getPropagationBehaviorName());
            transactionTemplate.setTransactionManager(manager);
            transactionTemplate.afterPropertiesSet();

            String databaseSessionName = dataSourceConfig.getName();
            Assert.notBlank(databaseSessionName, "The database session name can not be empty");

            DatabaseSession session = new DatabaseSession(dataSourceConfig.getName(), dataSource, template, newIdGenerator(dataSourceConfig), transactionTemplate);
            databaseRouter.addDatabaseSession(session);

        }
        databaseRouter.afterPropertiesSet();
        return databaseRouter;
    }

    private SqlSessionFactoryBean createSqlSessionFactoryBean(DataSource dataSource, DataSourceConfig config) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        if (StringUtils.isNotBlank(config.getMybatisConfigLocation())) {

            Resource resource = resourceLoader.getResource(config.getMybatisConfigLocation());
            if (resource.exists()) {
                sqlSessionFactoryBean.setConfigLocation(resource);
            } else {
                LOG.warn("Mybatis config file not found: {}", config.getMybatisConfigLocation());
            }
        }
        sqlSessionFactoryBean.afterPropertiesSet();
        return sqlSessionFactoryBean;
    }

    private DataSource createDataSource(DataSourceConfig config) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setJdbcUrl(config.getJdbcUrl());
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setAutoCommit(config.isAutoCommit());
        dataSource.setMinimumIdle(config.getMinimumIdle());
        dataSource.setMaximumPoolSize(config.getMaximumPoolSize());
        dataSource.setConnectionInitSql(config.getValidationQuery());

        // set connection properties
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setConnectionProperties(config.getConnectionProperties());
        dataSource.setDataSourceProperties(propertiesFactoryBean.getObject());
        return dataSource;
    }

    public void init() throws Exception {
        if (sqlSessionTemplates != null && !sqlSessionTemplates.isEmpty()) {
            for (SqlSessionTemplate template : sqlSessionTemplates) {
                template.getConfiguration().getMappedStatements();
            }
        }
        LOG.info("All mapped statement build successfully");
    }

    @Override
    public BasicDAO getObject() throws Exception {
        Assert.notNull(config, "config can not be null");
        DatabaseRouter router = newDataBaseRouter(config);
        BasicDAO dao = new BasicDAO();
        dao.setRouter(router);
        return dao;
    }

    @Override
    public Class<?> getObjectType() {
        return BasicDAO.class;
    }

    public MybatisxConfig getConfig() {
        return config;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
