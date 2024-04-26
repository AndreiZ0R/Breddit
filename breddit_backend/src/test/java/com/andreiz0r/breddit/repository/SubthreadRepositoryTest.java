package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.entity.Post;
import com.andreiz0r.breddit.entity.Subthread;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.PostUtils;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.SubthreadUtils;
import com.andreiz0r.breddit.utils.UserUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Transactional
class SubthreadRepositoryTest extends AbstractUnitTest<Subthread> {

    @Autowired
    private SubthreadRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void deleteSubthreadById_subthreadIsInRepository_deletesSubthread() {
        // Given
        Subthread subthread = repository.save(SubthreadUtils.createRandomSubthread(Randoms.randomPositiveInteger(), preparePost()));

        // When
        Integer result = repository.deleteSubthreadById(subthread.getId());

        // Then
        assertThat(result, equalTo(1));
    }

    @Test
    void deleteSubthreadById_subthreadIsNotInRepository_doesNotDelete() {
        // When
        Integer result = repository.deleteSubthreadById(Randoms.randomPositiveInteger());

        // Then
        assertThat(result, equalTo(0));
    }

    private Post preparePost() {
        return postRepository.save(PostUtils.createRandomPost(prepareAuthor()));
    }

    private User prepareAuthor() {
        return userRepository.save(UserUtils.createRandomUser());
    }

}