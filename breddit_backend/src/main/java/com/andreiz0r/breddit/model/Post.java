package com.andreiz0r.breddit.model;

import com.andreiz0r.breddit.utils.StringListConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "post")
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    //TODO: upgrade to Body class, also permitting pictures
    @Column(nullable = false)
    private String body;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private Timestamp postedAt;

    @OneToMany(targetEntity = Comment.class, mappedBy = "postId", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer votes;

    @Column(nullable = false)
    private Integer subthreadId;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "varchar default null")
    private List<String> imagesUrl;
}
