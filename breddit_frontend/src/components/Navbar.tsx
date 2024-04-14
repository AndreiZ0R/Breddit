import {useState} from "react";

import breddit_logo from "../assets/breddit_logo.png"

const Navbar = () => {
    //TODO: styling + functionality

    const [open, setOpen] = useState(false);

    return (<>
            <header className={`flex w-full items-center bg-background-accent sticky top-0 left-0 z-20 border-b-2 border-primary-lighter`}>
                <div className="container">
                    <div className="relative flex items-center justify-between">
                        <div className="w-60 max-w-full px-4">
                            <img
                                src={breddit_logo}
                                alt="logo"
                                className="h-24 w-24 object-cover ml-5"
                            />
                        </div>
                        <div className="flex w-full items-center justify-between px-4">
                            <div>
                                <button
                                    onClick={() => setOpen(!open)}
                                    id="navbarToggler"
                                    className={` ${
                                        open && "navbarTogglerActive"
                                    } absolute right-4 top-1/2 block -translate-y-1/2 rounded-lg px-3 py-[6px] ring-primary focus:ring-2 lg:hidden`}
                                >
                                    <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                                    <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                                    <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                                </button>
                                <nav
                                    // :className="!navbarOpen && 'hidden' "
                                    id="navbarCollapse"
                                    className={`absolute right-4 top-full w-full max-w-[250px] rounded-lg bg-background-accent px-6 py-5 shadow dark:bg-dark-2 lg:static lg:block lg:w-full lg:max-w-full lg:shadow-none lg:dark:bg-transparent ${
                                        !open && "hidden"
                                    } `}
                                >
                                    <ul className="block lg:flex">
                                        <ListItem linkTo="/ok">Home</ListItem>
                                        <ListItem linkTo="/#">Payment</ListItem>
                                        <ListItem linkTo="/#">About</ListItem>
                                        <ListItem linkTo="/#">Blog</ListItem>
                                    </ul>
                                </nav>
                            </div>
                            <div className="hidden justify-end pr-16 sm:flex lg:pr-0">
                                <a
                                    href="/#"
                                    className="px-7 py-3 text-base font-medium text-dark hover:text-primary dark:text-white"
                                >
                                    Sign in
                                </a>

                                <a
                                    href="/#"
                                    className="rounded-md bg-primary px-7 py-3 text-base font-medium text-white hover:bg-primary/90"
                                >
                                    Sign Up
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        </>
    );
};

export default Navbar;

type ListItemProps = {
    children: JSX.Element | string,
    linkTo: string,
}

const ListItem = ({children, linkTo}: ListItemProps) => {
    return (
        <>
            <li>
                <a
                    href={linkTo}
                    className="flex py-2 text-background-text font-medium text-body-color hover:text-primary-lighter lg:ml-12 lg:inline-flex transition duration-200 ease-linear"
                >
                    {children}
                </a>
            </li>
        </>
    );
};
