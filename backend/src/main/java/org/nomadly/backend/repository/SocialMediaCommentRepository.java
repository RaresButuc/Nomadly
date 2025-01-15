package org.nomadly.backend.repository;

import org.nomadly.backend.model.CommentClasses.SocialMediaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaCommentRepository extends JpaRepository<SocialMediaComment, Long> {
    List<SocialMediaComment> getAllByPostIdAndParentIsNullOrderByLikesDesc(Long id);
}
