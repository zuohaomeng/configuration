package com.meng.configuration.controller;

import com.meng.configuration.entity.ConfigurationItem;
import com.meng.configuration.entity.vo.ConfigurationItemVo;
import com.meng.configuration.service.ConfigurationItemService;
import com.meng.configuration.service.ProjectService;
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
 * 配置项管理
 *
 * @author 梦醉
 * @date 2020/3/12--0:45
 */
@Slf4j
@Controller
@RequestMapping("/item")
public class ConfigurationItemController {

    @Resource
    private ConfigurationItemService configurationItemService;
    @Resource
    private ProjectService projectService;

    @RequestMapping
    public String item(Integer projectId, @RequestParam(required = false, defaultValue = "1") Integer env, Model model) {
        log.info("[item item,env={}]",env);
        model.addAttribute("projectId", projectId);
        model.addAttribute("env", env);
        return "item/item";
    }

    @RequestMapping("to-add")
    public String toAdd(Integer projectId,@RequestParam(required = false, defaultValue = "1") Integer env,
                        Model model) {
        log.info("[ConfigurationItemController toadd]");
        model.addAttribute("projectId", projectId);
        model.addAttribute("env", env);
        return "item/itemAdd";
    }

    /**
     * 跳转update页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("to-update")
    public String toUpdate(Integer id, Model model) {
        ConfigurationItem item = configurationItemService.selectById(id);
        model.addAttribute("item", item);
        return "item/itemUpdate";
    }

    /**
     * 配置项分页
     *
     * @param page
     * @param limit
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping("list")
    public HashMap list(int page, int limit, @RequestParam(required = false, defaultValue = "1") Integer env, int projectId) {
        if (env == null || env <= 0) {
            env = 1;
        }
        List<ConfigurationItemVo> items = configurationItemService.selectVoByPage(page, limit, env, projectId);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", configurationItemService.getCountByProjectId(projectId, env));
        map.put("data", items);
        return map;
    }

    /**
     * 添加
     * @param configurationItem
     * @return
     */
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


    /**
     * 更新
     *
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public ResponseModel update(@RequestBody ConfigurationItem item) {
        int update = configurationItemService.update(item);
        return ResponseModel.SUCCESS();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public ResponseModel delete(Integer id) {
        int result = configurationItemService.delete(id);
        if (result > 0) {
            return ResponseModel.SUCCESS("删除成功");
        }
        return ResponseModel.ERROR("删除失败");
    }


    /**
     * 发布
     *
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping("release")
    public ResponseModel release(Integer projectId,Integer env) {
        int result = configurationItemService.release(projectId,env);
        if (result == -2) {
            return ResponseModel.SUCCESS("没有修改！");
        }
        return ResponseModel.SUCCESS("发布成功！");
    }


    /**
     * 回滚
     *
     * @param projectId
     * @return
     */
    @ResponseBody
    @RequestMapping("roll-black")
    public ResponseModel rollBlack(Integer projectId,Integer env) {
        log.info("roll back");
        configurationItemService.rollBalck(projectId,env);
        return ResponseModel.SUCCESS();
    }
}
