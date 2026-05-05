package com.ruoyi.system.domain.tongzhou.vo;

import java.util.List;

public class PortalQuestionDetailVo extends PortalQuestionListVo
{
    private List<PortalAnswerVo> answers;

    public List<PortalAnswerVo> getAnswers()
    {
        return answers;
    }

    public void setAnswers(List<PortalAnswerVo> answers)
    {
        this.answers = answers;
    }
}
