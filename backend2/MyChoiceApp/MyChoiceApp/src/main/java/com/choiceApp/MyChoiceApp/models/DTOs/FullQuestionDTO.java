package com.choiceApp.MyChoiceApp.models.DTOs;

import com.choiceApp.MyChoiceApp.models.Choice;
import com.choiceApp.MyChoiceApp.models.Question;

import java.util.ArrayList;
import java.util.List;

public class FullQuestionDTO {

    private Integer questionId;
    private String questionText;
    private Integer maxVotes;
    private Integer minVotes;
    private List<FullChoiceDTO> choices;

    public FullQuestionDTO (Question question, boolean showResults){
        setQuestionId(question.getId());
        setQuestionText(question.getQuestionText());
        setMaxVotes(question.getMaxVoterChoices());
        setMinVotes(question.getMinVoterChoices());

        choices = new ArrayList<>(List.of());

        for(Choice choice : question.getChoices()){
            FullChoiceDTO choiceDTO = new FullChoiceDTO(choice);
            if(!showResults){
                choice.setVotesCount(0);
            }
            choices.add(choiceDTO);
        }

    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

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

    public List<FullChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<FullChoiceDTO> choices) {
        this.choices = choices;
    }
}
