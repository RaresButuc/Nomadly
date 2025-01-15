package org.nomadly.backend.repository;

import org.nomadly.backend.model.CommentClasses.QuestionPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionPostCommentRepository extends JpaRepository<QuestionPostComment, Long> {
    List<QuestionPostComment> getAllByPostIdAndParentIsNullOrderByLikesDesc(Long id);
}
