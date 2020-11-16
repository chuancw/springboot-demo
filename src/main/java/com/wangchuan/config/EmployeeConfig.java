package com.wangchuan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @PropertySource(value = "classpath:Employee.properties")  加载非默认配置文件
 * 只能用于properties文件
 */
@PropertySource(value = {"classpath:Employee.properties"})
@Component
@ConfigurationProperties(prefix = "employee")
public class EmployeeConfig {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EmployeeConfig{" +
                "name='" + name + '\'' +
                '}';
    }
}
