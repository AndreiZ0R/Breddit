import {MouseEventHandler, ReactElement, useState} from "react";
import {IoIosArrowDown, IoIosArrowUp} from "react-icons/io";

export type DropdownItem = {
    label: string,
    onClick: MouseEventHandler<HTMLElement>,
    icon?: ReactElement,
}

type DropdownProps = {
    label: string,
    items: DropdownItem[],
}

export default function Dropdown({label, items}: DropdownProps) {
    const [open, setOpen] = useState<boolean>(false);

    return (
        <div className=" inline-block relative w-full" onClick={() => setOpen(!open)}>
            <button className="bg-background-hover  text-background-text  font-semibold py-2 px-4 rounded inline-flex items-center">
                <span className="mr-1">{label}</span>
                {open ? <IoIosArrowUp/> : <IoIosArrowDown/>}
            </button>

            {open && (
                <ul className="dropdown-menu absolute pt-1 rounded-b-lg ">
                    {
                        items.map((item, index) => (
                            <div className="cursor-pointer transition duration-200 ease-in-out inline-flex items-center bg-background-hover hover:bg-background-accent p-3 gap-2.5 w-full" onClick={item.onClick}>
                                {item.icon ?? item.icon}
                                <li className="text-background-text" key={index}>
                                    {item.label}
                                </li>
                            </div>
                        ))
                    }


                    {/*<li className=""><a*/}
                    {/*    className="rounded-t bg-gray-200 dark:bg-gray-600 hover:bg-gray-400 dark:hover:bg-gray-700 py-2 px-4 block whitespace-no-wrap"*/}
                    {/*    href="#">One</a></li>*/}
                    {/*<li className=""><a className="bg-gray-200 dark:bg-gray-600 hover:bg-gray-400 dark:hover:bg-gray-700 py-2 px-4 block whitespace-no-wrap"*/}
                    {/*                    href="#">Two</a></li>*/}
                    {/*<li className="">*/}
                    {/*    <a className="rounded-b bg-gray-200 dark:bg-gray-600 hover:bg-gray-400 dark:hover:bg-gray-700 py-2 px-4 block whitespace-no-wrap"*/}
                    {/*    href="#">Three*/}
                    {/*    is the magic number</a>*/}
                    {/*</li>*/}
                </ul>
            )}

        </div>
    );
}