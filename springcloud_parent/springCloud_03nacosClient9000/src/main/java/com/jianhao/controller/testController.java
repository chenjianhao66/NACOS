package com.jianhao.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;


@RestController
@Api(tags = "Nacos配置测试接口类")
public class testController {
    // nacos server addr:port
    private static final String NACOS_SERVER_ADDR = "http://127.0.0.1:8848";

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/getConfig")
    @ApiOperation(value = "根据dataId和group获取naCos上的配置config",notes = "命名空间没有要求的话，则为public")
    public String getConfig(@RequestParam(value = "tenant") String tenant,
                                @RequestParam(value = "dataId") String dataId,
                                @RequestParam("group") String group)
            throws IOException {
        String url = String.format( NACOS_SERVER_ADDR + "/nacos/v1/cs/configs?dataId=%s&group=%s&tenant=%s", dataId, group,tenant);
        return  restTemplate.getForObject(url, String.class);
    }


    @PostMapping("addConfig")
    @ApiOperation("发布配置")
    public String addConfig(String dataId,String group,String type,String[] content,String tenant){
        String strings ="";
        for (String s : content) {
            strings += s + "\n";
        }
        String url = String.format( NACOS_SERVER_ADDR +
                "/nacos/v1/cs/configs?dataId=%s&group=%s&type=%s&tenant=%s&content=%s", dataId, group, type,tenant,strings);
        System.out.println(url);
        return restTemplate.postForObject(url, "", String.class);
    }

    @DeleteMapping("deleteConfig")
    @ApiOperation(value = "删除配置",notes = "如果要删除配置的命名空间在public，那么不用加tenant参数")
    public boolean deleteConfig(String dataId,String group,String tenant){
        String url = String.format( NACOS_SERVER_ADDR + "/nacos/v1/cs/configs?dataId=%s&group=%s", dataId, group);
        //tenant传入的不是public，那么则就添加tenant参数
        if (!tenant.equals("public"))
            url += "&tenant="+tenant;
        System.out.println(url);
        try {
            restTemplate.delete(url,boolean.class);
            return true;
        } catch (RestClientException e) {
            e.printStackTrace();
            return false;
        }
    }


    @ApiOperation("增加监听器")
    @PostMapping("addListener")
    public String addListener(String dataId,String group){
        Properties properties = new Properties();
        properties.put("serverAddr","localhost");
        try {
            ConfigService configService = NacosFactory.createConfigService("localhost");
            String config = configService.getConfig(dataId, group, 10000);
            System.out.println(config);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    System.out.println("getExecutor method run......");
                    return null;
                }
                @Override
                public void receiveConfigInfo(String s) {
                    System.out.println("receiveConfigInfo method run......\n"+s);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }

        return "addListener method end.....";
    }


    @ApiOperation("删除对应配置文件的监听器")
    @PostMapping("deleteListener")
    public String deleteListener(String dataId,String group){
        try {
            ConfigService configService = NacosFactory.createConfigService("localhost");
            configService.removeListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String s) {

                }
            });
            return "delete Listener success.....";
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return "";
    }


    @ApiOperation("获取一个服务里的所有实例")
    @GetMapping("getAllInstances/{serverName}")
    public String getAllInstances(@PathVariable String serverName){
        try {
            NamingService namingService = NacosFactory.createNamingService("localhost");
            List<Instance> allInstances = namingService.getAllInstances(serverName);
            System.out.println(allInstances);
            System.out.println(SystemUtils.LINE_SEPARATOR);
            Instance instance = new Instance();
//            Service
        } catch (NacosException e) {
            System.out.println("nacos Exception~~~");
            e.printStackTrace();
        }
        return "getAllInstances method end....";
    }

}
