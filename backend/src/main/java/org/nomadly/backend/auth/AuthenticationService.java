package org.nomadly.backend.auth;

import com.journalistjunction.model.User;
import com.journalistjunction.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.nomadly.backend.enums.Role;
import org.nomadly.backend.model.User;
import org.nomadly.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (repository.findByEmailIgnoreCase(request.getEmail()) == null && repository.findByNameIgnoreCase(request.getName()) == null) {
            var user = User.builder()
                    .role(Role.USER)
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phoneNumber(request.getPhoneNumber())
                    .bornIn(request.getBornIn())
                    .currentlyIn(request.getCurrentlyIn())
                    .shortAutoDescription(request.getShortAutoDescription())
                    .build();
            repository.save(user);

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } else {
            throw new EntityExistsException("User With The Same Email or UserName Already Exists!");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmailIgnoreCase(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

