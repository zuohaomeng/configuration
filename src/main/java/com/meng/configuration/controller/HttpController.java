package com.meng.configuration.controller;

import com.meng.configuration.service.ConfigurationItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供http请求使用
 *
 * @author HAO.ZUO
 * @date 2020/4/11--20:53
 */
@Slf4j
@RestController
public class HttpController {
    @Resource
    private ConfigurationItemService configurationItemService;

    @GetMapping("/getallitem")
    public Map getAllItem() {

        return new HashMap();
    }


}
