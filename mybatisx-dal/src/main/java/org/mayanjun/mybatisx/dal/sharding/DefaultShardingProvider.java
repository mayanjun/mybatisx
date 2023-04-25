package org.mayanjun.mybatisx.dal.sharding;


import org.mayanjun.mybatisx.dal.Sharding;

public interface DefaultShardingProvider {

    Sharding get();
}
