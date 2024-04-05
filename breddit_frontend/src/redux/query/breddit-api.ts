import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {AuthResponse, BaseResponse, User} from 'models/models.ts';
import {AuthRequest} from "models/requests.ts";


const baseURI = "http://localhost:8080/api";
const authEndpoint = "/auth";
const usersEndpoint = "/users";
// const commentsEndpoint = "/comments";
// const postsEndpoint = "/posts";
// const subthreadsEndpoint = "/subthreads";
// TODO: messages


// const baseQuery = fetchBaseQuery({
//     baseUrl: baseURI,
//     prepareHeaders: (headers: Headers, {getState}) => {
//         // const token = (getState() as RootState).auth.token;
//         //
//         // if (token) {
//         //     headers.set('authorization', `Bearer ${token}`);
//         // }
//         //
//         return headers;
//     }
// })
export const bredditApi = createApi({
    reducerPath: "bredditApi",
    baseQuery: fetchBaseQuery({baseUrl: baseURI}),
    endpoints: (builder) => ({
        getUsers: builder.query<User[], void>({
            query: () => usersEndpoint,
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
    useLoginMutation,
} = bredditApi;