package com.ruoyi.web.controller.tongzhou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/tongzhou/dashboard")
public class TzDashboardController
{
    @Autowired
    private ITzCommunityService communityService;

    @PreAuthorize("@ss.hasPermi('tongzhou:dashboard:view')")
    @GetMapping("/overview")
    public AjaxResult overview()
    {
        return AjaxResult.success(communityService.selectOverview());
    }
}
