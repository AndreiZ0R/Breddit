package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.model.Comment;
import com.andreiz0r.breddit.model.Message;
import com.andreiz0r.breddit.model.Post;
import com.andreiz0r.breddit.model.Subthread;
import com.andreiz0r.breddit.model.User;

//TODO: role
public class DTOMapper {

    public static UserDTO mapUserToDTO(final User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry(),
                user.getCreatedAt(),
                user.getBirthDate());
//                user.getUserRole());
    }

    public static CommentDTO mapCommentToDTO(final Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getAuthor(),
                comment.getBody(),
                comment.getPostedAt(),
                comment.getVotes(),
                comment.getPostId(),
                comment.getParentId()
        );
    }

    public static MessageDTO mapMessageToDTO(final Message message) {
        return new MessageDTO(
                message.getId(),
                message.getSender(),
                message.getReceiver(),
                message.getContent(),
                message.getSentAt()
        );
    }

    public static PostDTO mapPostToDTO(final Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getAuthor(),
                post.getPostedAt(),
                post.getComments(),
                post.getVotes(),
                post.getSubthreadId()
        );
    }

    public static SubthreadDTO mapSubthreadToDTO(final Subthread subthread) {
        return new SubthreadDTO(
                subthread.getId(),
                subthread.getName(),
                subthread.getDescription(),
                subthread.getMembersCount(),
                subthread.getPosts()
        );
    }
}
