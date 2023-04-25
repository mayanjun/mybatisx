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

import org.mayanjun.mybatisx.dal.Sharding;
import org.mayanjun.mybatisx.dal.sharding.DefaultShardingProvider;
import org.mayanjun.mybatisx.dal.sharding.StaticSharding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * BasicDAO
 *
 * @author mayanjun(6/30/16)
 */
public class BasicDAO extends DynamicDAO {

    private Sharding defaultSharding;

    private volatile List<Sharding> shardings;

    public BasicDAO(DefaultShardingProvider defaultShardingProvider) {
        if (defaultShardingProvider != null) {
            this.defaultSharding = defaultShardingProvider.get();
        } else {
            this.defaultSharding = DEFAULT_SHARDING;
        }
    }

    @Override
    protected Sharding defaultSharding() {
        return defaultSharding;
    }

    public static final Sharding DEFAULT_SHARDING = new Sharding() {

        @Override
        public String getDatabaseName(Object source) {
            return null;
        }

        @Override
        public String getTableName(Object source) {
            return null;
        }

        @Override
        public Map<String, Set<String>> getDatabaseNames(Object source) {
            return null;
        }
    };

    /**
     * 返回所有数据库的静态Sharding
     * @return
     */
    public List<Sharding> shardings() {
        if (shardings == null) {
            synchronized (this) {
                List<Sharding> ss = shardings;
                if (ss == null) {
                    ss = new ArrayList<Sharding>();
                    for (DatabaseSession databaseSession : databaseRouter().getDatabaseSessions()) {
                        ss.add(
                                new StaticSharding(databaseSession.name(), null)
                        );
                    }
                    shardings = ss;
                }
            }
        }
        return shardings;
    }

}