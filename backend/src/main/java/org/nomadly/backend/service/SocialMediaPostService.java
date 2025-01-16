package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.DTO.SocialMediaPhotoAndByteDTO;
import org.nomadly.backend.DTO.SocialMediaPostAndThumbnailDTO;
import org.nomadly.backend.model.PhotosClasses.SocialMediaPhoto;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.SocialMediaPostPhotoRepository;
import org.nomadly.backend.repository.SocialMediaPostRepository;
import org.nomadly.backend.s3.S3Buckets;
import org.nomadly.backend.s3.S3Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocialMediaPostService {

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final SocialMediaPostRepository socialMediaPostRepository;
    private final SocialMediaPostPhotoRepository socialMediaPostPhotoRepository;

    public List<SocialMediaPost> getAllPosts() {
        return socialMediaPostRepository.findAll();
    }

//    public List<SocialMediaPost> getAllPostsByInputAndCountry(String input, String country) {
//
//        PageRequest pageRequest = PageRequest.of(currentPage, itemsPerPage);
//        List<SocialMediaPost> articles;
//
//        if (category.equals("null")) {
//            if (country.equals("null")) {
//                if (input.equals("null")) {
//                    if (language.equals("null")) {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueOrderByPostTimeDesc();
//                    } else {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(language);
//                    }
//                } else {
//                    if (language.equals("null")) {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndTitleContainingIgnoreCaseOrderByPostTimeDesc(input);
//                    } else {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndAndTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(input, language);
//                    }
//                }
//            } else {
//                if (input.equals("null")) {
//                    if (language.equals("null")) {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndLocation_CountryOrderByPostTimeDesc(country);
//                    } else {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndLocation_CountryAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(country, language);
//                    }
//                } else {
//                    if (language.equals("null")) {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndLocation_CountryAndTitleContainingIgnoreCaseOrderByPostTimeDesc(country, input);
//                    } else {
//                        articles = socialMediaPostRepository.findAllByPublishedIsTrueAndLocation_CountryAndTitleContainingIgnoreCaseAndLanguage_LanguageNameEnglishOrderByPostTimeDesc(country, input, language);
//                    }
//                }
//            }
//        } else {
//            if (country.equals("null")) {
//                if (input.equals("null")) {
//                    if (language.equals("null")) {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .collect(Collectors.toList());
//                    } else {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLanguage().getLanguageNameEnglish().equals(language))
//                                .collect(Collectors.toList());
//                    }
//                } else {
//                    if (language.equals("null")) {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getTitle().toLowerCase().contains(input.toLowerCase()))
//                                .collect(Collectors.toList());
//                    } else {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLanguage().getLanguageNameEnglish().equals(language) && e.getTitle().toLowerCase().contains(input.toLowerCase()))
//                                .collect(Collectors.toList());
//                    }
//                }
//            } else {
//                if (input.equals("null")) {
//                    if (language.equals("null")) {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLocation().getCountry().equals(country))
//                                .collect(Collectors.toList());
//                    } else {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getLanguage().getLanguageNameEnglish().equals(language))
//                                .collect(Collectors.toList());
//                    }
//                } else {
//                    if (language.equals("null")) {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getTitle().toLowerCase().contains(input.toLowerCase()))
//                                .collect(Collectors.toList());
//                    } else {
//                        articles = getArticlesByIsPublishedAndCategory(socialMediaPostRepository.findAll(), category)
//                                .filter(e -> e.getLocation().getCountry().equals(country) && e.getTitle().toLowerCase().contains(input.toLowerCase()) && e.getLanguage().getLanguageNameEnglish().equals(language))
//                                .collect(Collectors.toList());
//                    }
//                }
//            }
//        }
//
//        List<ArticleAndThumbnailDTO> sublist = articles.subList(
//                        (int) pageRequest.getOffset(),
//                        Math.min((int) pageRequest.getOffset() + pageRequest.getPageSize(), articles.size())
//                )
//                .stream()
//                .map(e -> new ArticleAndThumbnailDTO(e, getArticleThumbnail(e.getId()).getBytes())).toList();
//
//        return new PageImpl<>(sublist, pageRequest, articles.size());
//    }

//    public List<SocialMediaPostAndThumbnailDTO> getTrendingPosts(String category, Long time) {
//        List<SocialMediaPostAndThumbnailDTO> allArticles;
//
//        if (time == 1 || time == 7 || time == 30) {
//            if (category == null || category.equals("null")) {
//                allArticles = getAllPosts().stream()
//                        .filter(e -> e.getPostTime().until(LocalDateTime.now(), ChronoUnit.DAYS) < time + 1)
//                        .sorted((a, b) -> b.getViews().compareTo(a.getViews()))
//                        .map(e -> new SocialMediaPostAndThumbnailDTO(e, getArticleThumbnail(e.getId()).getBytes()))
//                        .toList();
//            } else {
//                allArticles = getArticlesByIsPublishedAndCategory(articleRepository.findAll(), category)
//                        .filter(e -> e.getPostTime().until(LocalDateTime.now(), ChronoUnit.DAYS) < time + 1)
//                        .sorted((a, b) -> b.getViews().compareTo(a.getViews()))
//                        .map(e -> new ArticleAndThumbnailDTO(e, getArticleThumbnail(e.getId()).getBytes()))
//                        .toList();
//            }
//        } else {
//            throw new IllegalStateException("An Error Has Occurred! Please Try Again!");
//        }
//        return allArticles;
//    }

    public SocialMediaPost getSocialMediaPostById(Long articleId) {
        return socialMediaPostRepository.findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("No Article Found!"));
    }

    public List<SocialMediaPostAndThumbnailDTO> getSocialMediaPostsByOwnerId(Long userId) {
        return socialMediaPostRepository.findAllByOwnerIdOrderByPostTimeDesc(userId)
                .stream()
                .map(e -> new SocialMediaPostAndThumbnailDTO(e, getSocialMediaPostsByOwnerId(e.getId()) != null ?
                        e.getPhotos().stream().limit(2).map(i -> s3Service.getObject(s3Buckets.getCustomer(), i.getKey())).toList() : null))
                .toList();
    }

    public SocialMediaPost createArticle(SocialMediaPost post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        if (Objects.equals(user.getId(), post.getOwner().getId())) {
            post.setPostTime(LocalDateTime.now());
            return socialMediaPostRepository.save(post);
        }
        throw new IllegalStateException("An Error Has Occurred! Please Try Again!");
    }

    public SocialMediaPost updateArticleById(Long postId, SocialMediaPost postUpdater) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        SocialMediaPost post = getSocialMediaPostById(postId);

        if (Objects.equals(post.getOwner().getId(), user.getId())) {
            copyArticleFields(postUpdater, post);

            return socialMediaPostRepository.save(post);
        }
        throw new IllegalStateException("You Can't Update An Article If You Are Not The Owner or A Contributor Of It!");
    }

    public List<SocialMediaPhotoAndByteDTO> getPostPhotos(Long postId) {
        SocialMediaPost article = getSocialMediaPostById(postId);

        List<SocialMediaPhotoAndByteDTO> photos = new ArrayList<>();
        for (SocialMediaPhoto articlePhoto : article.getPhotos()) {
            byte[] photo = s3Service.getObject(s3Buckets.getCustomer(), articlePhoto.getKey());
            photos.add(new SocialMediaPhotoAndByteDTO(articlePhoto, photo));
        }

        return photos;
    }

    @Transactional
    public void uploadPostPhotos(List<MultipartFile> files, Long postId) throws IOException {
        if (files.isEmpty()) {
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        SocialMediaPost post = getSocialMediaPostById(postId);

        if (!Objects.equals(post.getOwner().getId(), user.getId())) {
            throw new IllegalStateException("You Can't Update A Post If You Are Not The Owner Of It!");
        }

        if (post.getPhotos().size() + files.size() <= 10) {

            for (MultipartFile file : files) {
                String uuid = UUID.randomUUID().toString();
                String key = user.getId() + "/Article_" + postId + "/" + uuid;

                SocialMediaPhoto newArticlePhoto = socialMediaPostPhotoRepository.save(new SocialMediaPhoto(s3Buckets.getCustomer(), key, post));

                post.getPhotos().add(newArticlePhoto);

                s3Service.putObject(s3Buckets.getCustomer(), key, file.getBytes());
            }

            socialMediaPostRepository.save(post);
        } else {
            throw new IllegalStateException("Articles Can't Have More Than 10 Photos!");
        }
    }

    public void deleteArticlePhotos(List<SocialMediaPhoto> photos, Long postId) {
        if (photos.isEmpty()) {
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        SocialMediaPost article = getSocialMediaPostById(postId);

        if (!Objects.equals(article.getOwner().getId(), user.getId())) {
            throw new IllegalStateException("You Can't Update An Article If You Are Not The Owner Of It!");
        }

        List<String> keys = photos.stream().map(SocialMediaPhoto::getKey).toList();

        List<SocialMediaPhoto> updatedPhotos = article.getPhotos().stream()
                .filter(photoInArticle -> photos.stream().noneMatch(photoToDelete -> photoInArticle.getId().equals(photoToDelete.getId())))
                .collect(Collectors.toList());

        article.setPhotos(updatedPhotos);

        socialMediaPostRepository.save(article);

        socialMediaPostPhotoRepository.deleteAll(photos);

        s3Service.deleteMultipleObjects(s3Buckets.getCustomer(), keys);
    }

    private void copyArticleFields(SocialMediaPost source, SocialMediaPost destination) {
        destination.setBody(source.getBody());
    }

    private void validatePostFields(SocialMediaPost post) {
        if (
                post.getBody() == null ||
                        post.getBody().isEmpty() && post.getPhotos().isEmpty()) {
            throw new IllegalStateException("All The Necessary Fields Must Be Completed! They Are Marked With ` * `");
        }
    }

    public boolean isUserOwner(Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();
        SocialMediaPost article = getSocialMediaPostById(postId);

        return Objects.equals(article.getOwner().getId(), user.getId());
    }

//    public void increaseArticleViewCount(Long articleId) {
//        Article article = getArticleById(articleId);
//
//        article.setViews(article.getViews() + 1);
//
//        articleRepository.save(article);
//    }

    public String localDateTimeToString(Long articleId) {
        LocalDateTime articlePostTime = getSocialMediaPostById(articleId).getPostTime();

        String hourAndSeconds = articlePostTime.getHour() + ":" + articlePostTime.getSecond();
        String dayAndMonth = articlePostTime.getDayOfWeek().name() + ", " + articlePostTime.getDayOfMonth() + " " + articlePostTime.getMonth() + " " + articlePostTime.getYear();
        return hourAndSeconds + " / " + dayAndMonth;
    }

    public Long deleteArticleById(Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) auth.getPrincipal();

        if (Objects.equals(getSocialMediaPostById(postId).getOwner().getId(), user.getId())) {
            deleteArticlePhotos(getSocialMediaPostById(postId).getPhotos(), postId);

            socialMediaPostRepository.deleteById(postId);

            return user.getId();
        } else {
            throw new RuntimeException("An Error Occurred While Trying To Delete This Article! Please Try Again Later!");
        }
    }
}
