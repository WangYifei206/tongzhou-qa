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
import com.ruoyi.system.domain.tongzhou.TzQuestion;
import com.ruoyi.system.domain.tongzhou.dto.TzReviewBody;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/tongzhou/question")
public class TzQuestionController extends BaseController
{
    @Autowired
    private ITzCommunityService communityService;

    @PreAuthorize("@ss.hasPermi('tongzhou:question:list')")
    @GetMapping("/list")
    public TableDataInfo list(TzQuestion question)
    {
        startPage();
        List<TzQuestion> list = communityService.selectAdminQuestionList(question);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:question:query')")
    @GetMapping("/{questionId}")
    public AjaxResult getInfo(@PathVariable Long questionId)
    {
        return AjaxResult.success(communityService.selectAdminQuestionById(questionId));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:question:review')")
    @PutMapping("/review")
    public AjaxResult review(@RequestBody TzReviewBody body)
    {
        return toAjax(communityService.reviewQuestion(body.getId(), body.getStatus(), body.getRejectReason(), SecurityUtils.getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('tongzhou:question:offline')")
    @PutMapping("/offline/{questionId}")
    public AjaxResult offline(@PathVariable Long questionId)
    {
        return toAjax(communityService.offlineQuestion(questionId, SecurityUtils.getUsername()));
    }
}
