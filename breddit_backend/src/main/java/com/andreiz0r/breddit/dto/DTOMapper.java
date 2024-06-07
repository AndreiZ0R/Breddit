package com.andreiz0r.breddit.dto;

import com.andreiz0r.breddit.entity.ChatMessage;
import com.andreiz0r.breddit.entity.Comment;
import com.andreiz0r.breddit.entity.Post;
import com.andreiz0r.breddit.entity.Subthread;
import com.andreiz0r.breddit.entity.User;
import com.andreiz0r.breddit.entity.UserSession;
import com.andreiz0r.breddit.exception.ApiException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class DTOMapper {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T, U> void updateValues(T value, U newValue) {
        try {
            objectMapper.updateValue(value, newValue);
        } catch (JsonMappingException e) {
            throw new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public static UserDTO mapUserToDTO(final User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCountry(),
                user.getCreatedAt(),
                user.getBirthDate(),
                user.getRole());
    }

    public static CommentDTO mapCommentToDTO(final Comment comment) {
        return new CommentDTO(
                comment.getId(),
                mapUserToDTO(comment.getAuthor()),
                comment.getBody(),
                comment.getPostedAt(),
                comment.getVotes(),
                comment.getPostId(),
                comment.getParentId()
        );
    }

    public static MessageDTO mapMessageToDTO(final ChatMessage chatMessage) {
        return new MessageDTO(
                chatMessage.getId(),
                mapUserToDTO(chatMessage.getSender()),
                mapUserToDTO(chatMessage.getReceiver()),
                chatMessage.getContent(),
                chatMessage.getSentAt()
        );
    }

    public static PostDTO mapPostToDTO(final Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                mapUserToDTO(post.getAuthor()),
                post.getPostedAt(),
                post.getComments().stream().map(DTOMapper::mapCommentToDTO).toList(),
                post.getVotes(),
                post.getSubthreadId(),
                post.getImagesUrl()
        );
    }

    public static ViewPostDTO mapPostToDTO(final Post post, final String subthreadName) {
        return new ViewPostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                mapUserToDTO(post.getAuthor()),
                post.getPostedAt(),
                post.getComments().stream().map(DTOMapper::mapCommentToDTO).toList(),
                post.getVotes(),
                post.getSubthreadId(),
                subthreadName,
                post.getImagesUrl()
        );
    }

    public static SubthreadDTO mapSubthreadToDTO(final Subthread subthread) {
        return new SubthreadDTO(
                subthread.getId(),
                subthread.getName(),
                subthread.getDescription(),
                subthread.getMembersCount(),
                subthread.getPosts().stream().map(DTOMapper::mapPostToDTO).toList()
        );
    }

    public static UserSessionDTO mapUserSessionToDTO(final UserSession userSession) {
        return new UserSessionDTO(
                userSession.getSessionId(),
                mapUserToDTO(userSession.getUser())
        );
    }
}
