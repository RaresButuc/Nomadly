package org.nomadly.backend.model.PhotosClasses;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Photo {

    @NotNull
    @NotBlank
    private String bucket;

    @NotNull
    @NotBlank
    private String key;

}
