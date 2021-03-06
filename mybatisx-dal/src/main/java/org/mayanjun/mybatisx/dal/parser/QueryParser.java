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

package org.mayanjun.mybatisx.dal.parser;

import org.mayanjun.mybatisx.api.query.Query;

import java.io.Serializable;

/**
 * Query Parser
 * @author mayanjun
 * @since 0.0.5
 */
public interface QueryParser {

	/**
	 * 解析Query
	 * @param query
	 * @return
	 * @author mayanjun
	 * @since 1.0.1
	 */
	<T extends Serializable> SQLParameter<T> parse(Query<T> query);
}
