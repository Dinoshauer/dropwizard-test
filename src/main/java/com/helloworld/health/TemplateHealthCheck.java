package com.helloworld.health;

import com.yammer.metrics.core.HealthCheck;

/**
 * Created by kasper on 2/14/14.
 */
public class TemplateHealthCheck extends HealthCheck {
    private final String template;

    public TemplateHealthCheck (String template) {
        super("template");
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("Template doesn't include TEST");
        }
        return Result.healthy();
    }
}
