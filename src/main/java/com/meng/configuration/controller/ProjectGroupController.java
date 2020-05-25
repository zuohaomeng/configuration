package com.meng.configuration.controller;

import com.meng.configuration.entity.Project;
import com.meng.configuration.entity.ProjectGroup;
import com.meng.configuration.entity.vo.GroupUserVo;
import com.meng.configuration.service.ProjectGroupService;
import com.meng.configuration.service.UserService;
import com.meng.configuration.util.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author 梦醉
 * @date 2020/3/11--1:26
 */
@Slf4j
@Controller
@RequestMapping("/projectgroup")
@PreAuthorize("hasRole('permission')")
public class ProjectGroupController {
    @Resource
    private ProjectGroupService groupService;

    @Resource
    private UserService userService;

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
     * @param groupid
     * @param model
     * @return
     */
    @GetMapping("/to-groupAddUser")
    public String toGroupAddUser(Integer groupid, Model model) {
        model.addAttribute("groupid", groupid);
        return "projectgroup/projectgroupAddUser";
    }

    /**
     * @param groupid
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/getgroupuser")
    public HashMap getGroupUser(Integer groupid, Model model) {
        HashMap map = new HashMap<>();
        List<GroupUserVo> allUsers = userService.getAllUsers();
        List<Integer> useridBelongGroup = userService.getUseridBelongGroup(groupid);
        map.put("data1", allUsers);
        map.put("data2", useridBelongGroup);
        return map;
    }
    @ResponseBody
    @GetMapping("/changegroupuser")
    public String changeGroupUser(Integer index,Integer userid,Integer groupid){
        log.info("[changeGroupUser],参数,index={},userid={},groupid={}",index,userid,groupid);
        if(index == 0){
            groupService.addGroupUser(groupid, userid);
        }else {
            groupService.deleteGroupUser(groupid, userid);
        }
        return null;
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
    public HashMap list(int page, int limit, HttpServletRequest request) {
        log.info("[projectgroup list],page={},limit={}", page, limit);
        System.out.println(request.getSession().getMaxInactiveInterval());
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
        if (result <= 0) {
            return ResponseModel.ERROR("修改失败");
        }
        return ResponseModel.SUCCESS();
    }

    @ResponseBody
    @GetMapping("delete")
    public ResponseModel delete(Integer id) {
        int result = groupService.delete(id);
        if (result <= 0) {
            return ResponseModel.ERROR("删除失败");
        }
        return ResponseModel.SUCCESS();
    }

    @ResponseBody
    @GetMapping("/search")
    public HashMap search(String portion) {
        log.info("[project search],portion={}", portion);
        List<ProjectGroup> projects = groupService.searchByprojectName(portion);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", projects.size());
        map.put("data", projects);
        return map;
    }
}
