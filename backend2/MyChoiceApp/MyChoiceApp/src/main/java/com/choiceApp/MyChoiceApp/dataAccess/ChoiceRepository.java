package com.choiceApp.MyChoiceApp.dataAccess;

import com.choiceApp.MyChoiceApp.models.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

    List<Choice> findByQuestionId(int questionId);

    List<Choice> findByPollId(String pollId);

}
