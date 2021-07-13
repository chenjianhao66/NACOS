package com.jianhao.controller;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Api(tags = "NACOS服务管理接口类")
public class NaMingController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String NACOS_SERVER_ADDR = "http://127.0.0.1:8848";


    @ApiOperation("描述注册一个实例到服务。")
    @PostMapping("registerInstance")
    public String  registerInstance(@RequestParam("ip") String ip,
                                 @RequestParam("serviceName") String serviceName,
                                    @RequestParam("port") int port)
    {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setServiceName(serviceName);
        try {
            NamingService namingService = NacosFactory.createNamingService("localhost");
            namingService.registerInstance(serviceName,instance);
           return "success";
        } catch (NacosException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @ApiOperation("删除服务下的一个实例。")
    @PostMapping("deregisterInstance")
    public String deregisterInstance(@RequestParam("ip") String ip,
                                     @RequestParam("serviceName") String serviceName,
                                     @RequestParam("port") int port)
    {
        try {
            NamingService namingService = NacosFactory.createNamingService("localhost");
            namingService.deregisterInstance(serviceName,ip,port);
            return "success";
        } catch (NacosException e) {
            e.printStackTrace();
            return "fail";
        }
    }


    @ApiOperation("获取服务下的所有实例。")
    @GetMapping("getAllInstances")
    public String getAllInstances(@RequestParam("serviceName") String serviceName){
        NamingService namingService = null;
        try {
            namingService = NacosFactory.createNamingService("localhost");
            List<Instance> allInstances = namingService.getAllInstances(serviceName);
            String string = JSONObject.toJSONString(allInstances);
            return string;
        } catch (NacosException e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @ApiOperation("创建一个服务")
    @PostMapping("addService")
    public String addService(String serviceName){
        StringBuffer url = new StringBuffer(NACOS_SERVER_ADDR);
        url.append("/nacos/v1/ns/service?serviceName="+serviceName);
        System.out.println("url = " + url);
        String result = restTemplate.postForObject(url.toString(), "", String.class);
        return result;
    }

}
