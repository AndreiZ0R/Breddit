export const Constants = {
    AUTHORIZATION_HEADER: "Authorization",
    BUILD_AUTHORIZATION_HEADER: (token: string): string => `Bearer ${token}`,
    TOKEN: "token",
    THEME: "theme",
    USER: "user",
}

export const Endpoints = {
    baseURI: "/api",
    auth: "/auth",
    users: "/users",
    comments: "/comments",
    posts: "/posts",
    subthreads: "/subthreads",
    images: "/images",
// const messagesEndpoint = "/messages";
};

export const Queries = {
    login: "/login",
    register: "/register",
    getUsers: "/getUsers",
    getComments: "/getComments",
    getPosts: "/getPosts",
    getSubthreads: "/getSubthreads",
};

export const HttpMethods = {
    GET: "GET",
    POST: "POST",
    PATCH: "PATCH",
    DELETE: "DELETE",
}

export enum ThemeType {
    LIGHT = "light",
    DARK = "dark",
    GREEN = "green",
}

export const getThemeType = (theme: string): ThemeType => {
    switch (theme) {
        case "light":
            return ThemeType.LIGHT;
        case "dark":
            return ThemeType.DARK;
        case "green":
            return ThemeType.GREEN;
        default:
            return ThemeType.LIGHT;
    }
}

export type BaseProps = {
    additionalStyles?: string,
}
