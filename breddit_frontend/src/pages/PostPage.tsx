import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {useAddCommentMutation, useGetModel, useGetProfilePictureQuery} from "../redux/query/breddit-api.ts";
import {Comment, Post, Subthread} from "../models/models.ts";
import {Endpoints} from "../utils/constants.ts";
import {formatDaysHours} from "../utils/date-format.ts";
import IconButton, {IconButtonType} from "../components/IconButton.tsx";
import {RiArrowDownDoubleFill, RiArrowUpDoubleFill} from "react-icons/ri";
import {MdOutlineKeyboardArrowDown, MdOutlineKeyboardArrowUp} from "react-icons/md";
import {FaArrowLeft, FaCommentAlt} from "react-icons/fa";
import Button from "../components/Button.tsx";
import {CreateCommentRequest} from "../models/requests.ts";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {useSelector} from "react-redux";

enum VoteType {
   UP,
   NEUTRAL,
   DOWN
}

export default function PostPage() {
   const [searchParams, __] = useSearchParams();
   const {postId} = useParams();
   const authState: AuthState = useSelector(selectAuthState);
   const post = useGetModel<Post>(`${Endpoints.posts}/${postId}`);
   const subthread = useGetModel<Subthread>(`${Endpoints.subthreads}/${post?.subthreadId}`)
   const [addComment] = useAddCommentMutation();
   const navigate = useNavigate();

   const [voting, setVoting] = useState<VoteType>(VoteType.NEUTRAL);
   const [votes, setVotes] = useState<number>(post?.votes ?? 0);

   const [newCommentData, setNewCommentData] = useState<CreateCommentRequest>({
      body: "",
      postId: post?.id ?? 0,
      parentId: null,
      authorId: authState.user?.id ?? 0,
   })

   useEffect(() => {
      if (post)
         setNewCommentData({...newCommentData, postId: post.id})
   }, [post]);

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

   const postComment = () => {
      addComment(newCommentData).unwrap()
         .then(__ => {
            setNewCommentData({...newCommentData, body: ""})
            //TODO: invalidate cache!!
            //temp solution
            window.location.reload();
         })
         .catch(err => console.log(err));
   }

   const upVote = () => {
      setVoting(voting !== VoteType.UP ? VoteType.UP : VoteType.NEUTRAL);
      setVotes(voting === VoteType.NEUTRAL ? votes + 1 : voting === VoteType.DOWN ? votes + 2 : votes - 1);
   }
   const downVote = () => {
      setVoting(voting !== VoteType.DOWN ? VoteType.DOWN : VoteType.NEUTRAL);
      setVotes(voting === VoteType.NEUTRAL ? votes - 1 : voting === VoteType.UP ? votes - 2 : votes + 1);
   }

   const groupComments = (comments: Comment[]) => {
      const groupedComments: Record<number, Comment[]> = {}

      comments.forEach(comment => {
         if (comment.parentId === null)
            groupedComments[comment.id] = [];
      })

      comments.forEach(comment => {
         if (comment.parentId !== null) {
            groupedComments[comment.parentId].push(comment);
         }
      })

      return groupedComments;
   }

   const getReplies = (id: number) => {
      return groupComments(post?.comments ?? [])[id] ?? undefined;
   }

   const goBack = () => {
      const returnURL = searchParams.get("returnURL");
      if (returnURL)
         navigate(returnURL)
   }

   return (
      <div
         className="bg-background-base text-background-text content-container w-full flex flex-row items-center justify-center transition-all duration-200 ease-linear selection:bg-primary-base selection:text-background-text">
         {/*<span className="text-xs transition-all ease-linear cursor-pointer hover:text-primary-base">s/{subthread?.name}</span>*/}
         {/*<span>{post?.author.username}</span>*/}


         {/* Post Card*/}
         <div className="w-2/3 h-full flex flex-col items-center justify-start py-3 px-3 text-background-text">


            {/* Post */}
            <div className="lg:w-2/3 max-w-screen-md relative">
               <IconButton icon={<FaArrowLeft/>} onClick={goBack} additionalStyles="absolute top-0 -left-10"/>

               {/*<Avatar size={14} url={""}/>*/}
               <span className="font-normal text-sm hover:text-primary-base cursor-pointer transition-all">s/{subthread?.name}</span>
               <span className="font-extralight text-xs ml-2">{formatDaysHours(post?.postedAt ?? new Date())}</span>
               <div className="text-sm">{post?.author.username}</div>

               <div className="font-bold text-2xl my-2">{post?.title}</div>
               <div>{post?.body}</div>


               <div className="flex flex-row items-stretch justify-start gap-5 mt-2.5 w-full">
                  <div
                     className={`${getVotingSectionBackground()} transition-all ease-linear px-1 py-1 rounded-lg flex flex-row items-center justify-start gap-2`}>
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
                     // onClick={viewPost}
                     className="cursor-pointer flex flex-row items-center justify-start gap-2 bg-common-gray px-2 py-1 rounded-lg hover:bg-primary-base transition-all ease-linear">
                     <IconButton
                        icon={<FaCommentAlt/>}
                        type={IconButtonType.EMPTY}
                        onClick={() => {
                        }}/>
                     <span>{post?.comments.length.toString()}</span>
                  </div>
               </div>

               <textarea
                  name="add-comment"
                  id=""
                  rows={1}
                  className="w-full bg-transparent border border-common-gray py-2 px-3 my-3 rounded-xl block focus:outline-none h-auto"
                  placeholder="Add a comment"
                  value={newCommentData.body}
                  onChange={e => setNewCommentData({...newCommentData, body: e.target.value})}
               />
               {newCommentData.body.length > 0 && <Button label="Add Comment" onClick={postComment} additionalStyles="my-3"/>}

               {post?.comments && post.comments.length > 0 ?
                  <div className="flex flex-col gap-5">
                     {
                        post.comments
                           .filter(comment => comment.parentId === null)
                           .map(
                              comment => (<CommentCard key={`0-${comment.id}`} indent={0} comment={comment} replies={getReplies(comment.id)}/>)
                           )
                     }
                  </div> :
                  <div>No comments yet.</div>
               }

            </div>

         </div>

         {/* Sub card  */}
         <div className=" w-1/3 h-full flex flex-col items-center justify-start text-background-text px-3 py-5">
            <span className="text-3xl font-bold">s/{subthread?.name}</span>
            <span className="text-xl">{subthread?.description}</span>
            <span>{subthread?.membersCount.toString()} enrolled</span>
            <Button label="Join" onClick={() => {
            }}/>
         </div>

      </div>
   )
}

