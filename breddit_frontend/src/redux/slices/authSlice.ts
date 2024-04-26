import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AuthResponse, User} from "../../models/models.ts";
import Cookies from "js-cookie";
import {RootState} from "../store/store.ts";
import {Constants} from "../../utils/constants.ts";

export interface AuthState {
    user: User | null,
    token: string | null,
    sessionId: string | null,
    isLoggedIn: boolean,
}

const getInitialUser = (): User | null => {
    const storedUser = localStorage.getItem(Constants.USER);
    if (storedUser) {
        return JSON.parse(storedUser) as User;
    }

    return null;
}


const initialState: AuthState = {
    user: getInitialUser(),
    token: Cookies.get(Constants.TOKEN) ?? null,
    sessionId: Cookies.get(Constants.SESSION_ID) ?? null,
    isLoggedIn: !!Cookies.get(Constants.TOKEN),
}

console.log("auth", initialState);

export const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        endSession: (state) => {
            state.user = null;
            state.isLoggedIn = false;
            Cookies.remove(Constants.TOKEN);
            Cookies.remove(Constants.SESSION_ID);
            localStorage.removeItem(Constants.USER);
        },
        startSession: (state, action: PayloadAction<AuthResponse>) => {
            state.user = action.payload.user;
            state.isLoggedIn = true;
            Cookies.set(Constants.TOKEN, action.payload.token, {expires: 7, secure: true});
            Cookies.set(Constants.SESSION_ID, action.payload.sessionId, {expires: 7, secure: true});
            localStorage.setItem(Constants.USER, JSON.stringify(state.user));
        },
    },
});

export const {startSession, endSession} = authSlice.actions;
export const selectAuthState = (state: RootState) => state.auth;
export default authSlice.reducer;