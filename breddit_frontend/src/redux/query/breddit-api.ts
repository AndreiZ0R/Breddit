import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'
import {AuthResponse, BaseModel, BaseResponse, Comment, DomainResponse, ListResponse, SessionResponse, SingleResponse} from 'models/models.ts';
import {AuthRequest, CreateCommentRequest, RegisterRequest} from "models/requests.ts";
import Cookies from "js-cookie";
import {Constants, Endpoints, HttpMethods, Queries} from "../../utils/constants.ts";

const customBaseQuery = fetchBaseQuery({
   baseUrl: Endpoints.baseURI,
   prepareHeaders: (headers: Headers, /*{getState}*/) => {
      const token = Cookies.get(Constants.TOKEN) ?? "";
      const sessionId = Cookies.get(Constants.SESSION_ID) ?? "";

      if (token) {
         headers.set(Constants.AUTHORIZATION_HEADER, Constants.BUILD_AUTHORIZATION_HEADER(token));
      }

      if (sessionId) {
         headers.set(Constants.SESSION_HEADER, sessionId);
      }

      return headers;
   },
})


export const bredditApi = createApi({
   reducerPath: "bredditApi",
   baseQuery: customBaseQuery,
   tagTypes: [Queries.getSessions],
   endpoints: (builder) => ({
      getDomainModel: builder.query<DomainResponse, string>({
         query: (endpoint: string) => endpoint,
         //@ts-ignore
         // providesTags: (result, error, arg) => [{
         //     type: 'Record' as const,
         //     id: arg,
         // }],
      }),

      getPostPictures: builder.query<ListResponse, number>({
         query: (postId: number) => `${Endpoints.images}/post/${postId}`,
         transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
      }),

      getProfilePicture: builder.query<SingleResponse, string>({
         query: (username: string) => `${Endpoints.images}/profile/${username}`,
         transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
      }),

      getActiveSessions: builder.query<SessionResponse[], void>({
         query: () => Endpoints.sessions,
         transformResponse: (response: { payload: SessionResponse[] }): SessionResponse[] => response.payload as SessionResponse[],
         providesTags: [Queries.getSessions]
      }),

      login: builder.mutation<AuthResponse, AuthRequest>({
         query: (authRequest: AuthRequest) => ({
            url: `${Endpoints.auth}/login`,
            method: HttpMethods.POST,
            body: authRequest
         }),
         transformResponse: (response: { payload: AuthResponse }): AuthResponse => response.payload as AuthResponse,
         transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
         invalidatesTags: [Queries.getSessions],
      }),

      logout: builder.mutation<BaseResponse, void>({
         query: () => `${Endpoints.auth}/logout`,
         invalidatesTags: [Queries.getSessions]
      }),

      register: builder.mutation<AuthResponse, RegisterRequest>({
         query: (registerRequest: RegisterRequest) => ({
            url: `${Endpoints.auth}/register`,
            method: HttpMethods.POST,
            body: registerRequest
         }),
         transformResponse: (response: { payload: AuthResponse }): AuthResponse => response.payload as AuthResponse,
         transformErrorResponse: (response: { status: number, data: BaseResponse }): BaseResponse => response.data as BaseResponse,
      }),

      addComment: builder.mutation<Comment, CreateCommentRequest>({
         query: (createCommentRequest: CreateCommentRequest) => ({
            url: `${Endpoints.comments}/create`,
            method: HttpMethods.POST,
            body: createCommentRequest,
         }),
         transformResponse: (response: { payload: Comment }): Comment => response.payload as Comment,
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
   useGetActiveSessionsQuery,
   useLogoutMutation,
   useRegisterMutation,
   useGetProfilePictureQuery,
   useAddCommentMutation,
} = bredditApi;