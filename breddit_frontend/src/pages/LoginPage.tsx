import {useDispatch} from "react-redux";
import {useLoginMutation} from "../redux/query/breddit-api.ts";
import {AuthResponse} from "../models/models.ts";
import {useState} from "react";
import {startSession} from "../redux/slices/authSlice.ts";
import {useNavigate} from "react-router-dom";

export default function LoginPage() {
    // const count = useSelector(selectCount);

    // const {data: domainModel} = useGetDomainModelQuery("/subthreads");
    // const models = useGetModel<User[]>("/users");

    const dispatch = useDispatch();
    const [login] = useLoginMutation();
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    });

    // useEffect(() => {
    //     if (models)
    //         models.forEach((model: User) => console.log(model));
    // }, [models]);

    const startLogin = () => {
        login(credentials).unwrap()
            .then((response: AuthResponse) => {
                dispatch(startSession(response));
                navigate("/home");
            })
            .catch(error => console.log(error));
    }

    //TODO: react router dom + redirects in case not logged in + maybe theming: chakra / tailwind??
    return (
        <>
            <div>
                {/*<button onClick={() => dispatch(increment())}>Increment</button>*/}
                {/*<span>{count}</span>*/}
                {/*<button onClick={() => dispatch(decrement())}>Increment</button>*/}
                <input type="text" value={credentials.username} onChange={(newValue) =>
                    setCredentials((prev) => ({...prev, username: newValue.target.value}))}/>
                <input type="text" value={credentials.password} onChange={(newValue) =>
                    setCredentials((prev) => ({...prev, password: newValue.target.value}))}/>


                <button onClick={startLogin}>Login</button>
            </div>
        </>
    );
}