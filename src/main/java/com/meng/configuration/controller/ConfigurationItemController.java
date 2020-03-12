package com.meng.configuration.controller;

import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author 梦醉
 * @date 2020/3/12--0:45
 */
@Slf4j
@Controller
@RequestMapping("/item")
public class ConfigurationItemController {

    @Resource
    private ConfigurationItemService configurationItemService;

    @RequestMapping
    public String item(String id) {
        return "item/item";
    }

    @RequestMapping("to-add")
    public String toAdd() {
        log.info("[ConfigurationItemController toadd]");
        return "item/itemAdd";
    }


    @ResponseBody
    @RequestMapping("list")
    public HashMap list() {
        HashMap map = new HashMap();

        return map;
    }

    @ResponseBody
    @RequestMapping("add")
    public ResponseModel add(@RequestBody ConfigurationItem configurationItem) {
        log.info("[ConfigurationItemController add]");
        if (StringUtils.isEmpty(configurationItem.getNewKey())
                || StringUtils.isEmpty(configurationItem.getNewValue())) {
            return ResponseModel.ERROR("请填写key或者value");
        }
        ResponseModel model = configurationItemService.add(configurationItem);
        return model;
    }

}
