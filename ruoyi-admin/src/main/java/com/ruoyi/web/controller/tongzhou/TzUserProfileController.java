package com.ruoyi.web.controller.tongzhou;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.tongzhou.TzUserProfile;
import com.ruoyi.system.domain.tongzhou.vo.PortalProfileVo;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/tongzhou/user")
public class TzUserProfileController extends BaseController
{
    @Autowired
    private ITzCommunityService communityService;

    @PreAuthorize("@ss.hasPermi('tongzhou:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(TzUserProfile profile)
    {
        startPage();
        List<PortalProfileVo> list = communityService.selectPortalUserProfiles(profile);
        return getDataTable(list);
    }
}
