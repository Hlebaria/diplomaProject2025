package com.choiceApp.MyChoiceApp.models.DTOs;
import java.util.List;

public class VoteDTO {

    private Integer questionId;
    private List<Integer> choiceIds;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Integer> choiceIds) {
        this.choiceIds = choiceIds;
    }
}
