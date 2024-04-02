package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Message;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from Message m where m.id=:id")
    Integer deleteMessageById(final Integer id);
}
