package com.choiceApp.MyChoiceApp.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "poll")
public class Poll {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "creator_id", nullable = false)
    private String creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Column(name = "caption")
    private String caption;

    @Column(name = "poll_text")
    private String pollText;

    @Column(name = "birth_time", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime birthTime = LocalDateTime.now();

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @Column(name = "open", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean open = true;

    @Column(name = "show_results", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean showResults = false;

    @Column(name = "publicity", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean publicity = false;

    @Column(name = "platform_only", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean platformOnly = false;

    @Column(name = "voter_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer voterCount = 0;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    public Poll(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getVoterCount() {
        return voterCount;
    }

    public void setVoterCount(Integer voterCount) {
        this.voterCount = voterCount;
    }

    //    -- CREATE TABLE poll (
//--     id TEXT PRIMARY KEY,
//--     creator TEXT NOT NULL,
//--     caption TEXT,
//--     pollText TEXT,
//--     birthTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//--     closeTime TIMESTAMP,
//--     open BOOLEAN DEFAULT FALSE,
//--     showResults BOOLEAN DEFAULT FALSE,
//--     platformOnly BOOLEAN DEFAULT FALSE
//        -- );

}
