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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Author: Hao.Zuo
 * @Date: 2020/1/19 16:14
 */
@Slf4j
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;


    /**
     * 跳转项目页面
     * @return
     */
    @GetMapping()
    @ApiOperation("project")
    public String project() {
        return "project/project";
    }

    /**
     * 跳转项目添加页面
     * @return
     */
    @GetMapping("to-project-add")
    @ApiOperation("to-project-add")
    public String toProjectAdd() {
        return "project/projectAdd";
    }


    @ResponseBody
    @GetMapping("/list")
    public Map<String,Object> list() {
        log.info("[list project]");
        List<Project> deptList = projectService.selectAllProject();
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","成功");
        map.put("count",deptList.size());
        map.put("data",deptList);
        return map;
    }
    @PostMapping
    public ResponseModel add(){
        return ResponseModel.SUCCESS();
    }
    @GetMapping("/delete")
    public ResponseModel delete(Integer id){
        return ResponseModel.SUCCESS();
    }
    @PostMapping("/update")
    public ResponseModel update(){
        return ResponseModel.SUCCESS();
    }


}
