package com.andreiz0r.breddit.utils;

import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.entity.UserRole;

import java.sql.Date;
import java.sql.Timestamp;

public class UserUtils {

    public static User copy(final User user) {
        return createUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getCountry(),
                user.getCreatedAt(),
                user.getBirthDate(),
                user.getRole());
    }

    public static User createRandomUser(final Integer id) {
        User user = createRandomUser();
        user.setId(id);
        return user;
    }

    public static User createRandomUser(final String username) {
        User user = createRandomUser();
        user.setUsername(username);
        return user;
    }

    public static User createRandomUserWithCountry(final String country) {
        User user = createRandomUser();
        user.setCountry(country);
        return user;
    }

    public static User createRandomUserBetween(final String startTime, final String endTime) {
        long offset = Timestamp.valueOf(startTime).getTime();
        long end = Timestamp.valueOf(endTime).getTime();
        long diff = end - offset + 1;
        Timestamp randomTimestamp = new Timestamp(offset + (long) (Math.random() * diff));

        User user = createRandomUser();
        user.setCreatedAt(randomTimestamp);
        return user;
    }

    public static User createRandomUserBetween(final int startTime, final int endTime) {
        long diff = endTime - startTime + 1;
        Timestamp randomTimestamp = new Timestamp(startTime + (long) (Math.random() * diff));

        User user = createRandomUser();
        user.setCreatedAt(randomTimestamp);
        return user;
    }

    public static User createRandomUser() {
        return createUser(
                Randoms.randomPositiveInteger(),
                Randoms.alphabetic(),
                Randoms.alphabetic(),
                Randoms.alphabetic() + "@gmail.com",
                Randoms.alphabetic(),
                AppUtils.timestampNow(),
                new Date(System.currentTimeMillis()),
                Randoms.randomBoolean() ? UserRole.User : UserRole.Mod);
    }

    public static User createUser(
            final Integer id,
            final String username,
            final String password,
            final String email,
            final String country,
            final Timestamp createdAt,
            final Date birthDate,
            final UserRole role) {
        return new User(id, username, password, email, country, createdAt, birthDate, role);
    }
}
