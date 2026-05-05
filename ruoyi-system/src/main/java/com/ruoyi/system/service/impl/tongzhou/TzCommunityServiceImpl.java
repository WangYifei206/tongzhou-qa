package com.ruoyi.system.service.impl.tongzhou;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.TongzhouConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.tongzhou.TzAnswer;
import com.ruoyi.system.domain.tongzhou.TzCategory;
import com.ruoyi.system.domain.tongzhou.TzQuestion;
import com.ruoyi.system.domain.tongzhou.TzUserProfile;
import com.ruoyi.system.domain.tongzhou.dto.PortalAnswerBody;
import com.ruoyi.system.domain.tongzhou.dto.PortalProfileBody;
import com.ruoyi.system.domain.tongzhou.dto.PortalQuestionBody;
import com.ruoyi.system.domain.tongzhou.vo.PortalAnswerVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalProfileVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionDetailVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionListVo;
import com.ruoyi.system.domain.tongzhou.vo.TzOverviewVo;
import com.ruoyi.system.mapper.tongzhou.TzCommunityMapper;
import com.ruoyi.system.service.tongzhou.ITzCommunityService;

@Service
public class TzCommunityServiceImpl implements ITzCommunityService
{
    @Autowired
    private TzCommunityMapper communityMapper;

    @Override
    public List<TzCategory> selectCategoryList(TzCategory category)
    {
        return communityMapper.selectCategoryList(category);
    }

    @Override
    public List<TzCategory> selectEnabledCategories()
    {
        return communityMapper.selectEnabledCategories();
    }

    @Override
    public TzCategory selectCategoryById(Long categoryId)
    {
        return communityMapper.selectCategoryById(categoryId);
    }

    @Override
    public int insertCategory(TzCategory category)
    {
        if (StringUtils.isEmpty(category.getStatus()))
        {
            category.setStatus(UserConstants.NORMAL);
        }
        return communityMapper.insertCategory(category);
    }

    @Override
    public int updateCategory(TzCategory category)
    {
        return communityMapper.updateCategory(category);
    }

    @Override
    public int deleteCategoryByIds(Long[] categoryIds)
    {
        return communityMapper.deleteCategoryByIds(categoryIds);
    }

    @Override
    public PortalProfileVo selectPortalProfile(Long userId)
    {
        TzUserProfile profile = communityMapper.selectUserProfileByUserId(userId);
        if (profile == null)
        {
            return null;
        }
        PortalProfileVo vo = new PortalProfileVo();
        vo.setUserId(profile.getUserId());
        vo.setUserName(profile.getUserName());
        vo.setNickName(profile.getNickName());
        vo.setEmail(profile.getEmail());
        vo.setPhonenumber(profile.getPhonenumber());
        vo.setAvatar(profile.getAvatar());
        vo.setCollege(profile.getCollege());
        vo.setGrade(profile.getGrade());
        vo.setMajor(profile.getMajor());
        vo.setIntroduction(profile.getIntroduction());
        vo.setCreateTime(profile.getCreateTime());
        TzOverviewVo overview = communityMapper.selectOverview();
        List<PortalQuestionListVo> myQuestions = selectPortalMyQuestionList(userId);
        List<PortalAnswerVo> myAnswers = selectPortalMyAnswerList(userId);
        List<PortalQuestionListVo> favorites = selectPortalFavoriteQuestionList(userId);
        vo.setQuestionCount(myQuestions.size());
        vo.setAnswerCount(myAnswers.size());
        vo.setFavoriteCount(favorites.size());
        if (overview == null)
        {
            vo.setQuestionCount(0);
            vo.setAnswerCount(0);
            vo.setFavoriteCount(0);
        }
        return vo;
    }

