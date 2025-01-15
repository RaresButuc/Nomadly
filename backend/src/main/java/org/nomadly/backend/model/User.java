package org.nomadly.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.enums.Role;
import org.nomadly.backend.model.CommentClasses.QuestionPostComment;
import org.nomadly.backend.model.CommentClasses.SocialMediaComment;
import org.nomadly.backend.model.PhotosClasses.UserProfilePhoto;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    @Column(length = 10)
    private String phoneNumber;

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn
    private Location bornIn;

    @NotNull
    @NotBlank
    @ManyToMany
    @JoinColumn
    private List<Location> visitedLocations;

    private boolean visitedLocationsVisibility;

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn
    private Location currentlyIn;

    @NotNull
    @NotBlank
    @Column(length = 500)
    private String shortAutoDescription;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private UserProfilePhoto profilePhoto;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<SocialMediaPost> socialMediaPosts;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<QuestionPost> questionPosts;

    @ManyToMany
    @JsonManagedReference
    private List<User> friends;

    @ManyToMany(mappedBy = "friends")
    @JsonBackReference
    private List<User> friendOf;

    @ManyToMany(mappedBy = "subscribedUsers")
    private List<Location> preferences;

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER)
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SocialMediaComment> socialMediaComments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<QuestionPostComment> questionPostComments;

    @JsonIgnore
    @ManyToMany
    private List<SocialMediaComment> likedSocialMediaComments;

    @JsonIgnore
    @ManyToMany
    private List<QuestionPostComment> likedQuestionPostComments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
