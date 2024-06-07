import {BaseResponse, PostView} from "../models/models.ts";
import {useGetPostPicturesQuery} from "../redux/query/breddit-api.ts";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {AppRoutes} from "../utils/constants.ts";
import {formatDaysHours} from "../utils/date-format.ts";
import IconButton, {IconButtonType} from "./IconButton.tsx";
import {RiArrowDownDoubleFill, RiArrowUpDoubleFill} from "react-icons/ri";
import {MdOutlineKeyboardArrowDown, MdOutlineKeyboardArrowUp} from "react-icons/md";
import {FaCommentAlt} from "react-icons/fa";
import ImageSlider from "./ImageSlider.tsx";

type PostCardProps = {
   post: PostView,
}

enum VoteType {
   UP,
   NEUTRAL,
   DOWN
}

export default function PostCard({post}: PostCardProps) {
   //TODO: maybe show images only on detailed view?
   const {data: postImages, error} = useGetPostPicturesQuery(post.id);
   const [voting, setVoting] = useState<VoteType>(VoteType.NEUTRAL);
   const [votes, setVotes] = useState<number>(post.votes);
   const navigate = useNavigate();

   const getVotingSectionBackground = () => {
      switch (voting) {
         case VoteType.UP:
            return "bg-primary-base";
         case VoteType.NEUTRAL:
            return "bg-common-gray";
         case VoteType.DOWN:
            return "bg-rose-500";
      }
   }

   //TODO: make the call to update the votes

   const upVote = () => {
      setVoting(voting !== VoteType.UP ? VoteType.UP : VoteType.NEUTRAL);
      setVotes(voting === VoteType.NEUTRAL ? votes + 1 : voting === VoteType.DOWN ? votes + 2 : votes - 1);
   }
   const downVote = () => {
      setVoting(voting !== VoteType.DOWN ? VoteType.DOWN : VoteType.NEUTRAL);
      setVotes(voting === VoteType.NEUTRAL ? votes - 1 : voting === VoteType.UP ? votes - 2 : votes + 1);
   }

   const isNewPost = Math.floor(Math.abs(new Date(Date.now()).getTime() - new Date(post.postedAt).getTime()) / 36e5);

   const viewPost = () => {
      navigate(`${AppRoutes.SUBTHREAD}/${post.subthreadName}/${post.id}?returnURL=/home`)
   }

   if (error) {
      //TODO:
      const {message} = error as BaseResponse;
      console.log(message);
   }

   const redirectToUserPage = () => {
   };

   return (
      <div className="hover:bg-background-hover transition duration-200 ease-in-out scroll-auto px-6 py-3 rounded-xl"
           key={post.id}
      >
         <div className="flex flex-row items-center justify-start gap-5">
            <span className="text-xs transition-all ease-linear cursor-pointer hover:text-primary-base">s/{post.subthreadName}</span>
            <span className="text-xs">{formatDaysHours(post.postedAt)}</span>
            {isNewPost <= 24 && (
               <span className="bg-green-400/65 px-2 py-1 rounded-xl text-sm text-background-text font-bold">New</span>
            )}
         </div>

         <div className="text-2xl font-bold my-2.5 flex flex-row items-center justify-start" key={post.title}>
            <span>{post.title}</span>
            <span className="text-xs ml-4 text-background-text font-light transition-all cursor-pointer hover:text-primary-base"
                  onClick={redirectToUserPage}
            >u/{post.author.username}
            </span>
         </div>
         <div className="text-background-text/80 line-clamp-6 my-4">{post.body}</div>

         {postImages && postImages.payload.length > 0 &&
             <div className="w-full m-auto flex items-start justify-start rounded-xl my-5">
                 <ImageSlider images={postImages.payload}/>
             </div>
         }

         <div className="flex flex-row items-stretch justify-start gap-5 mt-2.5">
            <div className={`${getVotingSectionBackground()} transition-all ease-linear px-1 py-1 rounded-lg flex flex-row items-center justify-start gap-2`}>
               <IconButton
                  additionalStyles="hover:bg-primary-base"
                  icon={voting === VoteType.UP ? <RiArrowUpDoubleFill size={25}/> : <MdOutlineKeyboardArrowUp size={25}/>}
                  type={IconButtonType.EMPTY}
                  onClick={upVote}/>
               <span>{votes}</span>
               <IconButton
                  additionalStyles="hover:bg-rose-500"
                  icon={voting === VoteType.DOWN ? <RiArrowDownDoubleFill size={25}/> : <MdOutlineKeyboardArrowDown size={25}/>}
                  type={IconButtonType.EMPTY}
                  onClick={downVote}/>
            </div>
            <div
               onClick={viewPost}
               className="cursor-pointer flex flex-row items-center justify-start gap-2 bg-common-gray px-2 py-1 rounded-lg hover:bg-primary-base transition-all ease-linear">
               <IconButton
                  icon={<FaCommentAlt/>}
                  type={IconButtonType.EMPTY}
                  onClick={viewPost}/>
               <span>{post.comments.length.toString()} comments</span>
            </div>
         </div>


      </div>
   );
}