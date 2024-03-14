package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Subthread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubthreadRepository extends JpaRepository<Subthread, Integer> {
}
