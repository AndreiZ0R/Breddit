package com.andreiz0r.breddit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@Entity
@Table(name = "comment")
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private Timestamp postedAt;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer votes;

    @Column(nullable = false, unique = false)
    private Integer postId;

    @Column
    private Integer parentId;
}
