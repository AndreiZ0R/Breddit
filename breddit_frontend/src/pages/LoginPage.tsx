import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {AuthState, selectAuthState, startSession} from "../redux/slices/authSlice.ts";
import {useLocation, useNavigate} from "react-router-dom";
import {getRedirectedPath} from "../utils/uriHelper.ts";
import InputField from "../components/InputField.tsx";
import Button, {ButtonType} from "../components/Button.tsx";
import {AuthResponse} from "../models/models.ts";
import {useLoginMutation} from "../redux/query/breddit-api.ts";
import MountainsIllustration from "../assets/waves_illustr.jpg";
import {AppRoutes} from "../utils/constants.ts";

//TODO: zod with form validation
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

    const redirectToRegister = () => {
        navigate(AppRoutes.REGISTER);
    }

    return (
        <div className="flex flex-col items-center justify-center content-container w-full bg-background-base p-5 md:p-0 md:px-6">

            {/* Card */}
            <div className="md:w-full md:h-5/6 xl:w-5/6 xl:h-4/5  md:bg-background-accent w-full h-full flex justify-center items-center bg-transparent rounded-xl drop-shadow-lg transition-all duration-100 ease-in-out">

                {/* Image container */}
                <div className="md:h-full md:w-1/2 rounded-xl sm:h-0 sm:w-0">
                    <img src={MountainsIllustration} className="object-cover md:h-full md:w-full rounded-l-xl sm:h-0 sm:w-0 hidden md:block" alt="ill"/>
                </div>

                {/* Form */}
                <div className="h-full md:w-4/5 lg:w-1/2 w-full lg:px-12 lg:py-7 md:px-10 md:py-4 p-3 flex flex-col justify-around">
                    <div className="text-background-text text-4xl text-center">Sign in to your account</div>

                    <div className="flex flex-col gap-5">
                        <InputField type="text" label="Username" value={credentials.username} onChange={(newValue) =>
                            setCredentials((prev) => ({...prev, username: newValue.target.value}))}/>
                        <InputField type="password" label="Password" value={credentials.password} onChange={(newValue) =>
                            setCredentials((prev) => ({...prev, password: newValue.target.value}))}/>
                    </div>

                    <div className="flex flex-col gap-2">
                        <Button onClick={startLogin} label="Login" additionalStyles="w-full"/>
                        <Button onClick={redirectToRegister} label="Not a member yet? Create a free account" buttonType={ButtonType.TERTIARY}
                                additionalStyles={"w-full"}/>
                    </div>
                </div>
            </div>


            {/*<div style={{height: 59, width: 15}}></div>*/}
            {/*<div className="flex flex-row gap-5">*/}
            {/*    <InputField type="text" label="Username" additionalStyles={"w-1/2"} value={credentials.username} onChange={(newValue) =>*/}
            {/*        setCredentials((prev) => ({...prev, username: newValue.target.value}))}/>*/}
            {/*    <InputField type="password" label="Password" additionalStyles={"w-1/2"} value={credentials.password} onChange={(newValue) =>*/}
            {/*        setCredentials((prev) => ({...prev, password: newValue.target.value}))}/>*/}
            {/*</div>*/}


            {/*<Button onClick={startLogin} label="Login" additionalStyles="w-1/2"/>*/}
            {/*<Checkbox/>*/}


            {/*<div className="h-10 w-1"></div>*/}
            {/*<div className="text-primary bg-background">Teeext</div>*/}
            {/*<div className=" text-primary bg-background">Teeext</div>*/}
            {/*<div className=" text-primary bg-background">Teeext</div>*/}

            {/*<Button onClick={() => dispatch(changeTheme(ThemeType.LIGHT))} label="Light theme"/>*/}
            {/*<Button onClick={() => dispatch(changeTheme(ThemeType.DARK))} label="Dark theme"/>*/}
            {/*<Button onClick={() => dispatch(changeTheme(ThemeType.GREEN))} label="Green theme"/>*/}
            {/*<IconButton onClick={() => {*/}
            {/*}} text="Search" icon={<FaSearch color="white"/>}/>*/}


        </div>
    );
}