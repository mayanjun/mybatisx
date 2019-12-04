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

package org.mayanjun.mybatisx.api.annotation;

import org.mayanjun.mybatisx.api.enums.IndexType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引定义
 * Config the index
 * @author mayanjun(8/19/15)
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Index {

    /**
     * 索引名称
     * Index name
     * @return index name
     */
    String value() default "#";

    /**
     * 索引字段
     * Index columns
     * @return
     */
    IndexColumn[] columns();

    /**
     * 索引类型
     * Index type
     * @return
     */
    IndexType type() default IndexType.NULL;
}
