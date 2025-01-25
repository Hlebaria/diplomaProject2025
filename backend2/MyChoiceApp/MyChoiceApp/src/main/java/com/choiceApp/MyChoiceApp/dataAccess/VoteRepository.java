package com.choiceApp.MyChoiceApp.dataAccess;

import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {

    public List<Vote> findByUserId(String userId);

}
