import {useContext, useState} from "react";

import breddit_logo from "../assets/breddit_logo.png"
import Button from "./Button.tsx";
import {AuthState, endSession, selectAuthState} from "../redux/slices/authSlice.ts";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import Dropdown from "./Dropdown.tsx";
import {changeTheme} from "../redux/slices/themeSlice.ts";
import {AppRoutes, Endpoints, ThemeType} from "../utils/constants.ts";
import {MdOutlineWbSunny} from "react-icons/md";
import {IoIosLeaf, IoIosMoon} from "react-icons/io";
import {Client} from "stompjs";
import {WsContext} from "../main.tsx";
import {useGetModel, useLogoutMutation} from "../redux/query/breddit-api.ts";
import {FaSearch} from "react-icons/fa";
import {IoClose} from "react-icons/io5";
import {PostView} from "../models/models.ts";

const Navbar = () => {
   //TODO: styling + functionality

   const [open, setOpen] = useState(false);
   const authState: AuthState = useSelector(selectAuthState);
   const dispatch = useDispatch();
   const navigate = useNavigate();
   const wsClient = useContext<Client>(WsContext);
   const [callLogout] = useLogoutMutation();
   const [searchText, setSearchText] = useState<string>("");
   const posts = useGetModel<PostView[]>(`${Endpoints.posts}/withSubName`);

   const removeSearch = () => setSearchText("");

   const navigateToPost = (post: PostView) => {
      setSearchText("");
      navigate(`${AppRoutes.SUBTHREAD}/${post.subthreadName}/${post.id}?returnURL=/home`)
   }

   const searchPost = () => {
      //    TODO: display a list in a page or something
   }

   const logout = () => {
      wsClient.disconnect(() => console.log("WS Client disconnected."));

      callLogout().then(_ => {
         dispatch(endSession());
         console.log("Successfully logged out.");
         navigate(AppRoutes.LOGIN);
      });
      // setTimeout(() => {
      //     dispatch(endSession());
      //     console.log("Successfully logged out.");
      //     navigate("/login");
      // }, 500);
   }
   //TODO: remove home buttons etc and replace with a search
   return (<>
         <header className={`flex w-full items-center bg-background-accent sticky top-0 left-0 z-20 border-b-2 border-primary-lighter`}>
            <div className="w-full">
               <div className="relative flex items-center justify-between w-full">
                  <div className="w-60 max-w-full px-4">
                     <img
                        src={breddit_logo}
                        alt="logo"
                        className="h-24 w-24 object-cover ml-5 cursor-pointer"
                        onClick={() => navigate(AppRoutes.HOME)}
                     />
                  </div>
                  <div className="flex w-full items-center justify-between px-4 pr-10">
                     <div>
                        <button
                           onClick={() => setOpen(!open)}
                           id="navbarToggler"
                           className={`${open && "navbarTogglerActive"} absolute right-4 top-1/2 block -translate-y-1/2 rounded-lg px-3 py-[6px] ring-primary focus:ring-2 lg:hidden`}>
                           <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                           <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                           <span className="relative my-[6px] block h-[2px] w-[30px] bg-body-color dark:bg-white"></span>
                        </button>
                        <nav
                           // :className="!navbarOpen && 'hidden' "
                           id="navbarCollapse"
                           className={`absolute right-4 top-full w-full max-w-[250px] rounded-lg bg-background-accent px-6 py-5 shadow dark:bg-dark-2 lg:static lg:block lg:w-full lg:max-w-full lg:shadow-none lg:dark:bg-transparent 
                                    ${!open && "hidden"} `}>
                           <ul className="block lg:flex">
                              {/*<ListItem linkTo="/home">Home</ListItem>*/}
                              {/*<ListItem linkTo="/ok">Payment</ListItem>*/}
                              {/*<ListItem linkTo="/#">About</ListItem>*/}
                              {/*<ListItem linkTo="/#">Blog</ListItem>*/}

                              {/* SEARCh */}
                              <div className="inline-block relative w-full">
                                 <div className="flex flex-row items-center justify-center border-2 border-primary-base px-3 py-2 gap-3 rounded-lg z-10">
                                    {searchText.length > 0 ?
                                       <IoClose className="text-primary-base cursor-pointer" size={20} onClick={removeSearch}/> :
                                       <FaSearch className="text-primary-base cursor-pointer" size={20} onClick={searchPost}/>
                                    }
                                    <input
                                       placeholder="Search posts"
                                       type="text"
                                       className="bg-transparent focus:outline-none text-background-text"
                                       value={searchText}
                                       onChange={e => setSearchText(e.target.value)}
                                    />
                                 </div>
                                 <div
                                    className="absolute left-0 text-background-text bg-background-accent flex flex-col w-full px-3 py-2 rounded-b-lg transition-all">
                                    {/*<span className="px-3 py-2 hover:bg-background-hover rounded-lg cursor-pointer transition-all">sedsadsa</span>*/}
                                    {searchText.length > 0 &&
                                       posts?.filter(post => {
                                          return searchText === "" ? true : post.title.toLowerCase().includes(searchText.toLowerCase());
                                       }).map(post => (
                                          <div
                                             onClick={() => navigateToPost(post)}
                                             className="px-3 py-2 hover:bg-background-hover rounded-lg cursor-pointer transition-all">
                                             <span className="block text-sm font-bold">
                                             {post.title}
                                             </span>
                                             <span className="font-extralight text-xs">s/{post.subthreadName}</span>
                                          </div>
                                       ))
                                    }
                                 </div>
                              </div>


                           </ul>
                        </nav>
                     </div>
                     <div className="hidden justify-end pr-16 sm:flex lg:pr-0 flex-row gap-5">
                        <div>
                           <Dropdown label="Theme" items={[
                              {
                                 label: "Light",
                                 icon: <MdOutlineWbSunny size={25} className="text-yellow-600"/>,
                                 onClick: () => dispatch(changeTheme(ThemeType.LIGHT))
                              },
                              {
                                 label: "Dark",
                                 icon: <IoIosMoon size={25} className="text-black"/>,
                                 onClick: () => dispatch(changeTheme(ThemeType.DARK))
                              },
                              {
                                 label: "Green",
                                 icon: <IoIosLeaf size={25} className="text-green-400"/>,
                                 onClick: () => dispatch(changeTheme(ThemeType.GREEN))
                              },
                           ]}/>
                        </div>
                        {authState.isLoggedIn &&
                           (<Button label="Log Out" onClick={logout}/>)
                        }
                     </div>
                  </div>
               </div>
            </div>
         </header>
      </>
   );
};

export default Navbar;

// type ListItemProps = {
//    children: JSX.Element | string,
//    linkTo: string,
// }
//
// const ListItem = ({children, linkTo}: ListItemProps) => {
//    return (
//       <>
//          <li>
//             <a href={linkTo}
//                className="flex py-2 text-nav-text font-medium text-body-color hover:text-primary-lighter lg:ml-12 lg:inline-flex transition duration-200 ease-linear">
//                {children}
//             </a>
//          </li>
//       </>
//    );
// };
