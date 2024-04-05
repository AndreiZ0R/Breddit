import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AuthResponse, User} from "../../models/models.ts";
import Cookies from "js-cookie";
import {RootState} from "../store/store.ts";

export interface AuthState {
    user: User | null,
    token: string | null,
    isLoggedIn: boolean,
}

const initialState: AuthState = {
    user: null,
    token: null,
    isLoggedIn: false
}

export const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        endSession: (state) => {
            state.user = null;
            state.isLoggedIn = false;
            Cookies.remove("token");
        },
        startSession: (state, action: PayloadAction<AuthResponse>) => {
            state.user = action.payload.user;
            state.isLoggedIn = true;
            Cookies.set("token", action.payload.token, {expires: 7, secure: true});
            console.log("Session started successfully, username: ", state.user.username);
        },
    },
});

export const {startSession, endSession} = authSlice.actions;
export const selectAuthState = (state: RootState) => state.auth;
export default authSlice.reducer;