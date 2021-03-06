package com.redis_crud;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by kasper on 2/14/14.
 */
public class RedisCrudConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private RedisConfiguration redis = new RedisConfiguration();

    public RedisConfiguration getRedis() {
        return redis;
    }
}
