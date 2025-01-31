package com.choiceApp.MyChoiceApp.models.DTOs;

import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FullPollDTO {

    private String creatorName;
    private String caption;
    private String pollText;
    private LocalDateTime birthTime;
    private LocalDateTime closeTime;
    private Boolean open;
    private Boolean showResults;
    private Boolean publicity;
    private Boolean platformOnly;
    private Integer voterCount;

    private List<FullQuestionDTO> questions;

    public FullPollDTO (Poll poll){

        setCreatorName(poll.getCreatorName());
        setCaption(poll.getCaption());
        setPollText(poll.getPollText());
        setBirthTime(poll.getBirthTime());
        setCloseTime(poll.getCloseTime());
        setOpen(poll.getOpen());
        setShowResults(poll.getShowResults());
        setPublicity(poll.getPublicity());
        setPlatformOnly(poll.getPlatformOnly());
        setVoterCount(poll.getVoterCount());

        questions = new ArrayList<>(List.of());

        for(Question question : poll.getQuestions()){

            FullQuestionDTO questionDTO = new FullQuestionDTO(question, showResults);
            questions.add(questionDTO);

        }

    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPollText() {
        return pollText;
    }

    public void setPollText(String pollText) {
        this.pollText = pollText;
    }

    public LocalDateTime getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(LocalDateTime birthTime) {
        this.birthTime = birthTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getShowResults() {
        return showResults;
    }

    public void setShowResults(Boolean showResults) {
        this.showResults = showResults;
    }

    public Boolean getPublicity() {
        return publicity;
    }

    public void setPublicity(Boolean publicity) {
        this.publicity = publicity;
    }

    public Boolean getPlatformOnly() {
        return platformOnly;
    }

    public void setPlatformOnly(Boolean platformOnly) {
        this.platformOnly = platformOnly;
    }

    public Integer getVoterCount() {
        return voterCount;
    }

    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }

    public List<FullQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<FullQuestionDTO> questions) {
        this.questions = questions;
    }
}
