package org.nomadly.backend.model.PostClasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.nomadly.backend.model.*;
import org.nomadly.backend.model.PhotosClasses.QuestionPostPhoto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question_posts")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class QuestionPost extends Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable
    @Size(min = 1, max = 10)
    private List<Category> categories;

    @ManyToOne
    @JoinColumn
    private Language language;

    @OneToMany
    @Size(max = 10)
    private List<QuestionPostPhoto> photos;

    public QuestionPost(String title, String body, LocalDateTime postTime, Location location, User owner,
                        List<QuestionPostPhoto> photos, List<Comment> comments, Long commentsCount,
                        List<Category> categories, Language language) {
        super(title, body, postTime, location, owner, photos, comments, commentsCount);
        this.categories = categories;
        this.language = language;
    }

}

