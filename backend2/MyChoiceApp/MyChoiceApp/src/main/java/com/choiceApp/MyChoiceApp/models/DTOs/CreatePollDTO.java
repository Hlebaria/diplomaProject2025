package com.choiceApp.MyChoiceApp.models.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class CreatePollDTO {

    private String caption;
    private String description;
    private LocalDateTime closeTime;
    private boolean publicity;
    private boolean platformOnly;
    private List<CreateQuestionDTO> questions;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public boolean getPublicity() {
        return publicity;
    }

    public void setPublicity(boolean publicity) {
        this.publicity = publicity;
    }

    public boolean getPlatformOnly() {
        return platformOnly;
    }

    public void setPlatformOnly(boolean platformOnly) {
        this.platformOnly = platformOnly;
    }

    public List<CreateQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CreateQuestionDTO> questions) {
        this.questions = questions;
    }
}
