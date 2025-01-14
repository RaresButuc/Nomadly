package org.nomadly.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.enums.Role;
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

    @JsonIgnore
    @OneToMany
    private List<User> friends;

    @OneToMany(mappedBy = "to", fetch = FetchType.EAGER)
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany
    private List<Comment> likedComments;

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
