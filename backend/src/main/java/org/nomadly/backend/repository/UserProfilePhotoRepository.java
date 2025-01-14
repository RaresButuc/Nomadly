package org.nomadly.backend.repository;

import org.nomadly.backend.model.PhotosClasses.UserProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilePhotoRepository extends JpaRepository<UserProfilePhoto, Long> {
}
