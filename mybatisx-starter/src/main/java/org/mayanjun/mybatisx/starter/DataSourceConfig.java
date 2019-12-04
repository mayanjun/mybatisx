package org.mayanjun.mybatisx.starter;

/**
 * Datasource configuration
 */
public class DataSourceConfig {
    private String idGeneratorType;
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
    private String connectionProperties = "createDatabaseIfNotExist=true;characterEncoding=utf-8;useUnicode=true;allowMultiQueries=true;zeroDateTimeBehavior=convertToNull";

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

    public String getIdGeneratorType() {
        return idGeneratorType;
    }

    public void setIdGeneratorType(String idGeneratorType) {
        this.idGeneratorType = idGeneratorType;
    }
}