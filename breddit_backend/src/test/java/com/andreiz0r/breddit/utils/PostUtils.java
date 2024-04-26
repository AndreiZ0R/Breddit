package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.entity.Comment;
import com.andreiz0r.breddit.entity.Post;
import com.andreiz0r.breddit.entity.User;

import java.sql.Timestamp;
import java.util.List;

public class PostUtils {

    public static Post createRandomPost(final Integer id) {
        Post randomPost = createRandomPost();
        randomPost.setId(id);
        return randomPost;
    }

    public static Post createRandomPost(final User author, final Comment... comments) {
        return createPost(
                Randoms.randomPositiveInteger(),
                Randoms.alphabetic(),
                Randoms.alphabetic(),
                author,
                AppUtils.timestampNow(),
                List.of(comments),
                Randoms.randomInteger(),
                Randoms.randomPositiveInteger(),
                List.of(Randoms.alphabetic(), Randoms.alphabetic())
        );
    }

    public static Post createRandomPost() {
        return createPost(
                Randoms.randomPositiveInteger(),
                Randoms.alphabetic(),
                Randoms.alphabetic(),
                UserUtils.createRandomUser(),
                AppUtils.timestampNow(),
                List.of(CommentUtils.createRandomComment(), CommentUtils.createRandomComment()),
                Randoms.randomInteger(),
                Randoms.randomPositiveInteger(),
                List.of(Randoms.alphabetic(), Randoms.alphabetic())
        );
    }

    public static Post createPost(
            final Integer id,
            final String title,
            final String body,
            final User author,
            final Timestamp postedAt,
            final List<Comment> comments,
            final Integer votes,
            final Integer subthreadId,
            final List<String> imageUrls) {
        return new Post(id, title, body, author, postedAt, comments, votes, subthreadId, imageUrls);
    }
}
