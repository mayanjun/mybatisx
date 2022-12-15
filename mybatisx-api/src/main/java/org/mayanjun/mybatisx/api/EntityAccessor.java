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

package org.mayanjun.mybatisx.api;
import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.query.Query;

import java.util.List;

/**
 * Standard EntityAccessor
 *
 * @author mayanjun(2/26/16)
 * @since 0.0.5
 */
public interface EntityAccessor {

    /**
     * Query entities
     * @param query
     * @param <T>
     * @return
     */
    <T extends Entity> List<T> query(Query<T> query);

    /**
     * Returns only one entity
     * @param query
     * @param <T>
     * @return
     */
    <T extends Entity> T queryOne(Query<T> query);

    /**
     * Update a bean. All of Non-Null fields will be updated
     * @param entity
     * @return
     */
    int update(Entity entity);

    /**
     * Update beans with query
     * @param entity
     * @param query
     * @return
     */
    int update(Entity entity, Query<? extends Entity> query);

    /**
     * Save a bean
     * @param entity
     * @param isAutoIncrementId true if used an auto-increment id
     * @return
     */
    int save(Entity entity, boolean isAutoIncrementId);

    /**
     * Save a bean
     * @param entity
     * @return
     */
    int save(Entity entity);

    /**
     * Save or update a bean
     * @param entity
     * @return
     */
    int saveOrUpdate(Entity entity);

    /**
     * Save or update bean
     * @param entity
     * @param isAutoIncrementId
     * @return
     */
    int saveOrUpdate(Entity entity, boolean isAutoIncrementId);

    /**
     * Delete a bean
     * @param entity
     * @return
     */
    int delete(Entity entity);

    /**
     * Delete beans with a query
     * @param query
     * @return
     */
    int delete(Query<? extends Entity> query);

    /**
     * Get a bean that only the specified fields is included
     * @param entity
     * @param includeFields
     * @param <T>
     * @return
     */
    <T extends Entity> T getInclude(Entity entity, String... includeFields);

    //<T extends Entity> T getInclude(Serializable id, Class<T> cls, String... includeFields);

    /**
     * Get a bean that only the specified fields is included
     * @param entity
     * @param forUpdate pessimistic lock
     * @param includeFields
     * @param <T>
     * @return
     */
    <T extends Entity> T getInclude(Entity entity, boolean forUpdate, String... includeFields);

    //<T extends Entity> T getInclude(Serializable id, Class<T> cls, boolean forUpdate, String... includeFields);

    /**
     * Get a bean that the specified fields is excluded
     * @param entity
     * @param excludeFields
     * @param <T>
     * @return
     */
    <T extends Entity> T getExclude(Entity entity, String... excludeFields);
    //<T extends Entity> T getExclude(Serializable id, Class<T> cls, String... excludeFields);

    /**
     * Get a bean that the specified fields is excluded
     * @param entity
     * @param forUpdate pessimistic lock
     * @param excludeFields
     * @param <T>
     * @return
     */
    <T extends Entity> T getExclude(Entity entity, boolean forUpdate, String... excludeFields);
    //<T extends Entity> T getExclude(Serializable id, Class<T> cls, boolean forUpdate, String... excludeFields);


    /**
     * Count rows
     * @param query
     * @return
     */
    long count(Query<?> query);
}
