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


import org.mayanjun.mybatisx.api.EntityAccessor;

/**
 * DataBaseRouteAccessor
 *
 * @author mayanjun(6/24/16)
 */
public interface DataBaseRouteAccessor extends EntityAccessor {

    /**
     * Get DatabaseRouter
     * @return the database router
     */
    DatabaseRouter databaseRouter();
}
