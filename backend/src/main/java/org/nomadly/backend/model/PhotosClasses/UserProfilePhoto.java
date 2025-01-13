package org.nomadly.backend.model.PhotosClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_profile_photos")
public class UserProfilePhoto extends Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private User user;

    public UserProfilePhoto(@NotNull @NotBlank String bucket, @NotNull @NotBlank String key, User user) {
        super(bucket, key);
        this.user = user;
    }
}
