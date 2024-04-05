export const Constants = {
    AUTHORIZATION_HEADER: "Authorization",
    BUILD_AUTHORIZATION_HEADER: (token: string): string => `Bearer ${token}`,
    TOKEN: "token",
}

export const Endpoints = {
    baseURI: "/api",
    auth: "/auth",
    users: "/users",
    comments: "/comments",
    posts: "/posts",
    subthreads: "/subthreads",
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