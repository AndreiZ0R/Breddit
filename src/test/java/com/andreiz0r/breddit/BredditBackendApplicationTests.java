package com.andreiz0r.breddit;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.model.Subthread;
import com.andreiz0r.breddit.model.User;
import com.andreiz0r.breddit.model.UserRole;
import com.andreiz0r.breddit.repository.CommentRepository;
import com.andreiz0r.breddit.repository.PostRepository;
import com.andreiz0r.breddit.repository.SubthreadRepository;
import com.andreiz0r.breddit.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BredditBackendApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SubthreadRepository subthreadRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void setupUsers() {
        userRepository.saveAll(List.of(
                new User(-1, "AndreiZ0R", "myparol", "andreiborzagabriel@gmail.com", "Romania", Timestamp.from(Instant.now()), Date.valueOf("1985-07-12"), UserRole.Mod),
                new User(-1, "jane_smith", "securePass", "jane_smith@gmail.com", "UK", Timestamp.from(Instant.now()), Date.valueOf("1990-04-25"), UserRole.User),
                new User(-1, "mike123", "letmein", "mike123@gmail.com", "Canada", Timestamp.from(Instant.now()), Date.valueOf("1988-11-05"), UserRole.User),
                new User(-1, "sara_88", "qwerty", "sara_88@gmail.com", "Australia", Timestamp.from(Instant.now()), Date.valueOf("1976-09-30"), UserRole.User),
                new User(-1, "alexander", "123456", "alexander@gmail.com", "Germany", Timestamp.from(Instant.now()), Date.valueOf("1995-03-18"), UserRole.User),
                new User(-1, "emily22", "pass1234", "emily22@gmail.com", "France", Timestamp.from(Instant.now()), Date.valueOf("1980-12-08"), UserRole.User),
                new User(-1, "david87", "changeme", "david87@gmail.com", "Japan", Timestamp.from(Instant.now()), Date.valueOf("1972-06-21"), UserRole.User),
                new User(-1, "laura_09", "password", "laura_09@gmail.com", "Brazil", Timestamp.from(Instant.now()), Date.valueOf("1983-10-17"), UserRole.User),
                new User(-1, "samwise", "password@123", "samwise@gmail.com", "China", Timestamp.from(Instant.now()), Date.valueOf("1998-02-03"), UserRole.User),
                new User(-1, "frodo", "test123", "frodo@gmail.com", "India", Timestamp.from(Instant.now()), Date.valueOf("1979-08-14"), UserRole.User)
        ));
    }

    @Test
    void setupThreads() {
        subthreadRepository.save(new Subthread(-1, "LeagueOfLegends", "Thread dedicated to League", 0, new ArrayList<Post>()));

        Comment comment = new Comment(-1,
                                      userRepository.findById(2).get(),
                                      "Actually such a fine meme!",
                                      Timestamp.from(Instant.now()),
                                      2,
                                      1,
                                      null
        );
        Comment comment2 = new Comment(-1,
                                       userRepository.findById(5).get(),
                                       "More like mid, but sure",
                                       Timestamp.from(Instant.now()),
                                       6,
                                       1,
                                       1
        );

//        commentRepository.saveAll(
//                List.of(comment, comment2)
//        );

        postRepository.save(
                new Post(-1,
                         "Meme of february",
                         "Hello everyone, here is the best meme in february...",
                         userRepository.findById(8).get(),
                         Timestamp.from(Instant.now()),
                         List.of(comment, comment2),
                         0,
                         1
                )
        );
    }
}