    @Override
    public int updatePortalProfile(Long userId, PortalProfileBody body)
    {
        TzUserProfile profile = communityMapper.selectUserProfileByUserId(userId);
        if (profile == null)
        {
            throw new ServiceException("用户档案不存在");
        }
        profile.setNickName(body.getNickName());
        profile.setEmail(body.getEmail());
        profile.setPhonenumber(body.getPhonenumber());
        profile.setCollege(body.getCollege());
        profile.setGrade(body.getGrade());
        profile.setMajor(body.getMajor());
        profile.setIntroduction(body.getIntroduction());
        return communityMapper.updateUserProfile(profile);
    }

    @Override
    public List<PortalProfileVo> selectPortalUserProfiles(TzUserProfile profile)
    {
        return communityMapper.selectPortalUserProfiles(profile);
    }

    @Override
    public List<PortalQuestionListVo> selectPortalQuestionList(TzQuestion query, Long userId)
    {
        return communityMapper.selectPortalQuestionList(query, userId);
    }

    @Override
    public List<PortalQuestionListVo> selectPortalHotQuestionList(Integer limit, Long userId)
    {
        return communityMapper.selectPortalHotQuestionList(limit, userId);
    }

    @Override
    public PortalQuestionDetailVo selectPortalQuestionDetail(Long questionId, Long userId)
    {
        PortalQuestionDetailVo detail = communityMapper.selectPortalQuestionDetail(questionId, userId);
        if (detail == null)
        {
            throw new ServiceException("问题不存在");
        }
        boolean isOwner = userId != null && userId.equals(detail.getUserId());
        if (!TongzhouConstants.STATUS_APPROVED.equals(detail.getStatus()) && !isOwner)
        {
            throw new ServiceException("该问题暂不可查看");
        }
        communityMapper.increaseQuestionViewCount(questionId);
        detail.setAnswers(communityMapper.selectApprovedAnswersByQuestionId(questionId));
        if (detail.getAnswers() == null)
        {
            detail.setAnswers(Collections.emptyList());
        }
        return detail;
    }

    @Override
    @Transactional
    public int createQuestion(Long userId, PortalQuestionBody body)
    {
        TzQuestion question = new TzQuestion();
        question.setUserId(userId);
        question.setCategoryId(body.getCategoryId());
        question.setTitle(body.getTitle());
        question.setContent(body.getContent());
        question.setIsAnonymous(StringUtils.isEmpty(body.getIsAnonymous()) ? TongzhouConstants.YES : body.getIsAnonymous());
        question.setStatus(TongzhouConstants.STATUS_PENDING);
        question.setViewCount(0);
        question.setAnswerCount(0);
        question.setFavoriteCount(0);
        return communityMapper.insertQuestion(question);
    }

    @Override
    public List<PortalQuestionListVo> selectPortalMyQuestionList(Long userId)
    {
        return communityMapper.selectPortalMyQuestionList(userId);
    }

    @Override
    public List<PortalQuestionListVo> selectPortalFavoriteQuestionList(Long userId)
    {
        return communityMapper.selectPortalFavoriteQuestionList(userId);
    }

    @Override
    @Transactional
    public int favoriteQuestion(Long questionId, Long userId)
    {
        if (communityMapper.countQuestionFavorite(questionId, userId) > 0)
        {
            return 1;
        }
        int rows = communityMapper.insertQuestionFavorite(questionId, userId);
        communityMapper.recountQuestionFavoriteCount(questionId);
        return rows;
    }

    @Override
    @Transactional
    public int unfavoriteQuestion(Long questionId, Long userId)
    {
        int rows = communityMapper.deleteQuestionFavorite(questionId, userId);
        communityMapper.recountQuestionFavoriteCount(questionId);
        return rows;
    }

