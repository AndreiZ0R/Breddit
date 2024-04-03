package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.PostUtils;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.UserUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Transactional
class PostRepositoryTest extends AbstractUnitTest<Post> {

    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void deletePostById_postIsInRepository_deletesPost() {
        // Given
        Post post = repository.save(PostUtils.createRandomPost(prepareAuthor()));

        // When
        Integer result = repository.deletePostById(post.getId());

        // Then
        assertThat(result, equalTo(1));
    }

    @Test
    void deletePostById_postIsNotInRepository_doesNotDelete() {
        // When
        Integer result = repository.deletePostById(Randoms.randomPositiveInteger());

        // Then
        assertThat(result, equalTo(0));
    }

    private User prepareAuthor() {
        return userRepository.save(UserUtils.createRandomUser());
    }
}