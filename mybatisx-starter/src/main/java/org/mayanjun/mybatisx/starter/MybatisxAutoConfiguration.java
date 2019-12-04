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

import org.mayanjun.mybatisx.dal.dao.BasicDAO;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

@EnableConfigurationProperties(MybatisxConfig.class)
@ConditionalOnClass(BasicDAO.class)
@AutoConfigureAfter({MybatisAutoConfiguration.class})
public class MybatisxAutoConfiguration implements ResourceLoaderAware {

    private static final Logger LOG = LoggerFactory.getLogger(MybatisxAutoConfiguration.class);

    @Autowired
    private MybatisxConfig config;

    private ResourceLoader resourceLoader;

    @Bean
    @ConditionalOnMissingBean(BasicDAO.class)
    public BasicDAO dao() throws Exception {
        BasicDaoFactoryBean factory = new BasicDaoFactoryBean(config);
        factory.setResourceLoader(resourceLoader);
        BasicDAO dao = factory.getObject();
        LOG.info("BasicDAO create successfully");
        return dao;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}