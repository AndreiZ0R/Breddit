import {useNavigate} from "react-router-dom";

type PrivateRouteProps = {
    children: Element[]
}
//TODO:
export default function PrivateRoute({children}: PrivateRouteProps) {
    const user = undefined;
    const navigate = useNavigate();

    if (!user) {
        navigate("/login")
    }

    return children;
}