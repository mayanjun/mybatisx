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

package org.mayanjun.mybatisx.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@SuppressWarnings("ALL")
@ConfigurationProperties(prefix = "mybatisx")
public class MybatisxConfig {

    private List<DataSourceConfig> dataSources;

    private String [] entityPackages;

    public List<DataSourceConfig> getDataSources() {
        return dataSources;
    }

    public void setDataSources(List<DataSourceConfig> dataSources) {
        this.dataSources = dataSources;
    }

    public String[] getEntityPackages() {
        return entityPackages;
    }

    public void setEntityPackages(String[] entityPackages) {
        this.entityPackages = entityPackages;
    }
}