package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from Post p where p.id=:id")
    Integer deletePostById(final Integer id);
}
