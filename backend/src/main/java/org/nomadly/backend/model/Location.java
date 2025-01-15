package org.nomadly.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;

import java.util.List;

@Data
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
    @OneToMany(mappedBy = "bornIn")
    private List<User> bornInUsers;

    @JsonIgnore
    @OneToMany(mappedBy = "currentlyIn")
    private List<User> currentlyInUsers;

    @JsonIgnore
    @ManyToMany(mappedBy = "locationPreferences")
    private List<User> usersVisited;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<SocialMediaPost> socialMediaPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "location")
    private List<QuestionPost> questionPosts;
}
