package org.mayanjun.mybatisx.dal.converter;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({Class.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ClassVarcharTypeHandler extends BaseTypeHandler<Class> {

    private String classToString(Class cls) {
        if (cls == null) return null;
        return cls.getCanonicalName();
    }

    private Class stringToClass(String classString) {

        Class cls = null;

        try {
            cls = Class.forName(classString);
        } catch (Exception e) {

        }

        return cls;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Class aClass, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, classToString(aClass));
    }

    @Override
    public Class getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return stringToClass(resultSet.getString(s));
    }

    @Override
    public Class getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return stringToClass(resultSet.getString(i));
    }

    @Override
    public Class getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return stringToClass(callableStatement.getString(i));
    }
}
