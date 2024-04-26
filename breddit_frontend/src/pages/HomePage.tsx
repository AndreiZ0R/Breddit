import {useSelector} from "react-redux";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {bredditApi, useGetModel, useGetPostPicturesQuery} from "../redux/query/breddit-api.ts";
import {BaseResponse, Comment, Post} from "../models/models.ts";
import {Endpoints, Queries} from "../utils/constants.ts";
import {CSSProperties, useContext, useEffect} from "react";
import ImageSlider from "../components/ImageSlider.tsx";
import {themeColors} from "../../tailwind.config.ts";
import {Client, Message} from "stompjs";
import {WsContext} from "../main.tsx";
import {toast, Toaster} from "react-hot-toast";

export default function HomePage() {
    const authState: AuthState = useSelector(selectAuthState);
    const posts = useGetModel<Post[]>(Endpoints.posts);
    const wsClient = useContext<Client>(WsContext);

    const subscribeToNotifications = () => {
        wsClient.subscribe("/topic/notifications", (message: Message) => {
            const res = JSON.parse(message.body).body;
            toast.success(`File ${res.payload} successfully uploaded!`);
            bredditApi.util?.invalidateTags([Queries.getPosts]);
        });
    }

    useEffect(() => {
        setTimeout(() => {
            subscribeToNotifications();
        }, 1000);

        console.log(wsClient);

        return () => {
        };
    }, [wsClient]);

    return (
        <>
            <div className="bg-background-base text-background-text">Hello, {authState.user?.username}</div>
            <div className="bg-background-base text-background-text px-32">Today's posts:
                {
                    posts?.map((post: Post, index: number) => <PostCard post={post} key={index}/>)
                }

                <Toaster/>
            </div>
        </>
    )
}

type PostCardProps = {
    post: Post,
}

const PostCard = ({post}: PostCardProps) => {
    const wrapperStyle: CSSProperties = {
        margin: "10px",
        color: "black",
        padding: "15px",
        borderRadius: "14px",
    }

    const titleStyle: CSSProperties = {
        fontSize: "20px",
        marginBottom: "5px",
        textDecoration: "underline",
        color: themeColors.background.text
    }

    const authorStyle: CSSProperties = {
        color: "grey",
        textDecoration: "none",
    }

    const divider: CSSProperties = {
        height: "1px",
        width: "100%",
        backgroundColor: "grey",
        marginTop: "10px",
        marginBottom: "25px",
    }

    //TODO: maybe show images only on detailed view?
    const {data: postImages, error} = useGetPostPicturesQuery(post.id);

    if (error) {
        //TODO:
        const {message} = error as BaseResponse;
        console.log(message);
    }

    return (
        <>
            <div style={wrapperStyle} key={post.id}
                 className="bg-background-accent/50 hover:bg-background-hover transition duration-300 ease-in-out hover:cursor-pointer">
                <div style={titleStyle} key={post.title}>{post.title}</div>
                <div style={authorStyle} key={post.author.username}>by {post.author.username}</div>
                <div style={divider}></div>
                <div className="text-background-text">{post.body}</div>


                {postImages && postImages.payload.length > 0 &&
                    <div className="w-1/2 m-auto flex items-center justify-center rounded-xl">
                        <ImageSlider images={postImages.payload}/>
                    </div>
                }


                <div className="text-background-text">{`Votes: ${post.votes.toString()}`}</div>
                <div className="text-background-text">Comments:</div>
                {
                    post.comments.map((comment: Comment) => {
                        return <div className="text-secondary-base" key={comment.id}
                                    style={{marginLeft: "10px"}}>{comment.body} ---- {comment.author.username}</div>
                    })
                }
            </div>
        </>
    );
}