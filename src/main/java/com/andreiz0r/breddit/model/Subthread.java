package com.andreiz0r.breddit.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@Table(name = "subthread")
@OnDelete(action = OnDeleteAction.CASCADE)
@NoArgsConstructor
@AllArgsConstructor
public class Subthread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false)
    private Integer id;

    private String name;

    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer membersCount;

    @OneToMany(targetEntity = Post.class, mappedBy = "subthreadId", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Post> posts;

    //TODO: picture + banner
}
