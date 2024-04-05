interface BaseModel {
}

interface DomainModel extends BaseModel {
    id: bigint,
}

interface BaseResponse {
    message: string,
    status: string
}

interface SingleResponse extends BaseResponse {
    payload: BaseModel
}

interface ListResponse extends BaseResponse {
    payload: BaseModel[]
}

type DomainResponse = SingleResponse | ListResponse;


type UserRole = "User" | "Mod";

interface User extends DomainModel {
    username: string,
    email: string,
    country: string,
    createdAt: Date,
    birthDate: Date,
    role: UserRole,

    isModerator(role: UserRole): boolean;
}
//TODO: map to DTOs!
interface Comment extends DomainModel {
    author: User,
    body: string,
    postedAt: Date,
    votes: bigint,
    postId: bigint,
    parentId: bigint | null,
}

interface Post extends DomainModel {
    title: string,
    body: string,
    author: User,
    postedAt: Date,
    comments: Comment[],
    votes: bigint,
    subthreadId: bigint,
}

interface Subthread extends DomainModel {
    name: string,
    description: string,
    membersCount: bigint,
    posts: Post[],
}

interface Message extends DomainModel {
    sender: User,
    receiver: User,
    content: string,
    sentAt: Date,
}

interface AuthResponse extends BaseModel{
    token: string,
    user: User,
}


export type {
    BaseResponse,
    DomainResponse,
    DomainModel,
    SingleResponse,
    ListResponse,
    User,
    Comment,
    Post,
    Subthread,
    Message,
    AuthResponse,
};