import {useState} from "react";

export default function Dropdown() {
    const [open, setOpen] = useState<boolean>(false);

    return (
        <>
            <div className="w-full shadow">
                <button
                    onClick={() => setOpen(!open)}
                    className={`bg-background-accent text-background-text px-3 py-2 rounded-xl ${open ? "rounded-b-none" : ""} w-full`}>
                    Open Menu
                </button>
                {
                    open && (
                        <div className={`px-3 bg-background-accent ${open ? "rounded-b-xl" : ""}`}>
                            <div className="h-0.5 w-full bg-background-hover"></div>
                            <ul className={`w-full relative z-10 min-w-[180px] overflow-auto rounded-lg py-3 text-sm`}>
                                <li className="block w-full cursor-pointer select-none rounded-lg pt-[9px] p-2 text-background-text leading-tight transition duration-100 ease-linear hover:bg-background-hover focus:bg-blue-gray-50 focus:bg-opacity-80 focus:text-blue-gray-900 active:bg-blue-gray-50 active:bg-opacity-80 active:text-blue-gray-900">
                                    Menu Item 1
                                </li>
                                <li className="block w-full cursor-pointer select-none rounded-lg pt-[9px] p-2 text-background-text leading-tight transition duration-100 ease-linear hover:bg-background-hover focus:bg-blue-gray-50 focus:bg-opacity-80 focus:text-blue-gray-900 active:bg-blue-gray-50 active:bg-opacity-80 active:text-blue-gray-900">
                                    Menu Item 1
                                </li>
                                <li className="block w-full cursor-pointer select-none rounded-lg pt-[9px] p-2 text-background-text leading-tight transition duration-100 ease-linear hover:bg-background-hover focus:bg-blue-gray-50 focus:bg-opacity-80 focus:text-blue-gray-900 active:bg-blue-gray-50 active:bg-opacity-80 active:text-blue-gray-900">
                                    Menu Item 1
                                </li>
                            </ul>
                        </div>

                    )
                }
            </div>
        </>
    );
}