package com.ruoyi.web.controller.portal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.tongzhou.TzCategory;
import com.ruoyi.system.domain.tongzhou.TzQuestion;
import com.ruoyi.system.domain.tongzhou.dto.PortalAnswerBody;
import com.ruoyi.system.domain.tongzhou.dto.PortalProfileBody;
import com.ruoyi.system.domain.tongzhou.dto.PortalQuestionBody;
import com.ruoyi.system.domain.tongzhou.vo.PortalAnswerVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalProfileVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionDetailVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionListVo;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@RestController
@RequestMapping("/portal")
public class PortalCommunityController extends BaseController
{
    @Autowired
    private ITzCommunityService communityService;

    @GetMapping("/categories")
    public AjaxResult categories()
    {
        return AjaxResult.success(communityService.selectEnabledCategories());
    }

    @GetMapping("/questions")
    public AjaxResult questions(TzQuestion query)
    {
        startPage();
        List<PortalQuestionListVo> list = communityService.selectPortalQuestionList(query, tryGetPortalUserId());
        return AjaxResult.success(getDataTable(list));
    }

    @GetMapping("/questions/hot")
    public AjaxResult hotQuestions()
    {
        return AjaxResult.success(communityService.selectPortalHotQuestionList(6, tryGetPortalUserId()));
    }

    @GetMapping("/questions/{questionId}")
    public AjaxResult questionDetail(@PathVariable Long questionId)
    {
        PortalQuestionDetailVo detail = communityService.selectPortalQuestionDetail(questionId, tryGetPortalUserId());
        return AjaxResult.success(detail);
    }

    @PostMapping("/questions")
    public AjaxResult createQuestion(@RequestBody PortalQuestionBody body)
    {
        return toAjax(communityService.createQuestion(getPortalUserId(), body));
    }

    @GetMapping("/questions/mine")
    public AjaxResult myQuestions()
    {
        return AjaxResult.success(communityService.selectPortalMyQuestionList(getPortalUserId()));
    }

    @PostMapping("/questions/{questionId}/favorite")
    public AjaxResult favorite(@PathVariable Long questionId)
    {
        return toAjax(communityService.favoriteQuestion(questionId, getPortalUserId()));
    }

    @DeleteMapping("/questions/{questionId}/favorite")
    public AjaxResult unfavorite(@PathVariable Long questionId)
    {
        return toAjax(communityService.unfavoriteQuestion(questionId, getPortalUserId()));
    }

    @GetMapping("/favorites")
    public AjaxResult favorites()
    {
        return AjaxResult.success(communityService.selectPortalFavoriteQuestionList(getPortalUserId()));
    }

    @PostMapping("/questions/{questionId}/answers")
    public AjaxResult createAnswer(@PathVariable Long questionId, @RequestBody PortalAnswerBody body)
    {
        return toAjax(communityService.createAnswer(questionId, getPortalUserId(), body));
    }

    @GetMapping("/answers/mine")
    public AjaxResult myAnswers()
    {
        List<PortalAnswerVo> answers = communityService.selectPortalMyAnswerList(getPortalUserId());
        return AjaxResult.success(answers);
    }

    @GetMapping("/user/profile")
    public AjaxResult profile()
    {
        PortalProfileVo profile = communityService.selectPortalProfile(getPortalUserId());
        return AjaxResult.success(profile);
    }

    @PutMapping("/user/profile")
    public AjaxResult updateProfile(@RequestBody PortalProfileBody body)
    {
        return toAjax(communityService.updatePortalProfile(getPortalUserId(), body));
    }

    @GetMapping("/user/summary")
    public AjaxResult summary()
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("questions", communityService.selectPortalMyQuestionList(getPortalUserId()));
        ajax.put("answers", communityService.selectPortalMyAnswerList(getPortalUserId()));
        ajax.put("favorites", communityService.selectPortalFavoriteQuestionList(getPortalUserId()));
        return ajax;
    }

    private Long getPortalUserId()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null || loginUser.getUser() == null || !UserConstants.USER_TYPE_PORTAL.equals(loginUser.getUser().getUserType()))
        {
            throw new ServiceException("请先登录校园用户账号");
        }
        return loginUser.getUserId();
    }

    private Long tryGetPortalUserId()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (loginUser == null || loginUser.getUser() == null || !UserConstants.USER_TYPE_PORTAL.equals(loginUser.getUser().getUserType()))
        {
            return null;
        }
        return loginUser.getUserId();
    }
}
