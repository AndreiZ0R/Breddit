import {useDispatch, useSelector} from "react-redux";
import {useLoginMutation} from "../redux/query/breddit-api.ts";
import {AuthResponse} from "../models/models.ts";
import {useEffect, useState} from "react";
import {AuthState, selectAuthState, startSession} from "../redux/slices/authSlice.ts";
import {useLocation, useNavigate} from "react-router-dom";
import {changeTheme} from "../redux/slices/themeSlice.ts";
import {ThemeType} from "../utils/constants.ts";
import {getRedirectedPath} from "../utils/uriHelper.ts";
import Button from "../components/Button.tsx";
import IconButton from "../components/IconButton.tsx";
import {FaSearch} from "react-icons/fa";
import InputField from "../components/InputField.tsx";
import Checkbox from "../components/Checkbox.tsx";

export default function LoginPage() {
    const dispatch = useDispatch();
    const authState: AuthState = useSelector(selectAuthState);
    const [login] = useLoginMutation();
    const navigate = useNavigate();
    const location = useLocation();
    const [credentials, setCredentials] = useState({
        username: "",
        password: "",
    });

    useEffect(() => {
        if (authState.isLoggedIn) {
            navigate(getRedirectedPath(location.search));
        }
        return () => {
        };
    }, [authState]);

    const startLogin = () => {
        login(credentials).unwrap()
            .then((response: AuthResponse) => {
                dispatch(startSession(response));
                navigate(getRedirectedPath(location.search));
            })
            .catch(error => console.log(error));
    }

    return (
        <>
            <div className="flex flex-col content-container w-full bg-background-base scroll-auto p-3">
                {/*<button onClick={() => dispatch(increment())}>Increment</button>*/}
                {/*<span>{count}</span>*/}
                {/*<button onClick={() => dispatch(decrement())}>Increment</button>*/}
                <div style={{height: 59, width: 15}}></div>

                <div className="flex flex-row gap-5">
                    <InputField type="text" label="Username" additionalStyles={"w-1/2"} value={credentials.username} onChange={(newValue) =>
                        setCredentials((prev) => ({...prev, username: newValue.target.value}))}/>
                    <InputField type="password" label="Password" additionalStyles={"w-1/2"} value={credentials.password} onChange={(newValue) =>
                        setCredentials((prev) => ({...prev, password: newValue.target.value}))}/>
                </div>


                <Button onClick={startLogin} label="Login" additionalStyles="w-1/2"/>
                <Checkbox/>

                {/*<Button label={"send message"} onClick={sendMessage}/>*/}

                <div className="h-10 w-1"></div>
                <div className="text-primary bg-background">Teeext</div>
                <div className=" text-primary bg-background">Teeext</div>
                <div className=" text-primary bg-background">Teeext</div>

                <Button onClick={() => dispatch(changeTheme(ThemeType.LIGHT))} label="Light theme"/>
                <Button onClick={() => dispatch(changeTheme(ThemeType.DARK))} label="Dark theme"/>
                <Button onClick={() => dispatch(changeTheme(ThemeType.GREEN))} label="Green theme"/>
                {/*<IconButton text="Search" icon={<FaSearch color={themeColors.primary.base} />}/>*/}
                <IconButton onClick={() => {
                }} text="Search" icon={<FaSearch color="white"/>}/>


            </div>
        </>
    );
}