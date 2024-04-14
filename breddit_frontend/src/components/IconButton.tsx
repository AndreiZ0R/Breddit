import {MouseEventHandler, ReactElement} from "react";
import {BaseProps} from "../utils/constants.ts";

type IconButtonProps = {
    text: string,
    icon: ReactElement,
    onClick: MouseEventHandler<HTMLElement>,
} & BaseProps;

export default function IconButton({text, icon, onClick, additionalStyles}: IconButtonProps) {

    return (
        <button
            onClick={onClick}
            className={`flex flex-row align-middle justify-center items-center p-2 bg-primary-base text-white rounded-xl space-x-2 transition duration-300 ease-in-out hover:bg-primary-lighter ${additionalStyles}`}>
            <p>{text}</p>
            <>{icon}</>
        </button>
    )
}