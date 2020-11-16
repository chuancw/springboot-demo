package com.wangchuan.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    /**
     * 获取配置文件中的值
     */
    @Value("${person.dog.name}")
    private String name;

    @RequestMapping("/1")
    public String index1(){
        return "hello" + name;
    }
}
