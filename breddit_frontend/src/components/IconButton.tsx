import {MouseEventHandler, ReactElement} from "react";
import {BaseProps} from "../utils/constants.ts";

export enum IconButtonType {
    FILLED,
    EMPTY,
}

type IconButtonProps = {
    text?: string,
    icon: ReactElement,
    onClick: MouseEventHandler<HTMLElement>,
    type?: IconButtonType,
} & BaseProps;

export default function IconButton({text, icon, onClick, type = IconButtonType.FILLED, additionalStyles}: IconButtonProps) {

    const filledStyle = "bg-primary-base hover:bg-primary-lighter p-2";

    const getButtonStyle = (): string => {
        switch (type) {
            case IconButtonType.FILLED:
                return filledStyle;
            case IconButtonType.EMPTY:
                return "";
            default:
                return filledStyle;
        }
    }

    return (
        <button
            onClick={onClick}
            className={`flex flex-row align-middle justify-center items-center text-white rounded-md space-x-2 transition duration-200 ease-in-out shadow-2xl ${getButtonStyle()} ${additionalStyles}`}>
            {text && <p>{text}</p>}
            {icon}
        </button>
    )
}