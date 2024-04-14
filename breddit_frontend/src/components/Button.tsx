import {BaseProps} from "../utils/constants.ts";
import {MouseEventHandler} from "react";

type ButtonProps = {
    label: string,
    onClick: MouseEventHandler<HTMLElement>,
} & BaseProps;

export default function Button({label, onClick, additionalStyles}: ButtonProps) {
    return (
        <>
            <button
                className={`h-10 w-15 bg-primary-base text-white rounded-xl bold align-middle transition duration-300 ease-linear hover:bg-primary-lighter ${additionalStyles}`}
                onClick={onClick}>{label}
            </button>
        </>
    )
}