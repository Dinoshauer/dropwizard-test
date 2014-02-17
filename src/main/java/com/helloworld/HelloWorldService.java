package com.helloworld;

import com.helloworld.health.TemplateHealthCheck;
import com.helloworld.resources.HelloWorldResource;
import com.helloworld.resources.UserResource;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import redis.clients.jedis.Jedis;

/**
 * Created by kasper on 2/14/14.
 */
public class HelloWorldService extends Service<HelloWorldConfiguration> {
    public static void main (String[] args) throws Exception {
        new HelloWorldService().run(args);
    }

    @Override
    public void initialize (Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.setName("hello-world");
    }

    @Override
    public void run (HelloWorldConfiguration configuration,
                     Environment environment) {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();
        final Jedis jedis = new Jedis(configuration.getRedis().getHostname(),
                configuration.getRedis().getPort());

        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addHealthCheck(new TemplateHealthCheck(template));
        environment.addResource(new UserResource(jedis));
    }
}
