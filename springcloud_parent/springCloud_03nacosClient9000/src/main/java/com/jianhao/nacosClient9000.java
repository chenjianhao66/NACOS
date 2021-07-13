package com.jianhao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
//@Re
//@RefreshCode
public class nacosClient9000 {
    public static void main(String[] args) {
        SpringApplication.run(nacosClient9000.class,args);
    }


}
