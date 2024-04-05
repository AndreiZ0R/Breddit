import {configureStore} from '@reduxjs/toolkit'
import counterReducer from "../slices/counterSlice.ts"
import authReducer from "../slices/authSlice.ts";
import {bredditApi} from "../query/breddit-api.ts";
import {setupListeners} from "@reduxjs/toolkit/query";

export const store = configureStore({
    reducer: {
        counter: counterReducer,
        auth: authReducer,
        [bredditApi.reducerPath]: bredditApi.reducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(bredditApi.middleware)
})

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;