package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.User;

import java.sql.Timestamp;

public class CommentUtils {

    public static Comment createRandomComment(final Integer id) {
        Comment randomComment = createRandomComment();
        randomComment.setId(id);
        return randomComment;
    }

    public static Comment createRandomComment(final User author) {
        return createComment(
                Randoms.randomPositiveInteger(),
                author,
                Randoms.alphabetic(),
                AppUtils.timestampNow(),
                Randoms.randomInteger(),
                Randoms.randomPositiveInteger(),
                Randoms.randomInteger() > 0 ? Randoms.randomPositiveInteger() : null);
    }

    public static Comment createRandomComment() {
        return createComment(
                Randoms.randomPositiveInteger(),
                UserUtils.createRandomUser(),
                Randoms.alphabetic(),
                AppUtils.timestampNow(),
                Randoms.randomInteger(),
                Randoms.randomPositiveInteger(),
                Randoms.randomInteger() > 0 ? Randoms.randomPositiveInteger() : null);
    }

    public static Comment createComment(
            final Integer id,
            final User author,
            final String body,
            final Timestamp postedAt,
            final Integer votes,
            final Integer postId,
            final Integer parentId) {
        return new Comment(id, author, body, postedAt, votes, postId, parentId);
    }
}
