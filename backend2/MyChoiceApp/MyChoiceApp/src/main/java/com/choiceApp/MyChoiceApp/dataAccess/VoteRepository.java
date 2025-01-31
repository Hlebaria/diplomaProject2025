package com.choiceApp.MyChoiceApp.dataAccess;

import com.choiceApp.MyChoiceApp.models.Poll;
import com.choiceApp.MyChoiceApp.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(value = "SELECT choice_id FROM vote WHERE user_id = :userId AND poll_id = :pollId ", nativeQuery = true)
    List<Integer> checkVote(@Param("userId") String userId, @Param("pollId") String pollId);

    @Query(value = "SELECT choice_id FROM vote WHERE user_id = :userId AND poll_id = :pollId AND question_id = :questionId", nativeQuery = true)
    List<Integer> checkVotesForQuestion(@Param("userId") String userId, @Param("pollId") String pollId, @Param("questionId") Integer questionId);

}
