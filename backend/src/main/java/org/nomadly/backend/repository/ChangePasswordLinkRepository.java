package org.nomadly.backend.repository;

import org.nomadly.backend.model.ChangePasswordLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangePasswordLinkRepository extends JpaRepository<ChangePasswordLink, Long> {
    ChangePasswordLink findByUuid(String uuid);
}
