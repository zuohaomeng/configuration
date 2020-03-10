package com.meng.configuration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 梦醉
 * @date 2020/3/11--1:26
 */
@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectGroupController {
    
    @RequestMapping("/to-projectgroup")
    public String toProjectGroup(){
        return null;
    }
}
