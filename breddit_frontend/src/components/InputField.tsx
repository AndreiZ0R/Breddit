import {HTMLInputTypeAttribute} from "react";

type InputFieldProps = {
    label: string,
    type: HTMLInputTypeAttribute,
    errorLabel?: string | null,
}

export default function InputField({label, type, errorLabel = null}: InputFieldProps) {

    return (
        <>
            <div className="relative mb-6">
                <input
                    type={type}
                    className={`peer m-0 block h-[58px] w-full rounded border border-solid ${errorLabel ? "border-red-400" : "border-primary-lighter"} bg-transparent bg-clip-padding px-3 py-4 text-base font-normal leading-tight ${errorLabel ? "text-red-400" : "text-primary-lighter"} transition duration-200 ease-linear placeholder:text-transparent ${errorLabel ? "focus:border-red-500" : "focus:border-primary-base"} focus:pb-[0.625rem] focus:pt-[1.625rem] ${errorLabel ? "focus:text-red-300" : "focus:text-secondary-base"} focus:outline-none peer-focus:text-primary-base [&:not(:placeholder-shown)]:pb-[0.625rem] [&:not(:placeholder-shown)]:pt-[1.625rem] `}
                    id={`floatingInput${label}`}
                    placeholder=""/>
                <label
                    htmlFor={`floatingInput${label}`}
                    className={`pointer-events-none absolute left-0 top-0 origin-[0_0] border border-solid border-transparent px-3 py-4 ${errorLabel ? "text-red-600" : "text-primary-lighter"} transition-[opacity,_transform] duration-200 ease-linear ${errorLabel ? "peer-focus:text-red-400" : "peer-focus:text-primary-base"} peer-focus:-translate-y-2 peer-focus:translate-x-[0.15rem] peer-focus:scale-[0.85] peer-[:not(:placeholder-shown)]:-translate-y-2 peer-[:not(:placeholder-shown)]:translate-x-[0.15rem] peer-[:not(:placeholder-shown)]:scale-[0.85] motion-reduce:transition-none`}>
                    {label}
                </label>
                <div className={`absolute w-full text-sm ${errorLabel ? "text-red-500" : "text-neutral-500"}`}>
                    {errorLabel}
                </div>
            </div>
        </>
    );
}