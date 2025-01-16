package org.nomadly.backend.DTO;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.PhotosClasses.SocialMediaPhoto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class SocialMediaPhotoAndByteDTO {

    @NotNull
    @NotBlank
    private SocialMediaPhoto socialMediaPhoto;

    @NotNull
    @NotBlank
    private byte[] bytes;

}
