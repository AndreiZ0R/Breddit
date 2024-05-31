import MountainsIllustration from "../assets/mountains_illustr.jpg";
import InputField from "../components/InputField.tsx";
import Button, {ButtonType} from "../components/Button.tsx";
import {useNavigate} from "react-router-dom";
import {ChangeEvent, useState} from "react";
import {AppRoutes} from "../utils/constants.ts";
import Stepper from "../components/Stepper.tsx";
import FileUploader from "../components/FileUploader.tsx";

export default function RegisterPage() {

    // const dispatch = useDispatch();
    // const authState: AuthState = useSelector(selectAuthState);
    // const [login] = useLoginMutation();
    const navigate = useNavigate();
    // const location = useLocation();
    const [currentStep, setCurrentStep] = useState<number>(0);
    const [image, setImage] = useState<File | null>(null);
    const [registerData, setRegisterData] = useState({
        username: "",
        password: "",
        confirmPassword: "",
        email: "",
        country: "",
        birthDate: "",
        role: "User",
    });

    const redirectToLogin = () => {
        navigate(AppRoutes.LOGIN);
    }

    const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
        setRegisterData({
            ...registerData,
            [e.target.name]: e.target.value,
        });
    }

    const handleImageUpload = (image: File) => {
        setImage(image);
        setCurrentStep(currentStep + 1)
    }

    return (
        <div className="flex flex-col items-center justify-center content-container w-full bg-background-base p-5 md:p-0 md:px-6">
            {/*<Stepper steps={7} currentStep={5} stepsDirection="row"/>*/}

            {/* Card */}
            <div
                className="md:w-full md:h-5/6 xl:w-5/6 xl:h-4/5  md:bg-background-accent w-full h-full flex justify-center items-center bg-transparent rounded-xl drop-shadow-lg transition-all duration-100 ease-in-out">
                {/* Form */}
                <div className="h-full md:w-4/5 lg:w-1/2 w-full lg:px-12 lg:py-7 md:px-10 md:py-4 p-3 flex flex-col justify-around">
                    <Stepper steps={3} currentStep={currentStep} stepsDirection="row"/>

                    {/* Header */}
                    <div className="text-background-text text-4xl text-center">
                        {currentStep === 0 && <span>Sign up for a new account</span>}
                        {currentStep === 1 && <span>We need a few more details</span>}
                        {currentStep === 2 && <span>One last step</span>}
                        {currentStep > 2 && <span>You're all set!</span>}
                    </div>

                    {/* Inputs */}
                    <form>
                        {currentStep === 0 &&
                            (<div className="flex flex-col gap-5">
                                <InputField label="Username" name="username" type="text" value={registerData.username} onChange={handleInputChange}/>
                                <InputField label="Email" name="email" type="email" value={registerData.email} onChange={handleInputChange}/>
                                <InputField label="Country" name="country" type="text" value={registerData.country} onChange={handleInputChange}/>
                            </div>)
                        }
                        {currentStep === 1 &&
                            (<div className="flex flex-col gap-6">
                                <InputField label="Birth date" name="birthDate" type="date" value={registerData.birthDate} onChange={handleInputChange}/>
                                <InputField label="Password" name="password" type="password" value={registerData.password} onChange={handleInputChange}/>
                                <InputField label="Confirm password" name="confirmPassword" type="password" value={registerData.confirmPassword}
                                            onChange={handleInputChange}/>
                            </div>)
                        }
                        {currentStep >= 2 &&
                            (<div className="flex flex-col md:flex-row items-center justify-center gap-2 min-h-60">
                                <div className={`h-full ${image ? "w-1/2" : "w-full"}`}>
                                    <FileUploader onImageUploaded={handleImageUpload}/>
                                </div>
                                {image && (
                                    <div className="w-1/2 max-h-60 min-h-60 rounded-xl border-2 border-dotted border-primary-base overflow-hidden">
                                        <img src={URL.createObjectURL(image)} alt="preview" className="w-full h-full object-cover rounded-xl border-box"/>
                                    </div>)
                                }
                            </div>)
                        }
                    </form>

                    {/* Buttons */}
                    <div className="flex flex-col gap-2">
                        {currentStep === 0 &&
                            (<>
                                <Button label="Next" onClick={() => setCurrentStep(currentStep + 1)}/>
                                <Button onClick={redirectToLogin} label="Already a member? Sign in" type={ButtonType.TERTIARY}
                                        additionalStyles={"w-full"}/>
                            </>)
                        }
                        {currentStep === 1 &&
                            (<>
                                <Button label="Next" onClick={() => setCurrentStep(currentStep + 1)}/>
                                <Button label="Back" type={ButtonType.TERTIARY} onClick={() => setCurrentStep(currentStep - 1)}/>
                            </>)
                        }
                        {currentStep === 2 &&
                            (<>
                                <Button label="Back" type={ButtonType.TERTIARY} onClick={() => setCurrentStep(currentStep - 1)}/>
                            </>)
                        }
                        {currentStep > 2 &&
                            <Button onClick={() => {
                            }} label="Register" additionalStyles="w-full"/>}
                    </div>
                </div>

                {/* Image container */}
                <div className="md:h-full md:w-1/2 rounded-xl sm:h-0 sm:w-0">
                    <img src={MountainsIllustration} className="object-cover md:h-full md:w-full rounded-r-xl sm:h-0 sm:w-0 hidden md:block" alt="ill"/>
                </div>
            </div>

        </div>
    )
}