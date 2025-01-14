package org.nomadly.backend.model.PostClasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.nomadly.backend.model.*;

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

    private String title;

    @ManyToMany
    @JoinTable
    @Size(min = 1, max = 10)
    private List<Category> categories;

    @ManyToOne
    @JoinColumn
    private Language language;

    public QuestionPost(String body, LocalDateTime postTime, Location location, User owner, List<Comment> comments,
                        Long commentsCount, List<Category> categories, Language language) {
        super(body, postTime, location, owner, comments, commentsCount);
        this.categories = categories;
        this.language = language;
    }

}

