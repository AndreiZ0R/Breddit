import {useSelector} from "react-redux";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {bredditApi, useGetModel, useGetProfilePictureQuery} from "../redux/query/breddit-api.ts";
import {PostView} from "../models/models.ts";
import {Endpoints, Queries} from "../utils/constants.ts";
import {useContext, useEffect, useState} from "react";
import {Client, Message} from "stompjs";
import {WsContext} from "../main.tsx";
import {toast, Toaster} from "react-hot-toast";
import Avatar from "../components/Avatar.tsx";
import {FaAngleDoubleLeft, FaAngleDoubleRight, FaPlus} from "react-icons/fa";
import IconButton, {IconButtonType} from "../components/IconButton.tsx";
import {RiHome4Line} from "react-icons/ri";
import {MdDynamicFeed} from "react-icons/md";
import {successToastOptions} from "../utils/toast.tsx";
import PostCard from "../components/PostCard.tsx";

export default function HomePage() {
   const authState: AuthState = useSelector(selectAuthState);
   const posts = useGetModel<PostView[]>(`${Endpoints.posts}/withSubName`);
   const {data: profilePicture} = useGetProfilePictureQuery(authState.user!.username);
   const wsClient = useContext<Client>(WsContext);
   const [dashboardExpanded, setDashboardExpanded] = useState<boolean>(false);

   const subscribeToNotifications = () => {
      wsClient.subscribe("/topic/notifications", (message: Message) => {
         const res = JSON.parse(message.body).body;
         toast.success(`File ${res.payload} successfully uploaded!`, successToastOptions());
         bredditApi.util?.invalidateTags([Queries.getPosts]);
      });
   }

   useEffect(() => {
      console.log(profilePicture)
   }, [profilePicture]);

   useEffect(() => {
      setTimeout(() => {
         subscribeToNotifications();
      }, 1000);

      console.log(wsClient);
      return () => {
      };
   }, [wsClient]);

   const toggleDashboard = () => {
      setDashboardExpanded(!dashboardExpanded);
   }

   const sendToProfile = () => {

   }

   return (
      <div
         className="bg-background-base text-background-text content-container w-full flex flex-row transition-all duration-200 ease-linear selection:bg-primary-base selection:text-background-text">

         {/* Left dashboard */}
         <div className="flex flex-row transition-all duration-200 ease-linear">
            {/* Content */}
            <div className={`px-3 py-4 flex flex-col justify-between items-center transition-all duration-200 ease-linear `}>

               <div className="flex flex-col items-center justify-start gap-7">
                  {/* Avatar */}
                  <div className="flex flex-col items-center justify-center transition-all duration-200 ease-linear">
                     <Avatar size={12} url={profilePicture?.payload.toString() ?? ""} onClick={sendToProfile}/>
                     {dashboardExpanded && (
                        <div className="text-sm mt-1 transition-all cursor-pointer hover:text-primary-base" onClick={sendToProfile}>
                           u/{authState.user?.username}
                        </div>)
                     }
                  </div>

                  <div
                     className={`flex flex-row items-center ${dashboardExpanded ? "justify-start" : "justify-center"} w-full gap-2 hover:bg-primary-lighter transition-all duration-200 ease-linear cursor-pointer px-3 py-2 rounded-lg`}>
                     <RiHome4Line size={20}/>
                     {dashboardExpanded && (<span>Home</span>)}
                  </div>

                  <div
                     className={`flex flex-row items-center ${dashboardExpanded ? "justify-start" : "justify-center"} w-full gap-2 hover:bg-primary-lighter transition-all duration-200 ease-linear cursor-pointer px-3 py-2 rounded-lg`}>
                     <FaPlus size={20}/>
                     {dashboardExpanded && (<span>Post</span>)}
                  </div>

                  <div
                     className={`flex flex-row items-center ${dashboardExpanded ? "justify-start" : "justify-center"} w-full gap-2 hover:bg-primary-lighter transition-all duration-200 ease-linear cursor-pointer px-3 py-2 rounded-lg`}>
                     <MdDynamicFeed size={20}/>
                     {dashboardExpanded && (<span>Community</span>)}
                  </div>
               </div>


               <IconButton
                  icon={dashboardExpanded ? <FaAngleDoubleLeft className="text-background-text"/> : <FaAngleDoubleRight className="text-background-text"/>}
                  onClick={toggleDashboard}
                  type={IconButtonType.EMPTY}/>
            </div>
            {/* Dashboard divider */}
            <div className="bg-primary-lighter my-3 min-w-0.5"></div>
         </div>


         {/* Content */}
         <div className="flex-grow overflow-auto px-6 py-5">

            <div className="2xl:px-60 xl:px-52 lg:px-32 md:px-16 p-0">
               {
                  posts?.map((post: PostView, index: number) => (
                     <>
                        <div className="w-full h-0.5 bg-common-gray my-2.5"></div>
                        <PostCard post={post} key={index}/>
                     </>
                  ))
               }
               {
                  posts?.map((post: PostView, index: number) => (
                     <>
                        <div className="w-full h-0.5 bg-common-gray my-2.5"></div>
                        <PostCard post={post} key={index}/>
                     </>
                  ))
               }
               <div className="w-full h-0.5 bg-common-gray mt-2.5"></div>
            </div>
         </div>

         <Toaster/>
      </div>
   )
}

