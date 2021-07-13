package com.jianhao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableDiscoveryClient
public class nacosClient8888 {
    public static void main(String[] args) {
        SpringApplication.run(nacosClient8888.class,args);
    }

  @RestController
    public class controller{
        @GetMapping("/")
        public String test(){
            return "nacosClient8888 server run success";
        }
    }
}
