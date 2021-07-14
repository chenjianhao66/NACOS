package com.jianhao;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableDiscoveryClient
public class nacosclient9001 {
    public static void main(String[] args) {
        SpringApplication.run(nacosclient9001.class,args);
    }
}
