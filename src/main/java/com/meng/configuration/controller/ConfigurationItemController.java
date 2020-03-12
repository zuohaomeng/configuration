package com.meng.configuration.controller;

import com.meng.configuration.service.ConfigurationItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author 梦醉
 * @date 2020/3/12--0:45
 */
@Controller
@RequestMapping("/item")
public class ConfigurationItemController {

    @Resource
    private ConfigurationItemService configurationItemService;

    @RequestMapping
    public String item(String id){
        return "item/item";
    }


    @ResponseBody
    @RequestMapping("list")
    public HashMap list(){
        HashMap map = new HashMap();

        return map;
    }

}
