package com.ruoyi.web.controller.tongzhou;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.tongzhou.TzAnswer;
import com.ruoyi.system.domain.tongzhou.dto.TzReviewBody;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/tongzhou/answer")
public class TzAnswerController extends BaseController
{
    @Autowired
    private ITzCommunityService communityService;

    @PreAuthorize("@ss.hasPermi('tongzhou:answer:list')")
    @GetMapping("/list")
    public TableDataInfo list(TzAnswer answer)
    {
        startPage();
        List<TzAnswer> list = communityService.selectAdminAnswerList(answer);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:answer:query')")
    @GetMapping("/{answerId}")
    public AjaxResult getInfo(@PathVariable Long answerId)
    {
        return AjaxResult.success(communityService.selectAdminAnswerById(answerId));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:answer:review')")
    @PutMapping("/review")
    public AjaxResult review(@RequestBody TzReviewBody body)
    {
        return toAjax(communityService.reviewAnswer(body.getId(), body.getStatus(), body.getRejectReason(), SecurityUtils.getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:answer:offline')")
    @PutMapping("/offline/{answerId}")
    public AjaxResult offline(@PathVariable Long answerId)
    {
        return toAjax(communityService.offlineAnswer(answerId, SecurityUtils.getUsername()));
    }
}
