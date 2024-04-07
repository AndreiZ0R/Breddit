import {useSelector} from "react-redux";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {Navigate, useLocation} from 'react-router-dom';
import {ReactNode} from "react";

type PrivateRouteProps = {
    children: ReactNode,
}

const PrivateRoute = ({children}: PrivateRouteProps): JSX.Element => {
    const authState: AuthState = useSelector(selectAuthState);
    const location = useLocation();

    return authState.isLoggedIn ?
        (<>{children}</>)
        : <Navigate to={`/login?from=${location.pathname}${location.search}`} replace={true} /*state={{from: `${location.pathname}${location.search}`}}*//>;
};
export default PrivateRoute;