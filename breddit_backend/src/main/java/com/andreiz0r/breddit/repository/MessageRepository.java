package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.entity.ChatMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<ChatMessage, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from ChatMessage m where m.id=:id")
    Integer deleteMessageById(final Integer id);
}
