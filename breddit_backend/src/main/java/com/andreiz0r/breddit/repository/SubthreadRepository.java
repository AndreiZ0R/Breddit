package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.Subthread;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubthreadRepository extends JpaRepository<Subthread, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from Subthread s where s.id=:id")
    Integer deleteSubthreadById(final Integer id);
}
