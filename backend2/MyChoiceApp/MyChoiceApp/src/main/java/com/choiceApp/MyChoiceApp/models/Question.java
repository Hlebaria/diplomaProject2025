package com.choiceApp.MyChoiceApp.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pollId", nullable = false)
    private Poll poll;

    @Column(name = "allowedChoices", nullable = false)
    private String allowedChoices;

    @Column(name = "questionText", nullable = false)
    private String questionText;

    public Integer getId() {
        return id;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices;

    public void setId(Integer id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getAllowedChoices() {
        return allowedChoices;
    }

    public void setAllowedChoices(String allowedChoices) {
        this.allowedChoices = allowedChoices;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    //-- CREATE TABLE question (
//--     id INT PRIMARY KEY NOT NULL,
//--     pollId TEXT NOT NULL,
//--     allowedChoices INT NOT NULL,
//--     questionText TEXT NOT NULL,
//--     FOREIGN KEY (pollId) REFERENCES poll (id) ON DELETE CASCADE
//-- );
}
