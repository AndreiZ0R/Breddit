package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.model.Subthread;

import java.util.List;

public class SubthreadUtils {

    public static Subthread createRandomSubthread(final Integer id, final Post... posts) {
        return createSubthread(
                Randoms.randomPositiveInteger(),
                Randoms.alphabetic(),
                Randoms.alphabetic(),
                Randoms.randomPositiveInteger(),
                List.of(posts));
    }

    public static Subthread createRandomSubthread() {
        return createSubthread(
                Randoms.randomPositiveInteger(),
                Randoms.alphabetic(),
                Randoms.alphabetic(),
                Randoms.randomPositiveInteger(),
                List.of(PostUtils.createRandomPost(), PostUtils.createRandomPost()));
    }

    public static Subthread createSubthread(
            final Integer id,
            final String name,
            final String description,
            final Integer membersCount,
            final List<Post> posts) {
        return new Subthread(id, name, description, membersCount, posts);
    }
}
