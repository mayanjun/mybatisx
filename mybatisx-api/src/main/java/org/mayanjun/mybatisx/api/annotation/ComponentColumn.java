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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件。可以将一组字段封装在一个对象中，并通过 @Component 注解来整体引入，类似Hibernate中的组件列。
 * Component fields. A group of fields can be encapsulated in a component object. The same
 * as Component in Hibernate
 *
 * @author mayanjun(8/19/15)
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ComponentColumn {
    String[] excludes() default {};

    /**
     * 实体名称注释
     * @return
     */
    String value();

    /**
     * 引用的是哪个类型
     * @return
     */
    Class<?> referenceType();
}
