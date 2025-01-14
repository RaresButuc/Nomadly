package org.nomadly.backend.model.PostClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.nomadly.backend.model.CommentClasses.Comment;
import org.nomadly.backend.model.CommentClasses.SocialMediaComment;
import org.nomadly.backend.model.Location;
import org.nomadly.backend.model.PhotosClasses.SocialMediaPhoto;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "social_media_posts")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SocialMediaPost extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @Size(max = 10)
    private List<SocialMediaPhoto> photos;

    @OneToMany(mappedBy = "post")
    private List<SocialMediaComment> comments;

    public SocialMediaPost(String body, LocalDateTime postTime, Location location, User owner,
                           List<SocialMediaPhoto> photos, List<SocialMediaComment> comments, Long commentsCount) {
        super(body, postTime, location, owner, commentsCount);
        this.comments = comments;
        this.photos = photos;
    }

}

