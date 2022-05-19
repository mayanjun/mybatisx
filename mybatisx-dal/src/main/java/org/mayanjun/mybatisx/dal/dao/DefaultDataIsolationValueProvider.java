package org.mayanjun.mybatisx.dal.dao;

/**
 * 线程本地版本的数据隔离值提供者
 * @since 2022/5/17
 * @author mayanjun
 */
public class DefaultDataIsolationValueProvider implements DataIsolationValueProvider {

    @Override
    public Object value() {
        return ThreadLocalDataIsolation.getIsolationValue();
    }
}
