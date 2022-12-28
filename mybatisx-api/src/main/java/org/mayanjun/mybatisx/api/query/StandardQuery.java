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

package org.mayanjun.mybatisx.api.query;

import org.mayanjun.mybatisx.api.entity.Entity;
import org.mayanjun.mybatisx.api.enums.QueryDeletedMode;

import java.util.*;

/**
 * Represents a query
 * @author mayanjun
 * @since 0.0.1
 */
public class StandardQuery<T extends Entity> implements Query<T> {

	private static final long serialVersionUID = -656396755847643907L;
	private List<SqlComparator> comparators;
	private int[] limits;
	private Map<String, Sort> sorts = new HashMap<String, Sort>();
	private Class<T> beanType;
	private List<String> excludeFields;
	private List<String> includeFields;
	private boolean dataIsolationEnabled = true;

	private boolean forUpdate;
	private QueryDeletedMode queryDeletedMode;
	
	public StandardQuery() {
		this.comparators = new ArrayList<SqlComparator>();
		this.limits = new int[2];
	}

	@Override
	public Query<T> addComparator(SqlComparator comparator) {
		if(comparator != null) this.comparators.add(comparator);
		return this;
	}

	@Override
	public Query<T> removeComparator(SqlComparator comparator) {
		if(comparator != null) this.comparators.remove(comparator);
		return this;
	}

	@Override
	public Query<T> addComparators(List<SqlComparator> comparators) {
		this.comparators.addAll(comparators);
		return this;
	}

	@Override
	public List<SqlComparator> getComparators() {
		return this.comparators;
	}

	@Override
	public Query<T> setLimit(int limit) {
		return setLimits(0, limit);
	}

	@Override
	public int getLimit() {
		return this.limits[1];
	}

	@Override
	public Query<T> setLimits(int offset, int limit) {
		this.limits[0] = offset;
		this.limits[1] = limit;
		return this;
	}

	@Override
	public int[] getLimits() {
		return limits;
	}

	@Override
	public Query<T> setBeanType(Class<T> beanType) {
		this.beanType = beanType;
		return this;
	}

	@Override
	public Class<T> getBeanType() {
		return this.beanType;
	}

	@Override
	public Query<T> setExcludeFields(List<String> excludeFields) {
		this.excludeFields = excludeFields;
		return this;
	}

	@Override
	public List<String> getExcludeFields() {
		return this.excludeFields;
	}

	@Override
	public Query<T> setIncludeFields(List<String> includeFields) {
		this.includeFields = includeFields;
		return this;
	}

	@Override
	public List<String> getIncludeFields() {
		return this.includeFields;
	}

	@Override
	public boolean isForUpdate() {
		return this.forUpdate;
	}

	@Override
	public Query<T> setForUpdate(boolean forUpdate) {
		this.forUpdate = forUpdate;
		return this;
	}

	@Override
	public QueryDeletedMode getQueryDeletedMode() {
		return queryDeletedMode;
	}

	@Override
	public Query<T> setQueryDeletedMode(QueryDeletedMode queryDeletedMode) {
		this.queryDeletedMode = queryDeletedMode;
		return this;
	}

	@Override
	public Query<T> addSort(Sort sort) {
		if (sort != null) sorts.put(sort.getName(), sort);
		return this;
	}

	@Override
	public Query<T> clearSort() {
		this.sorts.clear();
		return this;
	}

	@Override
	public Query<T> removeSort(String field) {
		this.sorts.remove(field);
		return this;
	}

	@Override
	public Set<Sort> sorts() {
		return Collections.unmodifiableSet(new HashSet<Sort>(sorts.values()));
	}

	@Override
	public Query<T> setDataIsolationEnabled(boolean enabled) {
		this.dataIsolationEnabled = enabled;
		return this;
	}

	@Override
	public boolean isDataIsolationEnabled() {
		return dataIsolationEnabled;
	}

}
