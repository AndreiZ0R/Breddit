export const Constants = {
    AUTHORIZATION_HEADER: "Authorization",
    SESSION_HEADER: "SESSION_ID",
    BUILD_AUTHORIZATION_HEADER: (token: string): string => `Bearer ${token}`,
    TOKEN: "token",
    THEME: "theme",
    USER: "user",
    SESSION_ID: "sessionId",
}

export const AppRoutes = {
    PROTECTED: "/",
    HOME: "/home",
    SESSIONS: "/sessions",
    LOGIN: "/login",
    REGISTER: "/register"
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
    sessions: "/sessions",
};

export const Queries = {
    login: "LOGIN",
    register: "REGISTER",
    getUsers: "USERS",
    getComments: "COMMENTS",
    getPosts: "POSTS",
    getSubthreads: "SUBTHREADS",
    getSessions: "SESSIONS",
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
