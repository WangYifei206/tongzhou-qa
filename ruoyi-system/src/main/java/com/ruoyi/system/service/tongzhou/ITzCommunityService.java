package com.ruoyi.system.service.tongzhou;

import java.util.List;
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

public interface ITzCommunityService
{
    List<TzCategory> selectCategoryList(TzCategory category);

    List<TzCategory> selectEnabledCategories();

    TzCategory selectCategoryById(Long categoryId);

    int insertCategory(TzCategory category);

    int updateCategory(TzCategory category);

    int deleteCategoryByIds(Long[] categoryIds);

    PortalProfileVo selectPortalProfile(Long userId);

    int updatePortalProfile(Long userId, PortalProfileBody body);

    List<PortalProfileVo> selectPortalUserProfiles(TzUserProfile profile);

    List<PortalQuestionListVo> selectPortalQuestionList(TzQuestion query, Long userId);

    List<PortalQuestionListVo> selectPortalHotQuestionList(Integer limit, Long userId);

    PortalQuestionDetailVo selectPortalQuestionDetail(Long questionId, Long userId);

    int createQuestion(Long userId, PortalQuestionBody body);

    List<PortalQuestionListVo> selectPortalMyQuestionList(Long userId);

    List<PortalQuestionListVo> selectPortalFavoriteQuestionList(Long userId);

    int favoriteQuestion(Long questionId, Long userId);

    int unfavoriteQuestion(Long questionId, Long userId);

    int createAnswer(Long questionId, Long userId, PortalAnswerBody body);

    List<PortalAnswerVo> selectPortalMyAnswerList(Long userId);

    List<TzQuestion> selectAdminQuestionList(TzQuestion question);

    TzQuestion selectAdminQuestionById(Long questionId);

    int reviewQuestion(Long questionId, String status, String rejectReason, String operator);

    int offlineQuestion(Long questionId, String operator);

    List<TzAnswer> selectAdminAnswerList(TzAnswer answer);

    TzAnswer selectAdminAnswerById(Long answerId);

    int reviewAnswer(Long answerId, String status, String rejectReason, String operator);

    int offlineAnswer(Long answerId, String operator);

    TzOverviewVo selectOverview();
}
