package org.nomadly.backend.model.PhotosClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "social_meda_post_photos")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SocialMediaPhoto extends Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @ManyToOne
    @JsonIgnore
    private SocialMediaPost socialMediaPost;

    public SocialMediaPhoto(@NotNull @NotBlank String bucket, @NotNull @NotBlank String key, SocialMediaPost socialMediaPost) {
        super(bucket, key);
        this.socialMediaPost = socialMediaPost;
    }
}
