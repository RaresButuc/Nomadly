package org.nomadly.backend.model.PhotosClasses;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
