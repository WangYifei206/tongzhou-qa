package com.ruoyi.system.mapper.tongzhou;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.tongzhou.TzAnswer;
import com.ruoyi.system.domain.tongzhou.TzCategory;
import com.ruoyi.system.domain.tongzhou.TzQuestion;
import com.ruoyi.system.domain.tongzhou.TzUserProfile;
import com.ruoyi.system.domain.tongzhou.vo.PortalAnswerVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalProfileVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionDetailVo;
import com.ruoyi.system.domain.tongzhou.vo.PortalQuestionListVo;
import com.ruoyi.system.domain.tongzhou.vo.TzOverviewVo;

public interface TzCommunityMapper
{
    List<TzCategory> selectCategoryList(TzCategory category);

    List<TzCategory> selectEnabledCategories();

    TzCategory selectCategoryById(Long categoryId);

    int insertCategory(TzCategory category);

    int updateCategory(TzCategory category);

    int deleteCategoryByIds(Long[] categoryIds);

    TzUserProfile selectUserProfileByUserId(Long userId);

    int insertUserProfile(TzUserProfile profile);

    int updateUserProfile(TzUserProfile profile);

    List<PortalProfileVo> selectPortalUserProfiles(TzUserProfile profile);

    List<PortalQuestionListVo> selectPortalQuestionList(@Param("query") TzQuestion query, @Param("userId") Long userId);

    List<PortalQuestionListVo> selectPortalHotQuestionList(@Param("limit") Integer limit, @Param("userId") Long userId);

    PortalQuestionDetailVo selectPortalQuestionDetail(@Param("questionId") Long questionId, @Param("userId") Long userId);

    List<PortalQuestionListVo> selectPortalMyQuestionList(Long userId);

    List<PortalQuestionListVo> selectPortalFavoriteQuestionList(Long userId);

    int insertQuestion(TzQuestion question);

    int updateQuestion(TzQuestion question);

    int increaseQuestionViewCount(Long questionId);

    TzQuestion selectQuestionById(Long questionId);

    List<TzQuestion> selectAdminQuestionList(TzQuestion question);

    List<PortalAnswerVo> selectApprovedAnswersByQuestionId(Long questionId);

    List<PortalAnswerVo> selectPortalMyAnswerList(Long userId);

    int insertAnswer(TzAnswer answer);

    int updateAnswer(TzAnswer answer);

    TzAnswer selectAnswerById(Long answerId);

    List<TzAnswer> selectAdminAnswerList(TzAnswer answer);

    int insertQuestionFavorite(@Param("questionId") Long questionId, @Param("userId") Long userId);

    int deleteQuestionFavorite(@Param("questionId") Long questionId, @Param("userId") Long userId);

    int countQuestionFavorite(@Param("questionId") Long questionId, @Param("userId") Long userId);

    int recountQuestionAnswerCount(Long questionId);

    int recountQuestionFavoriteCount(Long questionId);

    TzOverviewVo selectOverview();
}
