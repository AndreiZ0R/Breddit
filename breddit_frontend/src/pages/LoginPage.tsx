import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {AuthState, selectAuthState, startSession} from "../redux/slices/authSlice.ts";
import {useLocation, useNavigate} from "react-router-dom";
import {getRedirectedPath} from "../utils/uriHelper.ts";
import InputField from "../components/InputField.tsx";
import Button, {ButtonType} from "../components/Button.tsx";
import {AuthResponse} from "../models/models.ts";
import {useLoginMutation} from "../redux/query/breddit-api.ts";
import MountainsIllustration from "../assets/waves_illustr.jpg";
import {AppRoutes} from "../utils/constants.ts";
import {z} from "zod";
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";

//TODO: zod with form validation


const loginSchema = z.object({
   username: z.string({required_error: "Field is required"}).refine(data => data.trim() !== "", {message: "Username can not be empty"}),
   password: z.string({required_error: "Field is required"}).refine(data => data.trim() !== "", {message: "Password can not be empty"}),
});

type LoginFields = z.infer<typeof loginSchema>;

export default function LoginPage() {
   const dispatch = useDispatch();
   const authState: AuthState = useSelector(selectAuthState);
   const [login] = useLoginMutation();
   const navigate = useNavigate();
   const location = useLocation();

   const {
      register: registerFn,
      handleSubmit,
      setError,
      formState: {errors},
   } = useForm<LoginFields>({
      resolver: zodResolver(loginSchema),
   })


   useEffect(() => {
      if (authState.isLoggedIn) {
         navigate(getRedirectedPath(location.search));
      }
      return () => {
      };
   }, [authState]);

   const onSubmit: SubmitHandler<LoginFields> = (data) => {
      login(data).unwrap()
         .then((response: AuthResponse) => {
            dispatch(startSession(response));
            navigate(getRedirectedPath(location.search));
         })
         .catch(error => {
            setError("root", {message: error.message});
         });
   }

   const redirectToRegister = () => {
      navigate(AppRoutes.REGISTER);
   }

   return (
      <div className="flex flex-col items-center justify-center content-container w-full bg-background-base p-5 md:p-0 md:px-6">

         {/* Card */}
         <div
            className="md:w-full md:h-5/6 xl:w-5/6 xl:h-4/5  md:bg-background-accent w-full h-full flex justify-center items-center bg-transparent rounded-xl drop-shadow-lg transition-all duration-100 ease-in-out">

            {/* Image container */}
            <div className="md:h-full md:w-1/2 rounded-xl sm:h-0 sm:w-0">
               <img src={MountainsIllustration} className="object-cover md:h-full md:w-full rounded-l-xl sm:h-0 sm:w-0 hidden md:block" alt="ill"/>
            </div>

            {/* Form */}
            <div className="h-full md:w-4/5 lg:w-1/2 w-full lg:px-12 lg:py-7 md:px-10 md:py-4 p-3 flex flex-col justify-around">
               <div className="text-background-text text-4xl text-center">Sign in to your account</div>

               <form className="flex flex-col gap-5 animate-fadeIn" id="loginForm">
                  <InputField label="Username" type="text" name="username" registerFn={registerFn} errorLabel={errors.username?.message}/>
                  <InputField label="Password" type="password" name="password" registerFn={registerFn} errorLabel={errors.password?.message}/>
                  <span className="text-red-400">{errors.root?.message}</span>
               </form>

               <div className="flex flex-col gap-2">
                  <Button onClick={handleSubmit(onSubmit)} label="Login" type="submit" formId="loginForm" additionalStyles="w-full"/>
                  <Button onClick={redirectToRegister} label="Not a member yet? Create a free account" buttonType={ButtonType.TERTIARY}
                          additionalStyles={"w-full"}/>
               </div>
            </div>
         </div>

      </div>
   );
}