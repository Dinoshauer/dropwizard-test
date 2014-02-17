package com.redis_crud;

import com.redis_crud.resources.UserResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import redis.clients.jedis.Jedis;

/**
 * Created by kasper on 2/14/14.
 */
public class RedisCrudService extends Service<RedisCrudConfiguration> {
    public static void main (String[] args) throws Exception {
        new RedisCrudService().run(args);
    }

    @Override
    public void initialize (Bootstrap<RedisCrudConfiguration> bootstrap) {
        bootstrap.setName("redis-crud");
    }

    @Override
    public void run (RedisCrudConfiguration configuration,
                     Environment environment) {
        final Jedis jedis = new Jedis(configuration.getRedis().getHostname(),
                configuration.getRedis().getPort());

        environment.addResource(new UserResource(jedis));
    }
}
