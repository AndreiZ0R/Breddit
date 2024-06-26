package com.andreiz0r.breddit.utils;

public enum Topic {
    MESSAGES("messages"),
    NOTIFICATIONS("notifications");

    private final String name;

    Topic(final String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return "/topic/" + this.name;
    }
}