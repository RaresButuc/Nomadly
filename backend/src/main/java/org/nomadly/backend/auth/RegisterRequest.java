package org.nomadly.backend.auth;

import com.journalistjunction.enums.Role;
import com.journalistjunction.model.Location;
import com.journalistjunction.model.SocialMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String name;

    private String phoneNumber;

    private Role role;

    private String email;

    private String password;

    private Location bornIn;

    private Location currentlyIn;

    private String shortAutoDescription;

}
