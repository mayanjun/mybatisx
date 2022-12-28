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

package org.mayanjun.mybatisx.dal;
import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * ShardingEntityAccessor.
 * Provide api for sharding business.
 * @author mayanjun(6/30/16)
 */
public interface ShardingEntityAccessor {

    /**
     * Query data
     * @param query query object
     * @param sharding sharding
     * @param <T> entity type
     * @return entity list
     */
    <T extends Entity> List<T> query(Query<T> query, Sharding sharding);

    /**
     * Query one entity
     * @param query query
     * @param sharding sharding
     * @param <T> entity type
     * @return entity
     */
    <T extends Entity> T queryOne(Query<T> query, Sharding sharding);

    /**
     * Update entity
     * @param entity entity bean
     * @param sharding sharding
     * @return effected rows
     */
    int update(Entity entity, Sharding sharding);

    /**
     * Update entity
     * @param entity entity bean
     * @param sharding sharding
     * @param query query
     * @return effected rows
     */
    int update(Entity entity, Sharding sharding, Query<? extends Entity> query);

    /**
     * Save entity bean
     * @param entity bean
     * @param sharding sharding
     * @return id
     */
    int save(Entity entity, Sharding sharding);

    /**
     * Save entity bean
     * @param entity entity bean
     * @param sharding sharding
     * @param isAutoIncrementId if use AUTO_INCREMENT to generate id
     * @return id
     */
    int save(Entity entity, Sharding sharding, boolean isAutoIncrementId);

    /**
     * Save or update entity bean
     * @param entity entity bean
     * @param sharding sharding
     * @return id
     */
    int saveOrUpdate(Entity entity, Sharding sharding);

    /**
     * Save of update entity bean
     * @param entity entity bean
     * @param sharding sharding
     * @param isAutoIncrementId if use AUTO_INCREMENT to generate id
     * @return id
     */
    int saveOrUpdate(Entity entity, Sharding sharding, boolean isAutoIncrementId);

    /**
     * Delete an entity bean. Note that the entity id must be present
     * @param entity entity bean
     * @param sharding sharding
     * @return effected rows
     */
    int delete(Entity entity, Sharding sharding);

    /**
     * Delete data with query.
     * @param query query
     * @param sharding sharding
     * @return effected rows
     */
    int delete(Query<? extends Entity> query, Sharding sharding);

    /**
     * Query an entity bean. Note that the entity id must be present
     * @param entity entity bean with id
     * @param sharding sharding
     * @param excludeFields excluded fields
     * @param <T> entity type
     * @return entity bean
     */
    <T extends Entity> T getExclude(Entity entity, Sharding sharding, String... excludeFields);

    /**
     * Query an entity bean. Note that the entity id must be present
     * @param entity bean with id
     * @param sharding sharding
     * @param includeFields included fields
     * @param <T> entity type
     * @return entity
     */
    <T extends Entity> T getInclude(Entity entity, Sharding sharding, String... includeFields);

    /**
     * Query an entity bean. Note that the entity id must be present
     * @param entity entity bean with id
     * @param sharding sharding
     * @param forUpdate if use Pessimistic Lock(FOR UPDATE)
     * @param excludeFields excluded fields
     * @param <T> entity type
     * @return entity
     */
    <T extends Entity> T getExclude(Entity entity, Sharding sharding, boolean forUpdate, String... excludeFields);

    /**
     * Query an entity bean. Note that the entity id must be present
     * @param entity entity bean with id
     * @param sharding sharding
     * @param forUpdate if use Pessimistic Lock(FOR UPDATE)
     * @param includeFields included fields
     * @param <T> entity type
     * @return entity
     */
    <T extends Entity> T getInclude(Entity entity, Sharding sharding, boolean forUpdate, String... includeFields);

    /**
     * Count
     * @param query query
     * @param sharding sharding
     * @return counts
     */
    long count(Query<?> query, Sharding sharding);
}
