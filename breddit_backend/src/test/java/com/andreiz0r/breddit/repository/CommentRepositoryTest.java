package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.CommentUtils;
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
class CommentRepositoryTest extends AbstractUnitTest<Comment> {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void deleteCommentById_commentIsInRepository_deletesComment() {
        // Given
        Comment comment = repository.save(CommentUtils.createRandomComment(prepareAuthor()));

        // When
        Integer result = repository.deleteCommentById(comment.getId());

        // Then
        assertThat(result, equalTo(1));
    }

    @Test
    void deleteCommentById_commentIsNotInRepository_doesNotDelete() {
        // When
        Integer result = repository.deleteCommentById(Randoms.randomPositiveInteger());

        // Then
        assertThat(result, equalTo(0));
    }

    private User prepareAuthor() {
        return userRepository.save(UserUtils.createRandomUser());
    }
}