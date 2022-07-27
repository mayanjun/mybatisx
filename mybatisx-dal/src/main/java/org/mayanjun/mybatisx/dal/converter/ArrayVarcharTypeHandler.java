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

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.mayanjun.mybatisx.dal.util.ClassUtils;

import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis String数组和VarChar的转换器
 * @since 2019-07-06
 * @author mayanjun
 */
public abstract class ArrayVarcharTypeHandler<T> extends BaseTypeHandler<T> {

    protected String arrayToString(T array) {
        if (array == null || !array.getClass().isArray() || Array.getLength(array) == 0) return "";

        StringBuffer sb = new StringBuffer();
        int length = Array.getLength(array);

        for (int i = 0; i < length; i++) {
            Object o = Array.get(array, i);
            sb.append(o != null ? o.toString() : "");
            if (i < length - 1) sb.append(",");
        }
        return sb.toString();
    }

    private Class<?> componentType;
    private Class<?> getComponentType() {
        if (componentType == null) {
            Class arrayType = ClassUtils.getFirstParameterizedType(this.getClass());
            componentType = arrayType.getComponentType();
        }
        return componentType;
    }

    protected T toArray(String s) {
        Class ct = getComponentType();
        if (s == null) return (T) Array.newInstance(ct, 0);
        String ss[] = s.split(",");
        List list = new ArrayList();
        for (String e : ss) {
            Object o = null;
            try {
                o = convert(e, ct);
            } catch (Exception ex) {}
            if (o != null) list.add(o);
        }
        T array = (T) Array.newInstance(ct, list.size());

        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }

        return array;
    }

    /**
     * 转换成对应元素类型
     * @param s
     * @param type
     * @return
     */
    public abstract Object convert(String s, Class type);

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, arrayToString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toArray(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toArray(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toArray(cs.getString(columnIndex));
    }
}
