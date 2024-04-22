import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {AuthResponse, BaseModel, BaseResponse, DomainResponse, ListResponse} from 'models/models.ts';
import {AuthRequest} from "models/requests.ts";
import Cookies from "js-cookie";
import {Constants, Endpoints, HttpMethods, Queries} from "../../utils/constants.ts";

const customBaseQuery = fetchBaseQuery({
    baseUrl: Endpoints.baseURI,
    prepareHeaders: (headers: Headers, /*{getState}*/) => {
        const token = Cookies.get(Constants.TOKEN) ?? "";

        if (token) {
            headers.set(Constants.AUTHORIZATION_HEADER, Constants.BUILD_AUTHORIZATION_HEADER(token));
        }

        return headers;
    },
})


export const bredditApi = createApi({
    reducerPath: "bredditApi",
    baseQuery: customBaseQuery,
    tagTypes: [Queries.login, Queries.getUsers, Queries.getPosts, Queries.getComments, Queries.getSubthreads],
    endpoints: (builder) => ({
        getDomainModel: builder.query<DomainResponse, string>({
            query: (endpoint: string) => endpoint,
            // @ts-ignore
            providesTags: (result, error, arg) => [{
                type: 'Record' as const,
                id: arg,
            }],
        }),

        getPostPictures: builder.query<ListResponse, bigint>({
            query: (postId: bigint) => `${Endpoints.images}/post/${postId}`,
            transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
        }),

        login: builder.mutation<AuthResponse, AuthRequest>({
            query: (authRequest: AuthRequest) => ({
                url: `${Endpoints.auth}/login`,
                method: HttpMethods.POST,
                body: authRequest
            }),
            transformResponse: (response: { payload: AuthResponse }): AuthResponse => response.payload as AuthResponse,
            transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
        }),


    }),
});

export const useGetModel = <T extends BaseModel>(endpoint: string) => {
    const {data: domainModel} = useGetDomainModelQuery(endpoint);
    if (domainModel) {
        return domainModel.payload as T;
    }
}

export const {
    useGetDomainModelQuery,
    useLoginMutation,
    useGetPostPicturesQuery,
} = bredditApi;