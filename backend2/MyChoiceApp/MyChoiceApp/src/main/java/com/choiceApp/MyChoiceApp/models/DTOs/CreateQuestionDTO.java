package com.choiceApp.MyChoiceApp.models.DTOs;

import java.util.List;

public class CreateQuestionDTO {

    private String questionText;
    private Integer maxVotes;
    private Integer minVotes;
    private List<String> choiceTexts;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getMaxVotes() {
        return maxVotes;
    }

    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    public Integer getMinVotes() {
        return minVotes;
    }

    public void setMinVotes(Integer minVotes) {
        this.minVotes = minVotes;
    }

    public List<String> getChoiceTexts() {
        return choiceTexts;
    }

    public void setChoiceTexts(List<String> choiceTexts) {
        this.choiceTexts = choiceTexts;
    }
}
