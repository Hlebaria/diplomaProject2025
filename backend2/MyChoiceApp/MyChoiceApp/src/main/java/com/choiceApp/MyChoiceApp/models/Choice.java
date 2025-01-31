package com.choiceApp.MyChoiceApp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "choice")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "choice_text", nullable = false)
    private String choiceText;

    @Column(name = "votes_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer votesCount = 0;

    public Choice(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    //    CREATE TABLE choice (
//            id INT PRIMARY KEY NOT NULL,
//            questionId INT NOT NULL,
//            choiceText TEXT NOT NULL,
//            votesCount INT DEFAULT 0,
//            FOREIGN KEY (questionId) REFERENCES question (id) ON DELETE CASCADE
//);

}
