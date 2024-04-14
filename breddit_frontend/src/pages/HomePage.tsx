import {useDispatch, useSelector} from "react-redux";
import {AuthState, endSession, selectAuthState} from "../redux/slices/authSlice.ts";
import {useGetModel} from "../redux/query/breddit-api.ts";
import {Comment, Post} from "../models/models.ts";
import {Endpoints} from "../utils/constants.ts";
import {CSSProperties} from "react";
import {useNavigate} from "react-router-dom";

export default function HomePage() {
    const authState: AuthState = useSelector(selectAuthState);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const posts = useGetModel<Post[]>(Endpoints.posts);

    const logout = () => {
        dispatch(endSession());
        navigate("/login");
    }

    return (
        <>
            <div>Hello, {authState.user?.username}</div>
            <div>Today's posts:</div>
            <button onClick={logout}>Log out</button>
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

    return (
        <div style={wrapperStyle} key={post.id}>
            <div style={titleStyle} key={post.title}>{post.title}</div>
            <div style={authorStyle} key={post.author.username}>by {post.author.username}</div>
            <div style={divider}></div>
            <div>{post.body}</div>
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