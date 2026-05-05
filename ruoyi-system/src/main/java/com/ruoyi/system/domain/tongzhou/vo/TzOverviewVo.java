package com.ruoyi.system.domain.tongzhou.vo;

public class TzOverviewVo
{
    private Integer questionCount;
    private Integer answerCount;
    private Integer categoryCount;
    private Integer pendingQuestionCount;
    private Integer pendingAnswerCount;
    private Integer favoriteCount;

    public Integer getQuestionCount()
    {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount)
    {
        this.questionCount = questionCount;
    }

    public Integer getAnswerCount()
    {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount)
    {
        this.answerCount = answerCount;
    }

    public Integer getCategoryCount()
    {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount)
    {
        this.categoryCount = categoryCount;
    }

    public Integer getPendingQuestionCount()
    {
        return pendingQuestionCount;
    }

    public void setPendingQuestionCount(Integer pendingQuestionCount)
    {
        this.pendingQuestionCount = pendingQuestionCount;
    }

    public Integer getPendingAnswerCount()
    {
        return pendingAnswerCount;
    }

    public void setPendingAnswerCount(Integer pendingAnswerCount)
    {
        this.pendingAnswerCount = pendingAnswerCount;
    }

    public Integer getFavoriteCount()
    {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount)
    {
        this.favoriteCount = favoriteCount;
    }
}
