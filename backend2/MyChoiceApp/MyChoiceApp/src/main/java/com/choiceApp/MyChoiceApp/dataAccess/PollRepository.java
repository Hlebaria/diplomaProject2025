package com.choiceApp.MyChoiceApp.dataAccess;

import com.choiceApp.MyChoiceApp.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, String> {

    @Query(value = "SELECT * FROM poll WHERE publicity = TRUE ORDER BY birth_time DESC LIMIT 10 OFFSET :loadedPolls", nativeQuery = true)
    List<Poll> customPageFindPolls(@Param("loadedPolls") int loadedPolls);

    @Query(value = "SELECT * FROM poll WHERE poll.id IN (SELECT DISTINCT poll_id FROM vote WHERE vote.user_id = :userId)", nativeQuery = true)
    List<Poll> customFindPollsVotedByUser(@Param("userId") String userId);

    List<Poll> findByCreator(String creator);

    @Query(value = "SELECT creator_id FROM poll WHERE id = :pollId", nativeQuery = true)
    String customFindOwnerOfPoll(@Param("pollId") String pollId);

    Optional<Poll> findById(String id);

//    @Query(value = "SELECT showResults FROM poll WHERE id = :pollId", nativeQuery = true)
//    Boolean showResults(@Param("pollId") String pollId);
}

