package com.andreiz0r.breddit.repository;

import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.utils.AbstractUnitTest;
import com.andreiz0r.breddit.utils.Randoms;
import com.andreiz0r.breddit.utils.UserUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@Transactional
class UserRepositoryTest extends AbstractUnitTest<User> {

    @Autowired
    private UserRepository repository;

    @Test
    void deleteUserById_userIsInRepository_deletesUser() {
        // Given
        User user = repository.save(UserUtils.createRandomUser());

        // When
        Integer result = repository.deleteUserById(user.getId());

        // Then
        assertThat(result, equalTo(1));
    }

    @Test
    void deleteUserById_userIsNotInRepository_deletesUser() {
        // Given
        repository.save(UserUtils.createRandomUser());

        // When
        Integer result = repository.deleteUserById(Randoms.randomPositiveInteger());

        // Then
        assertThat(result, equalTo(0));
    }
}