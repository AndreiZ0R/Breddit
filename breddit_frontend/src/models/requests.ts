interface BaseRequest {
}

interface AuthRequest extends BaseRequest {
    username: string,
    password: string
}


export type {
    BaseRequest,
    AuthRequest
};