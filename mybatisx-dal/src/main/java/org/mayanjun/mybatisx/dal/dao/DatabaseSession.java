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

import org.apache.ibatis.session.SqlSession;
import org.mayanjun.mybatisx.dal.IdGenerator;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Database session
 */
public class DatabaseSession {

    /**
     * Session name
     */
    private String name;

    /**
     * Underlying session. Here is a mybatis implementation
     */
    private SqlSession sqlSession;

    /**
     * Transaction
     */
    private TransactionTemplate transaction;

    private DataSource dataSource;

    private IdGenerator idGenerator;
    private static final Map<Class, Object> MAPPER_CACHE = new ConcurrentHashMap<Class, Object>();

    /**
     * Test if a Mapper class is registered
     *
     * @param type mybatis mapper type
     * @return
     */
    public boolean hasMapper(Class<?> type) {
        return sqlSession.getConfiguration().hasMapper(type);
    }

    /**
     * Return a registered mapper
     *
     * @param type mapper class
     * @param <T>  mapper type
     * @return Mapper object
     */
    public <T> T getMapper(Class<T> type) {
        Object mapper = MAPPER_CACHE.get(type);
        if (mapper == null) {
            synchronized (type) {
                mapper = MAPPER_CACHE.get(type);
                if (mapper == null) {
                    if (!hasMapper(type)) {
                        sqlSession.getConfiguration().addMapper(type);
                        mapper = sqlSession.getMapper(type);
                        MAPPER_CACHE.put(type, mapper);
                    }
                }
            }
        }
        return (T) mapper;
    }

    /**
     * Construct an instance of DatabaseSession
     *
     * @param name       database session name
     * @param sqlSession mybatis sql session
     */
    public DatabaseSession(String name, SqlSession sqlSession) {
        this(name, null, sqlSession, null, null);
    }

    /**
     * Construct an instance of DatabaseSession
     *
     * @param name        database session name
     * @param sqlSession  mybatis sql session
     * @param transaction transaction
     */
    public DatabaseSession(String name, SqlSession sqlSession, TransactionTemplate transaction) {
        this(name, null, sqlSession, null, transaction);
    }

    public DatabaseSession(String name, DataSource dataSource, SqlSession sqlSession, IdGenerator idGenerator, TransactionTemplate transaction) {
        this.name = name;
        this.dataSource = dataSource;
        this.sqlSession = sqlSession;
        this.idGenerator = idGenerator;
        this.transaction = transaction;
    }

    public String name() {
        return name;
    }

    public SqlSession sqlSession() {
        return sqlSession;
    }

    public TransactionTemplate transaction() {
        return transaction;
    }

    public DataSource dataSource() {
        return this.dataSource;
    }

    public IdGenerator idGenerator() {
        return this.idGenerator;
    }
}
