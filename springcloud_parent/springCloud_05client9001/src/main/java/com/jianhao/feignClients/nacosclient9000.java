package com.jianhao.feignClients;

import com.jianhao.pojo.response;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "nacosclient9000")
public interface nacosclient9000 {
    @GetMapping("config/getconfig")
    response getConfig(@RequestParam("dataId") String dataId,
                       @RequestParam("group") String group,
                       @RequestParam(value = "tenant", defaultValue = StringUtils.EMPTY) String tenant);

    @GetMapping("config/test")
    public String test();


}
