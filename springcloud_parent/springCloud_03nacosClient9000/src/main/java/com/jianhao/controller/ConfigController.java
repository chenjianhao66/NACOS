package com.jianhao.controller;


import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.jianhao.pojo.response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "NACOS配置中心接口类")
@RequestMapping("config")
public class ConfigController {

    @ApiOperation(value = "据dataId和group来获取一个配置，tenant可选", notes = "tenant默认为空")
    @GetMapping("getConfig")
    public response getConfig(@RequestParam("dataId") String dataId,
                              @RequestParam("group") String group,
                              @RequestParam(value = "tenant", defaultValue = StringUtils.EMPTY) String tenant) {
        response res = new response();
        try {
            ConfigService configService = NacosFactory.createConfigService("localhost");
            String config = configService.getConfig(dataId, group, 3000);
            if (StringUtils.isBlank(config))
                return res.setCode(404).setMsg("查找不到该配置，请重新输入dataId和group!");

            return res.setCode(200).setMsg("获取配置成功!").setData(config);
        } catch (NacosException e) {
            e.printStackTrace();
            return res.setCode(500).setMsg("连接超时或网络异常");
//            retun res.setMsg("").setCode(500);
        }
    }


    @ApiOperation(value = "根据dataId、group、tenant、type来增加配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataId", value = "只允许英文字符和 4 种特殊字符（“.”、“:”、“-”、“_”），不超过 256 字节。"),
            @ApiImplicitParam(name = "group", value = "配置分组，建议填写产品名:模块名（如 Nacos:Test）来保证唯一性。只允许英文字符和 4 种特殊字符（“.”、“:”、“-”、“_”），不超过 128 字节。"),
            @ApiImplicitParam(name = "type", value = "可不选，默认是properties")
    })
    @PostMapping("addConfig")
    public String addConfig(@RequestParam("dataId") String dataId,
                            @RequestParam("group") String group,
                            String[] content,
                            @RequestParam(value = "type", defaultValue = "properties") String type) {
        try {
            StringBuffer string = new StringBuffer();
            for (String s : content) {
                string.append(s);
                string.append(SystemUtils.LINE_SEPARATOR);
            }
            ConfigService configService = NacosFactory.createConfigService("localhost");
            boolean b = configService.publishConfig(dataId, group, string.toString(), type);
            if (b)
                return "发布配置成功!";
            else
                return "发布配置失败,请检查数据!";
        } catch (NacosException e) {
            e.printStackTrace();
            return "连接超时或网络异常!!";
        }
    }

    @ApiOperation("根据dataId以及group来删除配置")
    @DeleteMapping("deleteConfig")
    public response deleteConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group) {
        response res = new response();
        try {
            ConfigService configService = NacosFactory.createConfigService("localhost");
            //首先获取这个配置是否存在
            response config = this.getConfig(dataId, group, "");

            if (config.getCode() == 200) {
                boolean isRemove = configService.removeConfig(dataId, group);
                return res.setMsg("删除配置成功").setCode(200);
            }else
                return res.setCode(404).setMsg("要删除的配置不存在!");
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return res.setMsg("连接超时或网络异常!!").setCode(500);
    }

}
