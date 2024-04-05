import {useSelector} from "react-redux";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {Navigate, Outlet} from 'react-router-dom';


const PrivateRoute = () => {
    const authState: AuthState = useSelector(selectAuthState);
    return authState.isLoggedIn ? <Outlet/> : <Navigate to="/login" replace/>;
};

export default PrivateRoute;