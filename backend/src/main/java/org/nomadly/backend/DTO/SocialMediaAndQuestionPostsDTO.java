package org.nomadly.backend.DTO;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.PhotosClasses.SocialMediaPhoto;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class SocialMediaAndQuestionPostsDTO {

    @NotNull
    @NotBlank
    private List<SocialMediaPost> socialMediaPosts;

    @NotNull
    @NotBlank
    private List<QuestionPost> questionPosts;

}
