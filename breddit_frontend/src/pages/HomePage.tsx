import {useSelector} from "react-redux";
import {AuthState, selectAuthState} from "../redux/slices/authSlice.ts";
import {useGetModel, useGetPostPicturesQuery} from "../redux/query/breddit-api.ts";
import {BaseResponse, Comment, Post} from "../models/models.ts";
import {Endpoints} from "../utils/constants.ts";
import {CSSProperties} from "react";

export default function HomePage() {
    const authState: AuthState = useSelector(selectAuthState);
    const posts = useGetModel<Post[]>(Endpoints.posts);


    return (
        <>
            <div>Hello, {authState.user?.username}</div>
            <div>Today's posts:</div>
            {
                posts?.map((post: Post, index: number) => <PostCard post={post} key={index}/>)
            }
            {
                posts?.map((post: Post, index: number) => <PostCard post={post} key={index}/>)
            }
            {
                posts?.map((post: Post, index: number) => <PostCard post={post} key={index}/>)
            }
        </>
    )
}

type PostCardProps = {
    post: Post,
}

const PostCard = ({post}: PostCardProps) => {
    const wrapperStyle: CSSProperties = {
        margin: "10px",
        backgroundColor: "white",
        color: "black",
        padding: "15px",
        borderRadius: "14px",
    }

    const titleStyle: CSSProperties = {
        fontSize: "20px",
        marginBottom: "5px",
        textDecoration: "underline",
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

    const {data: postImages, error} = useGetPostPicturesQuery(post.id);

    if (error) {
        //TODO:
        const {message} = error as BaseResponse;
        console.log(message);
    }

    return (
        <div style={wrapperStyle} key={post.id}>
            <div style={titleStyle} key={post.title}>{post.title}</div>
            <div style={authorStyle} key={post.author.username}>by {post.author.username}</div>
            <div style={divider}></div>
            <div>{post.body}</div>

            {
                postImages?.payload.map((img, idx) => {
                    return <img className="rounded-xl" src={`data:image/jpeg;base64,${img}`} key={idx} alt="post img"/>
                })
            }


            <div>{`Votes: ${post.votes.toString()}`}</div>
            <div>Comments:</div>
            {
                post.comments.map((comment: Comment) => {
                    return <div key={comment.id} style={{marginLeft: "10px"}}>{comment.body} ---- {comment.author.username}</div>
                })
            }
        </div>
    );
}