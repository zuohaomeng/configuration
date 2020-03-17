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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
    @Resource
    private ProjectService projectService;

    @RequestMapping
    public String item(Integer projectId, Model model) {
        model.addAttribute("projectId", projectId);
        return "item/item";
    }

    @RequestMapping("to-add")
    public String toAdd(Integer projectId, Model model) {
        log.info("[ConfigurationItemController toadd]");
        model.addAttribute("projectId", projectId);
        return "item/itemAdd";
    }

    /**
     * 跳转update页面
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


    @ResponseBody
    @RequestMapping("list")
    public HashMap list(int page,int limit,int projectId) {
        List<ConfigurationItemVo> items = configurationItemService.selectVoByPage(page, limit,projectId);
        HashMap map = new HashMap();
        map.put("code", 0);
        map.put("msg", "成功");
        map.put("count", configurationItemService.getCountByProjectId(projectId));
        map.put("data", items);
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

    @ResponseBody
    @RequestMapping("update")
    public ResponseModel update(@RequestBody ConfigurationItem item){
        int update = configurationItemService.update(item);
        return ResponseModel.SUCCESS();
    }

    @ResponseBody
    @RequestMapping("delete")
    public ResponseModel delete(Integer id){
        int result = configurationItemService.delete(id);
        if(result>0){
            return ResponseModel.SUCCESS();
        }
        return ResponseModel.ERROR("删除失败");
    }

    @ResponseBody
    @RequestMapping("release")
    public ResponseModel release(Integer projectId){
        int result = configurationItemService.release(projectId);
        if(result==-2){
            return ResponseModel.SUCCESS("没有修改！");
        }
        return ResponseModel.SUCCESS("发布成功！");
    }
    @RequestMapping("roll-black")
    public ResponseModel rollBlack(Integer projectId){
        configurationItemService.rollBalck(projectId);

        return ResponseModel.SUCCESS();
    }
}
