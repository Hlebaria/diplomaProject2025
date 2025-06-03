package com.choiceApp.MyChoiceApp.dataAccess;

import com.choiceApp.MyChoiceApp.models.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {

    @Modifying
    @Query(value = "UPDATE choice SET votesCount = votesCount + 1 WHERE id = :id", nativeQuery = true)
    void incrementVoteCount(@Param("id") Integer id);

}
