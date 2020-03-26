package com.meng.configuration.controller;

import com.meng.configuration.entity.vo.HistoryDetailVo;
import com.meng.configuration.entity.vo.HistoryVo;
import com.meng.configuration.service.ReleaseHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HAO.ZUO
 * @date 2020/3/26--19:54
 */
@Slf4j
@Controller
@RequestMapping("history")
public class ReleaseHistoryController {

    @Resource
    private ReleaseHistoryService releaseHistoryService;

    @RequestMapping
    public String releaseHistory(Integer projectId, Integer env,
                                 @RequestParam(required = false, defaultValue = "-1") Integer version, Model model) {
        log.info("[release releaseHistory],projectId={},env={},version={}", projectId, env, version);
        //获取发布列表
        List<HistoryVo> historyList = releaseHistoryService.getHistoryList(projectId, env);

        //获取详情
        if (version == -1) {
            version = historyList.get(0).getVersion();
        }
        List<HistoryDetailVo> historyDetail = releaseHistoryService.getHistoryDetail(projectId, env, version);

        model.addAttribute("hlist", historyList);
        model.addAttribute("detail", historyDetail);
        model.addAttribute("projectId", projectId);
        model.addAttribute("env", env);
        return "item/releasehistory";
    }
}
