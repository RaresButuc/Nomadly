package org.nomadly.backend.model.PhotosClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.model.PostClasses.QuestionPost;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question_post_photos")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class QuestionPostPhoto extends Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private boolean isThumbnail;

    @NotNull
    @NotBlank
    @ManyToOne
    @JsonIgnore
    private QuestionPost questionPost;

    public QuestionPostPhoto(@NotNull @NotBlank String bucket, @NotNull @NotBlank String key, boolean isThumbnail,
                             QuestionPost questionPost) {
        super(bucket, key);
        this.isThumbnail = isThumbnail;
        this.questionPost = questionPost;
    }
}
