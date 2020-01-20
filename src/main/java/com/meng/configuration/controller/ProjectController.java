package com.meng.configuration.controller;

import com.meng.configuration.entity.Project;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.util.ResponseModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 16:14
 */
@Slf4j
@Controller
@RequestMapping("/system/dept")
public class ProjectController {
    private String prefix = "system/dept";

    @Resource
    private ProjectService projectService;

    @GetMapping("/test")
    public ResponseModel test(){
        return ResponseModel.SUCCESS();
    }

    @GetMapping()
    @ApiOperation("project")
    public String project() {
        return prefix + "/dept";
    }

    @PostMapping("/list")
    @ResponseBody
    public List<Project> list(Project dept) {
        log.info("[list project]");
        List<Project> deptList = projectService.selectAllProject();
        return deptList;
    }


}
