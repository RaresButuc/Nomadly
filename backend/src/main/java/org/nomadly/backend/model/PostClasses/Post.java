package org.nomadly.backend.model.PostClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.nomadly.backend.model.CommentClasses.Comment;
import org.nomadly.backend.model.Location;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Post {

    @Column(columnDefinition = "TEXT")
    private String body;

    private LocalDateTime postTime;

    @ManyToOne
    @JoinColumn
    private Location location;

    @ManyToOne
    @JoinColumn
    private User owner;

    private Long commentsCount;

}