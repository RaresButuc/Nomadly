package org.nomadly.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String country;

    @NotNull
    @NotBlank
    private String cca2;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<SocialMediaPost> socialMediaPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<QuestionPost> questionPosts;
}