    @Override
    @Transactional
    public int createAnswer(Long questionId, Long userId, PortalAnswerBody body)
    {
        TzQuestion question = communityMapper.selectQuestionById(questionId);
        if (question == null || !TongzhouConstants.STATUS_APPROVED.equals(question.getStatus()))
        {
            throw new ServiceException("仅可回答已通过审核的问题");
        }
        TzAnswer answer = new TzAnswer();
        answer.setQuestionId(questionId);
        answer.setUserId(userId);
        answer.setContent(body.getContent());
        answer.setIsAnonymous(StringUtils.isEmpty(body.getIsAnonymous()) ? TongzhouConstants.YES : body.getIsAnonymous());
        answer.setStatus(TongzhouConstants.STATUS_PENDING);
        answer.setLikeCount(0);
        int rows = communityMapper.insertAnswer(answer);
        communityMapper.recountQuestionAnswerCount(questionId);
        return rows;
    }

    @Override
    public List<PortalAnswerVo> selectPortalMyAnswerList(Long userId)
    {
        return communityMapper.selectPortalMyAnswerList(userId);
    }

    @Override
    public List<TzQuestion> selectAdminQuestionList(TzQuestion question)
    {
        return communityMapper.selectAdminQuestionList(question);
    }

    @Override
    public TzQuestion selectAdminQuestionById(Long questionId)
    {
        TzQuestion question = communityMapper.selectQuestionById(questionId);
        if (question == null)
        {
            throw new ServiceException("问题不存在");
        }
        return question;
    }

    @Override
    public int reviewQuestion(Long questionId, String status, String rejectReason, String operator)
    {
        if (!TongzhouConstants.STATUS_APPROVED.equals(status) && !TongzhouConstants.STATUS_REJECTED.equals(status))
        {
            throw new ServiceException("审核状态不合法");
        }
        TzQuestion question = new TzQuestion();
        question.setQuestionId(questionId);
        question.setStatus(status);
        question.setRejectReason(rejectReason);
        question.setUpdateBy(operator);
        return communityMapper.updateQuestion(question);
    }

    @Override
    public int offlineQuestion(Long questionId, String operator)
    {
        TzQuestion question = new TzQuestion();
        question.setQuestionId(questionId);
        question.setStatus(TongzhouConstants.STATUS_OFFLINE);
        question.setUpdateBy(operator);
        return communityMapper.updateQuestion(question);
    }

    @Override
    public List<TzAnswer> selectAdminAnswerList(TzAnswer answer)
    {
        return communityMapper.selectAdminAnswerList(answer);
    }

    @Override
    public TzAnswer selectAdminAnswerById(Long answerId)
    {
        TzAnswer answer = communityMapper.selectAnswerById(answerId);
        if (answer == null)
        {
            throw new ServiceException("回答不存在");
        }
        return answer;
    }

    @Override
    @Transactional
    public int reviewAnswer(Long answerId, String status, String rejectReason, String operator)
    {
        if (!TongzhouConstants.STATUS_APPROVED.equals(status) && !TongzhouConstants.STATUS_REJECTED.equals(status))
        {
            throw new ServiceException("审核状态不合法");
        }
        TzAnswer answer = communityMapper.selectAnswerById(answerId);
        if (answer == null)
        {
            throw new ServiceException("回答不存在");
        }
        TzAnswer update = new TzAnswer();
        update.setAnswerId(answerId);
        update.setStatus(status);
        update.setRejectReason(rejectReason);
        update.setUpdateBy(operator);
        int rows = communityMapper.updateAnswer(update);
        communityMapper.recountQuestionAnswerCount(answer.getQuestionId());
        return rows;
    }

    @Override
    @Transactional
    public int offlineAnswer(Long answerId, String operator)
    {
        TzAnswer answer = communityMapper.selectAnswerById(answerId);
        if (answer == null)
        {
            throw new ServiceException("回答不存在");
        }
        TzAnswer update = new TzAnswer();
        update.setAnswerId(answerId);
        update.setStatus(TongzhouConstants.STATUS_OFFLINE);
        update.setUpdateBy(operator);
        int rows = communityMapper.updateAnswer(update);
        communityMapper.recountQuestionAnswerCount(answer.getQuestionId());
        return rows;
    }

    @Override
    public TzOverviewVo selectOverview()
    {
        return communityMapper.selectOverview();
    }
}
