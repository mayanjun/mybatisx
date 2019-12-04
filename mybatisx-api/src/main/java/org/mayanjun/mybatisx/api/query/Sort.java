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

import java.io.Serializable;

/**
 * Sort
 * @author mayanjun
 * @since 0.0.1
 */
public class Sort implements Serializable {
	
	private static final long serialVersionUID = -180616073336552137L;

	public Sort() {
	}

	public Sort(String name, SortDirection direction) {
		this.name = name;
		this.direction = direction;
	}

	/**
	 * Sort name
	 */
	private String name;

	/**
	 * Sort order
	 */
	private SortDirection direction;

	/**
	 * Get field name
	 * @return field name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name
	 * @param name order field name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get sort order
	 * @return sort order
	 */
	public SortDirection getDirection() {
		return direction;
	}

	/**
	 * Set sort order
	 * @param direction sort order
	 */
	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Sort sort = (Sort) o;
		return name.equals(sort.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
}
