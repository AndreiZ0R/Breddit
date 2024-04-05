import ReactDOM from 'react-dom/client'
import './index.css'
import {Provider} from 'react-redux';
import {store} from './redux/store/store.ts'
import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import HomePage from "./pages/HomePage.tsx";
import LoginPage from "./pages/LoginPage.tsx";
import PrivateRoute from "./pages/PrivateRoutes.tsx";


function Layout() {
    return (
        <>
            {/*<Header/>*/}
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
                element: <PrivateRoute/>
            },
            {
                path: "/home",
                element: <HomePage/>
            },
            {
                path: "/login",
                element: <LoginPage/>
            },
        ]
    }
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
    // <React.StrictMode>
    <Provider store={store}>
        <RouterProvider router={router}/>
    </Provider>
    // </React.StrictMode>,
)
