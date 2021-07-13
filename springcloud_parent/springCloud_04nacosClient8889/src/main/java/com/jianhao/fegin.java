package com.jianhao;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacosclient9000")
public interface fegin {
    @GetMapping("nacosService")
    public String test2();
}

