package com.meng.configuration.controller;

import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.service.ProjectGroupService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 梦醉
 * @date 2020/3/11--1:26
 */
@Slf4j
@Controller
@RequestMapping("/projectgroup")
public class ProjectGroupController {
    @Resource
    private ProjectGroupService groupService;

    /**
     * 跳转项目组页面
     *
     * @return
     */
    @GetMapping()
    public String toProjectGroup() {
        return "projectgroup/projectgroup";
    }

    /**
     * 跳转添加页面
     *
     * @return
     */
    @GetMapping("/to-add")
    public String toAdd() {
        return "projectgroup/projectgroupAdd";
    }

    /**
     * 更新页面
     *
     * @return
     */
    @GetMapping("/to-update")
    public String toUpdate(Integer id, Model model) {
        log.info("[projectGroup update],id={}", id);
        ProjectGroup projectGroup = groupService.selectById(id);
        model.addAttribute("pg", projectGroup);
        return "projectgroup/projectgroupUpdate";
    }

    /**
     * 获取项目组
     *
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public HashMap list(int page, int limit) {
        log.info("[projectgroup list],page={},limit={}", page, limit);
        List<ProjectGroup> projectGroups = groupService.selectGroupBypage(page, limit);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", groupService.getCount());
        map.put("data", projectGroups);
        return map;
    }

    @ResponseBody
    @PostMapping("add")
    public ResponseModel add(@RequestBody ProjectGroup projectGroup) {
        if (StringUtils.isEmpty(projectGroup.getGroupName())
                || StringUtils.isEmpty(projectGroup.getLeaderName())
                || StringUtils.isEmpty(projectGroup.getEmail())) {
            return ResponseModel.ERROR("信息未填完。");
        }
        ResponseModel responseModel = groupService.add(projectGroup);
        return responseModel;
    }

    @ResponseBody
    @PostMapping("update")
    public ResponseModel update(@RequestBody ProjectGroup projectGroup) {
        if (StringUtils.isEmpty(projectGroup.getGroupName())
                || StringUtils.isEmpty(projectGroup.getLeaderName())
                || StringUtils.isEmpty(projectGroup.getEmail())) {
            return ResponseModel.ERROR("信息未填完。");
        }
        int result = groupService.update(projectGroup);
        if(result<=0){
            return ResponseModel.ERROR("修改失败");
        }
        return ResponseModel.SUCCESS();
    }

    @ResponseBody
    @GetMapping("delete")
    public ResponseModel delete(Integer id){
        int result = groupService.delete(id);
        if(result<=0){
            return ResponseModel.ERROR("删除失败");
        }
        return ResponseModel.SUCCESS();
    }
    @ResponseBody
    @GetMapping("/search")
    public HashMap search(String portion){
        log.info("[project search],portion={}",portion);
        List<ProjectGroup> projects = groupService.searchByprojectName(portion);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", projects.size());
        map.put("data", projects);
        return map;
    }
}