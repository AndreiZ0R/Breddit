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

export type {
   BaseRequest,
   AuthRequest,
   RegisterRequest,
};