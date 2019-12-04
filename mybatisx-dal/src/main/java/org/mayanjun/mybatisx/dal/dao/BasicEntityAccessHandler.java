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

import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.query.Query;
import org.mayanjun.mybatisx.dal.Sharding;
import org.mayanjun.mybatisx.dal.ShardingEntityAccessor;
import org.mayanjun.mybatisx.dal.handler.EntityAccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * BasicEntityHandler
 *
 * @author mayanjun
 */
public abstract class BasicEntityAccessHandler implements EntityAccessHandler, ShardingEntityAccessor {

	@Override
	public long count(Query<?> query, Sharding sharding) {
		return dao.count(query, sharding);
	}

	@Override
	public long count(Query<?> query) {
		return dao.count(query);
	}

	@Autowired
	private BasicDAO dao;

	@Override
	public int delete(Entity bean) {
		return dao.delete(bean);
	}

	@Override
	public int delete(Entity bean, Sharding sharding) {
		return dao.delete(bean, sharding);
	}

	@Override
	public int delete(Query<? extends Entity> query) {
		return dao.delete(query);
	}

	@Override
	public int delete(Query<? extends Entity> query, Sharding sharding) {
		return dao.delete(query, sharding);
	}

	@Override
	public <T extends Entity> T getExclude(Entity bean, String... excludeFields) {
		return dao.getExclude(bean, excludeFields);
	}

	@Override
	public <T extends Entity> T getInclude(Entity bean, String... includeFields) {
		return dao.getInclude(bean, includeFields);
	}

	@Override
	public <T extends Entity> T getExclude(Entity bean, boolean forUpdate, String... excludeFields) {
		return dao.getExclude(bean, forUpdate, excludeFields);
	}

	@Override
	public <T extends Entity> T getInclude(Entity bean, boolean forUpdate, String... includeFields) {
		return dao.getInclude(bean, forUpdate, includeFields);
	}

	@Override
	public <T extends Entity> T getInclude(Entity bean, Sharding sharding,
													  String... includeFields) {
		return dao.getInclude(bean, sharding, includeFields);
	}

	@Override
	public <T extends Entity> T getExclude(Entity bean, Sharding sharding, boolean forUpdate, String... excludeFields) {
		return dao.getExclude(bean, sharding, forUpdate, excludeFields);
	}

	@Override
	public <T extends Entity> T getInclude(Entity bean, Sharding sharding, boolean forUpdate, String... includeFields) {
		return dao.getInclude(bean, sharding, forUpdate, includeFields);
	}

	@Override
	public <T extends Entity> T getExclude(Entity bean, Sharding sharding, String... excludeFields) {
		return dao.getExclude(bean, sharding, excludeFields);
	}

	@Override
	public <T extends Entity> List<T> query(Query<T> query) {
		return dao.query(query);
	}

	@Override
	public <T extends Entity> T queryOne(Query<T> query) {
		return dao.queryOne(query);
	}

	@Override
	public <T extends Entity> List<T> query(Query<T> query, Sharding sharding) {
		return dao.query(query, sharding);
	}

	@Override
	public <T extends Entity> T queryOne(Query<T> query, Sharding sharding) {
		return dao.queryOne(query, sharding);
	}

	@Override
	public int save(Entity bean) {
		return dao.save(bean);
	}

	@Override
	public int save(Entity bean, Sharding sharding) {
		return dao.save(bean, sharding);
	}

	@Override
	public int update(Entity bean) {
		return dao.update(bean);
	}

	@Override
	public int update(Entity bean, Query<? extends Entity> query) {
		return dao.update(bean, query);
	}

	@Override
	public int update(Entity bean, Sharding sharding) {
		return dao.update(bean, sharding);
	}

	@Override
	public int update(Entity bean, Sharding sharding, Query<? extends Entity> query) {
		return dao.update(bean, sharding, query);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public int save(Entity bean, boolean isAutoIncrementId) {
		return dao.save(bean, isAutoIncrementId);
	}

	@Override
	public int saveOrUpdate(Entity bean) {
		return dao.saveOrUpdate(bean);
	}

	@Override
	public int saveOrUpdate(Entity bean, boolean isAutoIncrementId) {
		return dao.saveOrUpdate(bean, isAutoIncrementId);
	}

	@Override
	public int save(Entity bean, Sharding sharding, boolean isAutoIncrementId) {
		return dao.save(bean, sharding, isAutoIncrementId);
	}

	@Override
	public int saveOrUpdate(Entity bean, Sharding sharding) {
		return dao.saveOrUpdate(bean, sharding);
	}

	@Override
	public int saveOrUpdate(Entity bean, Sharding sharding, boolean isAutoIncrementId) {
		return dao.saveOrUpdate(bean, sharding, isAutoIncrementId);
	}
}