import {BaseProps} from "../utils/constants.ts";
import {MouseEventHandler} from "react";
import {AiOutlineLoading} from "react-icons/ai";

export enum ButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY
}

type ButtonProps = {
    label: string,
    onClick: MouseEventHandler<HTMLElement>,
    buttonType?: ButtonType,
    type?: "button" | "submit" | "reset",
    formId?: string;
    disabled?: boolean;
} & BaseProps;

export default function Button({label, onClick, buttonType = ButtonType.PRIMARY, additionalStyles, type = "button", formId, disabled}: ButtonProps) {

    const primaryStyle = "px-3 py-2 bg-primary-base text-white rounded-md bold align-middle transition duration-200 ease-linear hover:bg-primary-lighter";
    const secondaryStyle = "px-3 py-2 bg-transparent text-primary-base border border-primary-base rounded-md bold align-middle transition duration-200 ease-linear hover:bg-primary-lighter hover:border-transparent hover:text-white";
    const tertiaryStyle = "px-3 py-2 bg-transparent text-primary-base rounded-md bold align-middle transition duration-200 ease-linear hover:text-primary-lighter underline";

    const getButtonStyle = (): string => {
        switch (buttonType) {
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
        <button
            disabled={disabled}
            type={type}
            form={formId}
            className={`${getButtonStyle()} ${additionalStyles} ${disabled ? "cursor-not-allowed bg-primary-base/25 hover:bg-primary-base/25 flex flex-row items-center justify-center gap-3" : ""} `}
            onClick={onClick}>
            {label}
            {disabled ? <AiOutlineLoading className="text-white animate-spin"/> : ""}
        </button>
    )
}