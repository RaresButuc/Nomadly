package org.nomadly.backend.repository;

import org.nomadly.backend.model.PhotosClasses.SocialMediaPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialMediaPostPhotoRepository extends JpaRepository<SocialMediaPhoto, Long> {
}
