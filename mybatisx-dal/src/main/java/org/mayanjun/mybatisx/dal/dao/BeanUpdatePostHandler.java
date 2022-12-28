package org.mayanjun.mybatisx.dal.dao;

import org.mayanjun.mybatisx.api.entity.Entity;

/**
 * 一个具体的实体Bean更新后的回调
 * @since 2022/12/28
 * @author mayanjun
 */
public interface BeanUpdatePostHandler {

    /**
     * 实体更新后执行，即时在此方法中抛出异常也不会影响更新结果，发生异常也不会抛出异常。
     * 此方法是留给用户实现一些数据更新后的通知类动作，例如缓存淘汰等，并不适合终止事务的执行。
     * 注意：带条件的更新不会调用此方法
     * 如果要抛出异常终止业务，请自行实现
     * @param entity
     */
    void postUpdate(Entity entity);

}
