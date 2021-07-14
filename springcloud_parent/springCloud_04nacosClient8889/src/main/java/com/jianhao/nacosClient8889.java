package com.jianhao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@NacosPropertySource(dataId = "example", autoRefreshed = true)
public class nacosClient8889 {
    public static void main(String[] args) {
        SpringApplication.run(nacosClient8889.class,args);
    }


    @RestController
    public class controller{
        @Value("${server.port}")
        String port;

        @Autowired
        fegin fegin;

        @GetMapping("/")
        public String test(){
            System.out.println("nacos9000 ~~~!!~~~");
            String s = fegin.test2();
            return s+"-------------nacosClient8889  run success !";
        }

        @GetMapping("/config/test")
        public String test1(){
            return "service:nacosclient9000,port---->"+port;
//            return "nacosClient8888 server run success";
        }


    }
}
