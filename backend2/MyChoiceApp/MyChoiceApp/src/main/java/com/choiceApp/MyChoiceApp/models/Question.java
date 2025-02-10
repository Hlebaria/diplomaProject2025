package com.choiceApp.MyChoiceApp.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @Column(name = "max_voter_choices", nullable = false)
    private Integer maxVoterChoices;

    @Column(name = "min_voter_choices", nullable = false)
    private Integer minVoterChoices;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices;

    public Question(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Integer getMaxVoterChoices() {
        return maxVoterChoices;
    }

    public void setMaxVoterChoices(Integer maxVoterChoices) {
        this.maxVoterChoices = maxVoterChoices;
    }

    public Integer getMinVoterChoices() {
        return minVoterChoices;
    }

    public void setMinVoterChoices(Integer minVoterChoices) {
        this.minVoterChoices = minVoterChoices;
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
