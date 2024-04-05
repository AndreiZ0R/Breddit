import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {AuthResponse, User} from "../../models/models.ts";
import Cookies from "js-cookie";
import {RootState} from "../store/store.ts";
import {Constants} from "../../utils/constants.ts";

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
            Cookies.remove(Constants.TOKEN);
        },
        startSession: (state, action: PayloadAction<AuthResponse>) => {
            state.user = action.payload.user;
            state.isLoggedIn = true;
            Cookies.set(Constants.TOKEN, action.payload.token, {expires: 7, secure: true});
        },
    },
});

export const {startSession, endSession} = authSlice.actions;
export const selectAuthState = (state: RootState) => state.auth;
export default authSlice.reducer;