package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u where u.username=:username")
    Optional<User> findByUsername(final String username);

    @Query(value = "select u from User u where u.email=:email")
    Optional<User> findByEmail(final String email);

    @Query(value = "select u from User u where u.country=:country")
    List<User> findAllByCountry(final String country);

    @Query(value = "select u from User u WHERE u.username like %:pattern%")
    List<User> findByUsernameContaining(final String pattern);

    @Query(value = "select u from User u where u.id in :ids")
    List<User> findMultipleById(final List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "delete from User u where u.id=:id")
    Integer deleteUserById(final Integer id);
}
