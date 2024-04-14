import ReactDOM from 'react-dom/client'
import './index.css'
import {Provider} from 'react-redux';
import {store} from './redux/store/store.ts'
import {createBrowserRouter, Navigate, Outlet, RouterProvider} from "react-router-dom";
import HomePage from "./pages/HomePage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import PrivateRoute from "./pages/PrivateRoute.tsx";
import React from 'react';
import Navbar from "./components/Navbar.tsx";


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
        // errorElement: <ErrorElement/>,
        children: [
            {
                path: "/",
                element: <Navigate to="/home" replace/>
            },
            {
                path: "/home",
                element: <PrivateRoute><HomePage/></PrivateRoute>,
            },
            {
                path: "/login",
                element: <LoginPage/>
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

const root = ReactDOM.createRoot(document.getElementById("root")!);
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <RouterProvider router={router}/>
        </Provider>
    </React.StrictMode>,
)
