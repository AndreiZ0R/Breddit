package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from Comment c where c.id=:id")
    Comment deleteByIdAndReturn(final Integer id);

    //TODO: check this
}
