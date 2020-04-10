package com.meng.configuration.controller;

import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.entity.vo.ProjectVo;
import com.meng.configuration.service.ProjectGroupService;
import com.meng.configuration.service.ProjectService;
import com.meng.configuration.util.ResponseModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
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
    @Resource
    private ProjectGroupService projectGroupService;

    /**
     * 跳转项目页面
     *
     * @return
     */
    @GetMapping()
    public String project() {
        return "project/project";
    }

    /**
     * 跳转项目添加页面
     *
     * @return
     */
    @GetMapping("to-project-add")
    @ApiOperation("to-project-add")
    public String toProjectAdd(Model model) {
        List<ProjectGroup> projectGroups = projectGroupService.selectAllProjectGroup();
        model.addAttribute("pgs", projectGroups);
        return "project/projectAdd";
    }

    /**
     * 跳转到修改页面
     *
     * @return
     */
    @GetMapping("to-update")
    @ApiOperation("to-update")
    public String toUpdate(@RequestParam Integer id, Model model) {
        log.info("[toUpdate],id", id);
        Project project = projectService.selectById(id);
        model.addAttribute("project", project);
        List<ProjectGroup> projectGroups = projectGroupService.selectAllProjectGroup();
        model.addAttribute("pgs", projectGroups);
        return "project/projectUpdate";
    }

    /**
     * 获取项目列表
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Map<String, Object> list(int page, int limit) {
        log.info("[list project]");
        List<ProjectVo> projects = projectService.selectAllProject(page, limit);
        Map map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", projectService.getCount());
        map.put("data", projects);
        return map;
    }

    /**
     * 添加项目
     *
     * @param project
     * @return
     */
    @ApiOperation("project/add")
    @PostMapping("/add")
    @ResponseBody
    public ResponseModel add(@RequestBody Project project) {
        if (StringUtils.isEmpty(project) || StringUtils.isEmpty(project.getProjectId())
                || StringUtils.isEmpty(project.getProjectName()) || StringUtils.isEmpty(project.getEmail())
                || StringUtils.isEmpty(project.getLeaderName())) {
            return ResponseModel.ERROR("请填写所有必选项!");
        }
        if (project.getGroupId() < 0) {
            return ResponseModel.ERROR("请选择项目组!");
        }
        ResponseModel responseModel = projectService.add(project);
        return responseModel;
    }

    /**
     * 更新项目
     *
     * @param project
     * @return
     */
    @ResponseBody
    @ApiOperation("project/update")
    @PostMapping("/update")
    public ResponseModel update(@RequestBody Project project) {
        log.info("[project update],project={}", project.toString());
        Integer result = projectService.update(project);
        if (result > 0) {
            return ResponseModel.SUCCESS(result);
        } else {
            return ResponseModel.ERROR("更新失败");
        }

    }

    /**
     * 删除项目
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/delete")
    public ResponseModel delete(Integer id) {
        log.info("[project update],id={}", id);
        Integer result = projectService.delete(id);
        if (result > 0) {
            return ResponseModel.SUCCESS(result);
        } else {
            return ResponseModel.ERROR("删除失败");
        }

    }

    @ResponseBody
    @GetMapping("/search")
    public HashMap search(String portion) {
        log.info("[project search],portion={}", portion);
        List<ProjectVo> projects = projectService.searchByprojectName(portion);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", projects.size());
        map.put("data", projects);
        return map;
    }


}
