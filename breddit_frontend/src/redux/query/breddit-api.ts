import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {AuthResponse, BaseResponse, DomainModel, Post, Subthread, User} from 'models/models.ts';
import {AuthRequest} from "models/requests.ts";
import Cookies from "js-cookie";

const baseURI = "/api";
const authEndpoint = "/auth";
const usersEndpoint = "/users";
const commentsEndpoint = "/comments";
const postsEndpoint = "/posts";
const subthreadsEndpoint = "/subthreads";
// const messagesEndpoint = "/messages";


const customBaseQuery = fetchBaseQuery({
    baseUrl: baseURI,
    prepareHeaders: (headers: Headers, {getState}) => {
        const token = Cookies.get("token") ?? "";

        if (token) {
            headers.set('authorization', `Bearer ${token}`);
        }

        return headers;
    },
})

//maybe create a custom query?
export const bredditApi = createApi({
    reducerPath: "bredditApi",
    baseQuery: customBaseQuery,
    endpoints: (builder) => ({
        getDomainModel: builder.query<DomainModel[], string>({
            query: (endpoint: string) => endpoint,
        }),

        getUsers: builder.query<User[], void>({
            query: () => usersEndpoint,
        }),

        getComments: builder.query<Comment[], void>({
            query: () => commentsEndpoint,
        }),

        getPosts: builder.query<Post[], void>({
            query: () => postsEndpoint,
        }),

        getSubthreads: builder.query<Subthread[], void>({
            query: () => subthreadsEndpoint,
        }),

        login: builder.mutation<AuthResponse, AuthRequest>({
            query: (authRequest: AuthRequest) => ({
                url: `${authEndpoint}/login`,
                method: 'POST',
                body: authRequest
            }),
            transformResponse: (response: { payload: AuthResponse }): AuthResponse => response.payload as AuthResponse,
            transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
        })
    }),
});

export const {
    useGetUsersQuery,
    useGetCommentsQuery,
    useGetPostsQuery,
    useGetSubthreadsQuery,
    useGetDomainModelQuery,
    useLoginMutation,
} = bredditApi;