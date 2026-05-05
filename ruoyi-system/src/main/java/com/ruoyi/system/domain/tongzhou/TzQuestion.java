package com.ruoyi.system.domain.tongzhou;

import com.ruoyi.common.core.domain.BaseEntity;

public class TzQuestion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long questionId;
    private Long categoryId;
    private Long userId;
    private String title;
    private String content;
    private String status;
    private String rejectReason;
    private String isAnonymous;
    private Integer viewCount;
    private Integer answerCount;
    private Integer favoriteCount;
    private String keyword;
    private String categoryName;
    private String publisherName;
    private String publisherNickName;
    private String publisherUserType;
    private String favorited;

    public Long getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(Long questionId)
    {
        this.questionId = questionId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRejectReason()
    {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
        this.rejectReason = rejectReason;
    }

    public String getIsAnonymous()
    {
        return isAnonymous;
    }

    public void setIsAnonymous(String isAnonymous)
    {
        this.isAnonymous = isAnonymous;
    }

    public Integer getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Integer viewCount)
    {
        this.viewCount = viewCount;
    }

    public Integer getAnswerCount()
    {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount)
    {
        this.answerCount = answerCount;
    }

    public Integer getFavoriteCount()
    {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount)
    {
        this.favoriteCount = favoriteCount;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getPublisherName()
    {
        return publisherName;
    }

    public void setPublisherName(String publisherName)
    {
        this.publisherName = publisherName;
    }

    public String getPublisherNickName()
    {
        return publisherNickName;
    }

    public void setPublisherNickName(String publisherNickName)
    {
        this.publisherNickName = publisherNickName;
    }

    public String getPublisherUserType()
    {
        return publisherUserType;
    }

    public void setPublisherUserType(String publisherUserType)
    {
        this.publisherUserType = publisherUserType;
    }

    public String getFavorited()
    {
        return favorited;
    }

    public void setFavorited(String favorited)
    {
        this.favorited = favorited;
    }
}
