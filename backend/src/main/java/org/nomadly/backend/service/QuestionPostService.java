package org.nomadly.backend.service;


import lombok.AllArgsConstructor;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.QuestionPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class QuestionPostService {

    private final QuestionPostRepository questionPostRepository;

    public List<QuestionPost> getAllQuestionPosts() {
        return questionPostRepository.findAll();
    }

    public Page<QuestionPost> getAllPostsByInputAndCategory(String input, String category, String country, String language, int currentPage, int itemsPerPage) {

        PageRequest pageRequest = PageRequest.of(currentPage, itemsPerPage);
        List<QuestionPost> posts;

        if (category.equals("null")) {
            if (country.equals("null")) {
                if (input.equals("null")) {
                    if (language.equals("null")) {
                        posts = questionPostRepository.findAllByOrderByPostTimeDesc();
                    } else {
                        posts = questionPostRepository.findAllByLanguage_LanguageNameEnglishOrderByPostTimeDesc(language);
                    }
                } else {
                    if (language.equals("null")) {
                        posts = questionPostRepository.findAllByTitleContainingIgnoreCaseOrderByPostTimeDesc(input);
                    } else {
                        posts = questionPostRepository.findAllByTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(input, language);
                    }
                }
            } else {
                if (input.equals("null")) {
                    if (language.equals("null")) {
                        posts = questionPostRepository.findAllByLocation_CountryOrderByPostTimeDesc(country);
                    } else {
                        posts = questionPostRepository.findAllByLocation_CountryAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(country, language);
                    }
                } else {
                    if (language.equals("null")) {
                        posts = questionPostRepository.findAllByLocation_CountryAndTitleContainingIgnoreCaseOrderByPostTimeDesc(country, input);
                    } else {
                        posts = questionPostRepository.findAllByLocation_CountryAndTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(country, input, language);
                    }
                }
            }
        } else {
            if (country.equals("null")) {
                if (input.equals("null")) {
                    if (language.equals("null")) {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .collect(Collectors.toList());
                    } else {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLanguage().getLanguageNameEnglish().equals(language))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (language.equals("null")) {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getTitle().toLowerCase().contains(input.toLowerCase()))
                                .collect(Collectors.toList());
                    } else {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLanguage().getLanguageNameEnglish().equals(language) && e.getTitle().toLowerCase().contains(input.toLowerCase()))
                                .collect(Collectors.toList());
                    }
                }
            } else {
                if (input.equals("null")) {
                    if (language.equals("null")) {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLocation().getCountry().equals(country))
                                .collect(Collectors.toList());
                    } else {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getLanguage().getLanguageNameEnglish().equals(language))
                                .collect(Collectors.toList());
                    }
                } else {
                    if (language.equals("null")) {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getTitle().toLowerCase().contains(input.toLowerCase()))
                                .collect(Collectors.toList());
                    } else {
                        posts = getPostsByCategory(questionPostRepository.findAll(), category)
                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getTitle().toLowerCase().contains(input.toLowerCase()) && e.getLanguage().getLanguageNameEnglish().equals(language))
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        List<QuestionPost> sublist = posts.subList(
                (int) pageRequest.getOffset(),
                Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize(), posts.size())
        );

        return new PageImpl<>(sublist, pageRequest, posts.size());
    }

    public Stream<QuestionPost> getPostsByCategory(List<QuestionPost> posts, String category) {
        return posts
                .stream()
                .filter(e -> e.getCategories().stream().anyMatch(i -> i.getNameOfCategory().equals(category)))
                .sorted(Comparator.comparing(QuestionPost::getPostTime).reversed());
    }

    public QuestionPost getQuestionPostById(Long articleId) {
        return questionPostRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("No Article Found!"));
    }

    public List<QuestionPost> getQuestionPostsByOwnerId(Long userId) {
        return questionPostRepository.findAllByOwnerIdOrderByPostTimeDesc(userId).stream().toList();
    }

    public QuestionPost createPost(QuestionPost post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        if (Objects.equals(user.getId(), post.getOwner().getId())) {
            post.setPostTime(LocalDateTime.now());
            return questionPostRepository.save(post);
        }
        throw new IllegalStateException("An Error Has Occurred! Please Try Again!");
    }

    public QuestionPost updatePostById(Long articleId, QuestionPost postUpdater) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        QuestionPost post = getQuestionPostById(articleId);

        if (Objects.equals(post.getOwner().getId(), user.getId())) {
            validateArticleFields(postUpdater);
            copyArticleFields(postUpdater, post);

            return questionPostRepository.save(post);
        }
        throw new IllegalStateException("You Can't Update An Post If You Are Not The Owner Of It!");
    }

    private void copyArticleFields(QuestionPost source, QuestionPost destination) {
        destination.setTitle(source.getTitle());
        destination.setBody(source.getBody());
        destination.setCategories(source.getCategories());
        destination.setLocation(source.getLocation());
        destination.setLanguage(source.getLanguage());
    }

    private void validateArticleFields(QuestionPost article) {
        if (article.getTitle() == null ||
                article.getTitle().isEmpty() ||
                article.getBody() == null ||
                article.getBody().isEmpty() ||
                article.getCategories() == null ||
                article.getCategories().isEmpty() ||
                article.getLocation() == null ||
                article.getLanguage() == null) {
            throw new IllegalStateException("All The Fields Must Be Completed!");
        }
    }

    public boolean isUserOwner(Long articleId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        QuestionPost article = getQuestionPostById(articleId);

        return Objects.equals(article.getOwner().getId(), user.getId());
    }

    public String localDateTimeToString(Long articleId) {
        LocalDateTime articlePostTime = getQuestionPostById(articleId).getPostTime();

        String hourAndSeconds = articlePostTime.getHour() + ":" + articlePostTime.getSecond();
        String dayAndMonth = articlePostTime.getDayOfWeek().name() + ", " + articlePostTime.getDayOfMonth() + " " + articlePostTime.getMonth() + " " + articlePostTime.getYear();
        return hourAndSeconds + " / " + dayAndMonth;
    }

    public void deleteArticleById(Long articleId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        if (Objects.equals(getQuestionPostById(articleId).getOwner().getId(), user.getId())) {
            questionPostRepository.deleteById(articleId);
        } else {
            throw new RuntimeException("An Error Occurred While Trying To Delete This Post! Please Try Again Later!");
        }
    }
}
