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

package org.mayanjun.mybatisx.dal.converter;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * Long[] 与 varchar 转换
 * @since 2022/7/27
 * @author mayanjun
 */
@MappedTypes({Long[].class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class LongArrayVarcharTypeHandler extends ArrayVarcharTypeHandler<Long[]> {

    @Override
    public Object convert(String s, Class type) {
        if (StringUtils.isNumeric(s)) {
            return Long.parseLong(s);
        }
        return null;
    }
}
