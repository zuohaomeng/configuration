package com.meng.configuration.controller;

import cn.hutool.json.JSONUtil;
import com.meng.configuration.component.EnvConstant;
import com.meng.configuration.entity.Project;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.service.PullService;
import com.meng.configuration.util.HttpResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @Resource
    private ProjectService projectService;
    @Resource
    private PullService pullService;


    //获取所有配置项
    @GetMapping("/getallitem")
    public String getAllItem(String project, String env,Integer version) {
        Project p = projectService.selectByProjectId(project);
        if (p == null) {
            return JSONUtil.toJsonStr(HttpResult.ERROR("this project is error,project=" + project));
        }

        int envId = 0;
        if (env.equals(EnvConstant.DEV.getValue())) {
            envId = EnvConstant.DEV.getId();
        } else if (env.equals(EnvConstant.TEST.getValue())) {
            envId = EnvConstant.TEST.getId();
        } else if (env.equals(EnvConstant.PRE.getValue())) {
            envId = EnvConstant.PRE.getId();
        } else if (env.equals(EnvConstant.PRO.getValue())) {
            envId = EnvConstant.PRO.getId();
        }
        if (envId == 0) {
            return JSONUtil.toJsonStr(HttpResult.ERROR("the env is error,env=" + env));
        }

        log.info("[cycleGetAllItem],projectId={},env={},version={}",p.getId(),envId,version);
        String result = pullService.getAllItem(p.getId(), envId,version);
        return result;
    }

    /**
     * 循环获取
     * @param project
     * @param env
     * @param version
     * @return
     */
    @GetMapping("cyclegetallitem")
    public String cycleGetAllItem(String project, String env,Integer version){

        Project p = projectService.selectByProjectId(project);
        if (p == null) {
            return JSONUtil.toJsonStr(HttpResult.ERROR("this project is error,project=" + project));
        }

        int envId = 0;
        if (env.equals(EnvConstant.DEV.getValue())) {
            envId = EnvConstant.DEV.getId();
        } else if (env.equals(EnvConstant.TEST.getValue())) {
            envId = EnvConstant.TEST.getId();
        } else if (env.equals(EnvConstant.PRE.getValue())) {
            envId = EnvConstant.PRE.getId();
        } else if (env.equals(EnvConstant.PRO.getValue())) {
            envId = EnvConstant.PRO.getId();
        }
        if (envId == 0) {
            return JSONUtil.toJsonStr(HttpResult.ERROR("the env is error,env=" + env));
        }

        log.info("[cycleGetAllItem],projectId={},env={},version={}",p.getId(),envId,version);
        String result = pullService.cycleGetAllItem(p.getId(), envId,version);
        return result;
    }

    @ApiOperation("itemchange")
    @GetMapping("itemchange")
    public void itemChange(Integer projectId,Integer envId,Integer version){
        pullService.itemChange(projectId, envId, version);
    }
}
