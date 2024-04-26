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

// const sock: WebSocket = new SockJS('http://localhost:8080/socket');
const stompClient = over(new SockJS('http://localhost:8080/socket'));
stompClient.connect({}, _ => {
    console.log("connect");
})

export const WsContext = createContext<Client>(stompClient);


const root = ReactDOM.createRoot(document.getElementById("root")!);
root.render(
    // <React.StrictMode>
    <Provider store={store}>
        <WsContext.Provider value={stompClient}>
            <RouterProvider router={router}/>
        </WsContext.Provider>
    </Provider>
    // </React.StrictMode>,
)
