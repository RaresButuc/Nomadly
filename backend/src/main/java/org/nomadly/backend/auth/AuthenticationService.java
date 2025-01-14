package org.nomadly.backend.auth;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.nomadly.backend.enums.Role;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.UserRepository;
import org.nomadly.backend.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.findByEmailIgnoreCase(request.getEmail()) == null && userRepository.findByNameIgnoreCase(request.getName()) == null) {
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
            userRepository.save(user);

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
        var user = userRepository.findByEmailIgnoreCase(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

