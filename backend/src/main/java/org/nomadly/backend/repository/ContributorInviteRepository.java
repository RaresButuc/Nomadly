package org.nomadly.backend.repository;

import org.nomadly.backend.model.ContributorInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributorInviteRepository extends JpaRepository<ContributorInvite, Long> {

    ContributorInvite findByUuid(String uuid);

}
