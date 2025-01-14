package org.nomadly.backend.model.CommentClasses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.ParentCommData;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MappedSuperclass
public class Comment {

    @ManyToOne
    @JoinColumn
    private User user;

    @Embedded
    private ParentCommData parentCommData;

    @Column(length = 560)
    private String content;

    @ManyToMany
    private List<User> likers;

    @Column(name = "likes_count")
    private Long likes;

    private boolean edited;

    private LocalDateTime postTime;

    private String stringPostTime;

}
