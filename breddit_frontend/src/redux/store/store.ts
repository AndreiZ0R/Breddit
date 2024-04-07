import {configureStore} from '@reduxjs/toolkit'
import counterReducer from "../slices/counterSlice.ts"
import authReducer from "../slices/authSlice.ts";
import themeReducer from "../slices/themeSlice.ts";
import {bredditApi} from "../query/breddit-api.ts";
import {setupListeners} from "@reduxjs/toolkit/query";

export const store = configureStore({
    reducer: {
        [bredditApi.reducerPath]: bredditApi.reducer,
        auth: authReducer,
        theme: themeReducer,
        counter: counterReducer,
    },
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(bredditApi.middleware)
})

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;