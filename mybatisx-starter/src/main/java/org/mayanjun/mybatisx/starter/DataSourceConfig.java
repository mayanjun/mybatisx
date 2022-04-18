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

import org.mayanjun.mybatisx.dal.IdGenerator;

/**
 * Datasource configuration
 */
public class DataSourceConfig {

    private Class<? extends IdGenerator> idGeneratorType;

    private int [] snowflakeIndexes;

    private String name = "master";

    private String driverClassName = "com.mysql.jdbc.Driver";

    private String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/myjack";

    private String username = "root";

    private String password = "123456";

    private boolean autoCommit = true;

    private String validationQuery = "select 1 from dual";

    private int minimumIdle = 0;

    private int maximumPoolSize = 50;

    private String mybatisConfigLocation = "classpath:default-mybatis-config.xml";

    private String isolationLevelName = "ISOLATION_READ_COMMITTED";

    private int transactionTimeout = 3000;

    private String propagationBehaviorName = "PROPAGATION_REQUIRED";

    private String connectionProperties = "createDatabaseIfNotExist=true;zeroDateTimeBehavior=convertToNull;characterEncoding=utf8;useUnicode=true;allowMultiQueries=true;useSSL=false;serverTimezone=GMT+8;autoReconnect=true;sessionVariables=sql_mode=''";


    public int getTransactionTimeout() {
        return transactionTimeout;
    }

    public void setTransactionTimeout(int transactionTimeout) {
        this.transactionTimeout = transactionTimeout;
    }

    public String getIsolationLevelName() {
        return isolationLevelName;
    }

    public void setIsolationLevelName(String isolationLevelName) {
        this.isolationLevelName = isolationLevelName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public void setMinimumIdle(int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getMybatisConfigLocation() {
        return mybatisConfigLocation;
    }

    public void setMybatisConfigLocation(String mybatisConfigLocation) {
        this.mybatisConfigLocation = mybatisConfigLocation;
    }

    public String getPropagationBehaviorName() {
        return propagationBehaviorName;
    }

    public void setPropagationBehaviorName(String propagationBehaviorName) {
        this.propagationBehaviorName = propagationBehaviorName;
    }

    public Class<? extends IdGenerator> getIdGeneratorType() {
        return idGeneratorType;
    }

    public void setIdGeneratorType(Class<? extends IdGenerator> idGeneratorType) {
        this.idGeneratorType = idGeneratorType;
    }

    public int[] getSnowflakeIndexes() {
        return snowflakeIndexes;
    }

    public void setSnowflakeIndexes(int[] snowflakeIndexes) {
        this.snowflakeIndexes = snowflakeIndexes;
    }
}