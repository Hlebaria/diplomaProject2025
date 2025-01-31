package com.choiceApp.MyChoiceApp.models.DTOs;

import com.choiceApp.MyChoiceApp.models.Choice;

public class FullChoiceDTO {

    private Integer choiceId;
    private String text;
    private Integer votes;

    public FullChoiceDTO(Choice choice){
        setChoiceId(choice.getId());
        setText(choice.getChoiceText());
        setVotes(choice.getVotesCount());
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
