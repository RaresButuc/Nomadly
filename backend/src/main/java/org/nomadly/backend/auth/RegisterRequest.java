package org.nomadly.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.enums.Role;
import org.nomadly.backend.model.Location;

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
