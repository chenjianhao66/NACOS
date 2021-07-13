package com.jianhao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
@RefreshScope
public class test {
    @Value("${user.name}")
    private String port;

    @Value("${spring.application.name}")
    private String server_name;


    @GetMapping("/")
    public String test(){
//        return "获取到的服务器地址:"+port+"\n获取到的服务名---->"+server_name;
        return "run"+port+"\n"+server_name;
    }
}
