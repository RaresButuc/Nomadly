package org.nomadly.backend.model.PostClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nomadly.backend.model.Comment;
import org.nomadly.backend.model.Location;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Post {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime postTime;

    @ManyToOne
    @JoinColumn
    private Location location;

    @ManyToOne
    @JoinColumn
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    private Long commentsCount;

    public Post(String title, String body, LocalDateTime postTime, Location location, User owner, List<Comment> comments,
                Long commentsCount) {
        this.title = title;
        this.body = body;
        this.postTime = postTime;
        this.location = location;
        this.owner = owner;
        this.comments = comments;
        this.commentsCount = commentsCount;
    }
}