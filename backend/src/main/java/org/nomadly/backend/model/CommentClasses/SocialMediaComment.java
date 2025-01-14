package org.nomadly.backend.model.CommentClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.ParentCommData;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "social_media_comments")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SocialMediaComment extends Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private SocialMediaPost post;

    public SocialMediaComment(User user, ParentCommData parentCommData, String content, List<User> likers,
                              Long likes, boolean edited, LocalDateTime postTime, String stringPostTime,
                              SocialMediaPost post) {
        super(user, parentCommData, content, likers, likes, edited, postTime, stringPostTime);
        this.post = post;
    }
}
