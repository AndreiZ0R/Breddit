import {useDispatch, useSelector} from "react-redux";
import {decrement, increment, selectCount} from "./redux/slices/counterSlice.ts";
import {useGetDomainModelQuery, useLoginMutation} from "./redux/query/breddit-api.ts";
import {useEffect, useState} from "react";
import {AuthResponse} from "./models/models.ts";
import {startSession} from "./redux/slices/authSlice.ts";

function App() {
    const count = useSelector(selectCount);

    const {data: domainModel} = useGetDomainModelQuery("/subthreads");

    const dispatch = useDispatch();
    const [login] = useLoginMutation();
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    });

    useEffect(() => {
        console.log(domainModel)
    }, [domainModel]);

    const startLogin = () => {
        login(credentials).unwrap()
            .then((response: AuthResponse) => dispatch(startSession(response)))
            .catch(error => console.log(error));
    }

    //TODO: react router dom + redirects in case not logged in + maybe theming: chakra / tailwind?
    return (
        <>
            <div>
                <button onClick={() => dispatch(increment())}>Increment</button>
                <span>{count}</span>
                <button onClick={() => dispatch(decrement())}>Increment</button>
                <input type="text" value={credentials.username} onChange={(newValue) =>
                    setCredentials((prev) => ({...prev, username: newValue.target.value}))}/>
                <input type="text" value={credentials.password} onChange={(newValue) =>
                    setCredentials((prev) => ({...prev, password: newValue.target.value}))}/>


                <button onClick={startLogin}>Login</button>
            </div>
        </>
    )
}

export default App
