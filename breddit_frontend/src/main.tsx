import ReactDOM from 'react-dom/client'
import './index.css'
import {Provider} from 'react-redux';
import {store} from './redux/store/store.ts'
import {createBrowserRouter, Navigate, Outlet, RouterProvider} from "react-router-dom";
import HomePage from "./pages/HomePage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import PrivateRoute from "./pages/PrivateRoute.tsx";
import Navbar from "./components/Navbar.tsx";
import SockJS from "sockjs-client";
import {Client, over} from "stompjs";
import {createContext} from "react";
import SessionsPage from "./pages/SessionsPage.tsx";
import {AppRoutes} from "./utils/constants.ts";
import RegisterPage from "./pages/RegisterPage.tsx";

function Layout() {
    return (
        <>
            <Navbar/>
            <Outlet/>
            {/*<Footer/>*/}
        </>
    );
}

const router = createBrowserRouter([
    {
        element: <Layout/>,
        // errorElement: <div>Not found</div>,
        children: [
            {
                path: AppRoutes.PROTECTED,
                element: <Navigate to={AppRoutes.HOME} replace/>
            },
            {
                path: AppRoutes.HOME,
                element: <PrivateRoute><HomePage/></PrivateRoute>,
            },
            {
                path: AppRoutes.LOGIN,
                element: <LoginPage/>
            },
            {
                path: AppRoutes.SESSIONS,
                element: <PrivateRoute><SessionsPage/></PrivateRoute>
            },
            {
                path: AppRoutes.REGISTER,
                element: <RegisterPage/>
            },
            {
                path: "/ok",
                element: <PrivateRoute>
                    <div>okok</div>
                </PrivateRoute>
            }
        ]
    }
]);

const stompClient: Client = over(new SockJS('http://localhost:8080/socket'));
stompClient.connect({}, _ => {
    console.log("WS Client connected.");
})

export const WsContext = createContext<Client>(stompClient);


const root = ReactDOM.createRoot(document.getElementById("root")!);
root.render(
    <Provider store={store}>
        <WsContext.Provider value={stompClient}>
            <RouterProvider router={router}/>
        </WsContext.Provider>
    </Provider>
)
