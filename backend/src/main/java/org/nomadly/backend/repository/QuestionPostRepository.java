package org.nomadly.backend.repository;

import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionPostRepository extends JpaRepository<QuestionPost, Long> {

    List<QuestionPost> findAllByOwnerIdOrderByPostTimeDesc(Long id);

    List<QuestionPost> findAllByLocation_CountryOrderByPostTimeDesc(String country);

    List<QuestionPost> findAllByLocation_CountryAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(String country, String language);

    List<QuestionPost> findAllByTitleContainingIgnoreCaseOrderByPostTimeDesc(String input);

    List<QuestionPost> findAllByLanguage_LanguageNameEnglishOrderByPostTimeDesc(String language);

    List<QuestionPost> findAllByTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(String input, String language);

    List<QuestionPost> findAllByLocation_CountryAndTitleContainingIgnoreCaseOrderByPostTimeDesc(String country, String input);

    List<QuestionPost> findAllByLocation_CountryAndTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(String country, String input, String language);

    List<QuestionPost> findAllByOrderByPostTimeDesc();
}
