package com.fukwang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:fileConfig.properties")
@Configuration
public class ConfigProps {
    @Value("${name}")
    private String name;
    public ConfigProps() {
    }

    public ConfigProps(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
