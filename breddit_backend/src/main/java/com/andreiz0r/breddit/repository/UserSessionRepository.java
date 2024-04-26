package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.entity.UserSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {

    @Transactional
    @Modifying
    @Query("delete from UserSession us where us.sessionId = :sessionId")
    Integer deleteSessionBySessionId(final UUID sessionId);

    @Transactional
    @Modifying
    @Query("delete from UserSession us where us.user.id = :id")
    Integer deleteSessionByUserId(final Integer id);

    @Query("select us from UserSession us where us.user.id = :userId")
    Optional<UserSession> findByUserId(final Integer userId);
}