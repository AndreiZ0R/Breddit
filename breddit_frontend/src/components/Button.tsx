import {BaseProps} from "../utils/constants.ts";
import {MouseEventHandler} from "react";

export enum ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

type ButtonProps = {
    label: string,
    onClick: MouseEventHandler<HTMLElement>,
    type?: ButtonType,
} & BaseProps;

export default function Button({label, onClick, type = ButtonType.PRIMARY, additionalStyles}: ButtonProps) {

    const primaryStyle = "px-3 py-2 bg-primary-base text-white rounded-xl bold align-middle transition duration-300 ease-linear hover:bg-primary-lighter";
    const secondaryStyle = "px-3 py-2 bg-transparent text-primary-base border border-primary-base rounded-xl bold align-middle transition duration-300 ease-linear hover:bg-primary-lighter hover:border-transparent hover:text-white";
    const tertiaryStyle = "px-3 py-2 bg-transparent text-primary-base rounded-xl bold align-middle transition duration-300 ease-linear hover:text-primary-lighter underline";


    const getButtonStyle = (): string => {
        switch (type) {
            case ButtonType.PRIMARY:
                return primaryStyle;
            case ButtonType.SECONDARY:
                return secondaryStyle;
            case ButtonType.TERTIARY:
                return tertiaryStyle;
            default:
                return primaryStyle;
        }
    }

    return (
        <>
            <button
                className={`${getButtonStyle()} ${additionalStyles}`}
                onClick={onClick}>
                {label}
            </button>
        </>
    )
}