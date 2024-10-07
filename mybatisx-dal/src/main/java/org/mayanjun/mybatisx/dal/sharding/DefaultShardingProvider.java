package org.mayanjun.mybatisx.dal.sharding;


import org.mayanjun.mybatisx.dal.Sharding;

/**
 * The Default sharding provider
 * @since 2024/10/7
 * @author mayanjun
 */
public interface DefaultShardingProvider {

    Sharding get();
}
