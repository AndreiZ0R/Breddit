import {UserRole} from "./models.ts";

interface BaseRequest {
}

interface AuthRequest extends BaseRequest {
   username: string,
   password: string
}

interface RegisterRequest extends BaseRequest {
   username: string,
   password: string,
   email: string,
   country: string,
   birthDate: Date,
   role: UserRole
}

interface CreateCommentRequest extends BaseRequest {
   authorId: number,
   body: string,
   postId: number,
   parentId: number | null,
}

export type {
   BaseRequest,
   AuthRequest,
   RegisterRequest,
   CreateCommentRequest
};