type CommentCardProps = {
   comment: Comment,
   replies?: Comment[],
   indent: number,
}

function CommentCard({comment, replies, indent}: CommentCardProps) {
   const {data: profilePic} = useGetProfilePictureQuery(comment.author.username);

   const groupComments = (comments: Comment[]) => {
      const groupedComments: Record<number, Comment[]> = {}

      comments.forEach(comment => {
         if (comment.parentId === null)
            groupedComments[comment.id] = [];
      })

      comments.forEach(comment => {
         if (comment.parentId !== null) {
            if (!groupedComments[comment.parentId])
               groupedComments[comment.parentId] = []
            groupedComments[comment.parentId].push(comment);
         }
      })

      return groupedComments;
   }

   const getReplies = (id: number) => {
      return groupComments(replies ?? [])[id] ?? undefined;
   }

   return (<>
      <div className={`flex flex-row items-start justify-start gap-3 ${indent === 0 ? "" : "ml-8"}`} id={`${comment.id}-${indent}`}>
         {indent !== 0 && <div className="h-12 w-0.5 bg-common-gray"></div>}

         <div className="w-9 h-9 rounded-full bg-background-accent">
            <img className="w-full h-full object-cover rounded-xl shadow-2xl" src={`data:image/jpeg;base64,${profilePic?.payload}`} alt=""/>
         </div>

         <section>
            <div className="font-bold text-sm">
               <span className="font-bold text-sm">{comment.author.username}</span>
               <span className="font-extralight text-xs ml-2">{formatDaysHours(comment.postedAt ?? new Date())}</span>
            </div>
            <div className="mt-2 font-light">{comment.body}</div>
         </section>
      </div>

      <div>
         {replies && replies.map(reply => <CommentCard key={`reply-${indent}-${reply.id}`} indent={indent + 1} comment={reply}
                                                       replies={getReplies(reply.id)}/>)}
      </div>
   </>)
}