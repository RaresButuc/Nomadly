package org.nomadly.backend.repository;

import org.nomadly.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findByEmailIgnoreCase(String email);

    User findByNameIgnoreCase(String Title);
}
