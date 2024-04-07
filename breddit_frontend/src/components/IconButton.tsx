import {ReactElement} from "react";

type IconButtonProps = {
    text: string,
    icon: ReactElement,
}

export default function IconButton({text, icon}: IconButtonProps) {

    return (
        <button className="flex flex-row align-middle justify-center items-center p-2 bg-slate-400 rounded-xl space-x-2 hover:bg-background-hover">
            <p className="text-lg">{text}</p>
            <>{icon}</>
        </button>
    )
}