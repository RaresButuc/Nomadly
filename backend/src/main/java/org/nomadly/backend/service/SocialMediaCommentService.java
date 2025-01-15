package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.CommentClasses.Comment;
import org.nomadly.backend.model.CommentClasses.SocialMediaComment;
import org.nomadly.backend.model.ParentCommData;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.SocialMediaCommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocialMediaCommentService {

    private final SocialMediaCommentRepository socialMediaCommentRepository;

    public List<SocialMediaComment> getAllCommentsByArticle(Long articleId) {
        return socialMediaCommentRepository.getAllByPostIdAndParentIsNullOrderByLikesDesc(articleId);
    }

    public SocialMediaComment addNewComment(SocialMediaComment comment) {
        SocialMediaComment preparedComm = prepareCommentToPost(comment);

        return socialMediaCommentRepository.save(preparedComm);
    }

    public SocialMediaComment addChildComment(SocialMediaComment comment, Long parentCommId) {
        SocialMediaComment preparedComment = prepareCommentToPost(comment);
        SocialMediaComment parentComm = socialMediaCommentRepository.findById(parentCommId)
                .orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Respond To Doesn't Exist!"));
        SocialMediaComment mainComm = findMainByChildId(parentComm.getPost().getId(), parentComm.getId());

        preparedComment.setParent(parentComm);
        preparedComment.setParentCommData(new ParentCommData(parentCommId, parentComm.getUser().getName()));

        preparedComment.setMainComment(Objects.requireNonNullElse(mainComm, parentComm));

        socialMediaCommentRepository.save(parentComm);

        return socialMediaCommentRepository.save(preparedComment);
    }

    public void editComment(SocialMediaComment newComment, Long commentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        SocialMediaComment comm = socialMediaCommentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Edit To Doesn't Exist!"));

        if (newComment.getContent().isEmpty() || newComment.getContent().isBlank()) {
            throw new IllegalStateException("You Can't Post Empty Comments! Try Again!");
        }

        if (comm.getUser().getId().equals(user.getId())) {
            if (!comm.isEdited() && !comm.getContent().equals(newComment.getContent())) {
                comm.setEdited(true);
            }
            comm.setContent(newComment.getContent());

            socialMediaCommentRepository.save(comm);
        } else {
            throw new IllegalStateException("You Can't Edit This Comment!");
        }
    }

    public SocialMediaComment prepareCommentToPost(SocialMediaComment comment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (user == null) {
            throw new IllegalStateException("You Should Be Logged To Post Comments!");
        } else if (comment.getContent().isEmpty() || comment.getContent().isBlank()) {
            throw new IllegalStateException("You Can't Post Empty Comments! Try Again!");
        }

        LocalDateTime postTime = LocalDateTime.now();

        String hourAndSeconds = postTime.getHour() + ":" + postTime.getSecond();
        String dayAndMonth = postTime.getDayOfWeek().name() + ", " + postTime.getDayOfMonth() + " " + postTime.getMonth() + " " + postTime.getYear();

        comment.setLikes(0L);
        comment.setUser(user);
        comment.setEdited(false);
        comment.setPostTime(postTime);
        comment.setStringPostTime(hourAndSeconds + " / " + dayAndMonth);

        return comment;
    }

    public void likeOrUnlikeComment(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        SocialMediaComment comment = socialMediaCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Comment Was Found!"));
        boolean isLiked = isCommLiked(id);

        if (isLiked) {
            comment.setLikes(comment.getLikes() - 1);
            List<User> usersFiltered = comment.getLikers().stream().filter(e -> !Objects.equals(e.getId(), user.getId())).collect(Collectors.toList());

            comment.setLikers(usersFiltered);
        } else {
            List<User> newLikers = comment.getLikers();
            newLikers.add(user);

            comment.setLikes(comment.getLikes() + 1);
            comment.setLikers(newLikers);
        }

        socialMediaCommentRepository.save(comment);
    }

    public boolean isCommLiked(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        return socialMediaCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Comment Was Found!"))
                .getLikers().stream().anyMatch(e -> e.getId().equals(user.getId()));
    }

    public SocialMediaComment findParentByChildId(Long articleId, Long childId) {
        List<SocialMediaComment> articleComments = getAllCommentsByArticle(articleId);
        SocialMediaComment commParent = null;

        for (SocialMediaComment comment : articleComments) {
            List<SocialMediaComment> parentChildren = comment.getParentChildren();
            boolean isMainTheParent = parentChildren.stream()
                    .anyMatch(e -> Objects.equals(e.getId(), childId));
            if (isMainTheParent) {
                commParent = comment;
            } else {
                for (SocialMediaComment mainChildrenComm : comment.getParentChildren()) {
                    List<SocialMediaComment> children = mainChildrenComm.getParentChildren();
                    boolean isThisTheParent = children.stream().
                            anyMatch(e -> Objects.equals(e.getId(), childId));

                    commParent = isThisTheParent ? mainChildrenComm : null;
                }
            }
        }
        return commParent;
    }

    public SocialMediaComment findMainByChildId(Long articleId, Long childId) {
        List<SocialMediaComment> articleComments = getAllCommentsByArticle(articleId);
        SocialMediaComment commMain = null;

        for (SocialMediaComment comment : articleComments) {
            List<SocialMediaComment> mainChildren = comment.getMainChildren();
            boolean isThisMain = mainChildren.stream()
                    .anyMatch(e -> Objects.equals(e.getId(), childId));
            if (isThisMain) {
                commMain = comment;
            } else {
                for (SocialMediaComment mainChildrenComm : comment.getMainChildren()) {
                    List<SocialMediaComment> children = mainChildrenComm.getMainChildren();
                    boolean isThisTheMain = children.stream().
                            anyMatch(e -> Objects.equals(e.getId(), childId));

                    commMain = isThisTheMain ? mainChildrenComm : null;
                }
            }
        }
        return commMain;
    }

    public void deleteCommentById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        SocialMediaComment comm = socialMediaCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Delete Doesn't Exists!"));
        SocialMediaComment parentComm = findParentByChildId(comm.getPost().getId(), comm.getId());

        if (Objects.equals(comm.getUser().getId(), user.getId()) ||
                Objects.equals(comm.getPost().getOwner().getId(), user.getId())) {
            if (parentComm != null) {
                socialMediaCommentRepository.save(parentComm);
            }

            socialMediaCommentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("You Can't Delete This Comment!");
        }
    }
}
