package org.nomadly.backend.service;

import lombok.AllArgsConstructor;
import org.nomadly.backend.model.CommentClasses.Comment;
import org.nomadly.backend.model.CommentClasses.QuestionPostComment;
import org.nomadly.backend.model.ParentCommData;
import org.nomadly.backend.model.User;
import org.nomadly.backend.repository.QuestionPostCommentRepository;
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
public class QuestionPostCommentService {

    private final QuestionPostCommentRepository questionPostCommentRepository;

    public List<QuestionPostComment> getAllCommentsByPost(Long postId) {
        return questionPostCommentRepository.getAllByPostIdAndParentIsNullOrderByLikesDesc(postId);
    }

    public QuestionPostComment addNewComment(QuestionPostComment comment) {
        QuestionPostComment preparedComm = prepareCommentToPost(comment);

        return questionPostCommentRepository.save(preparedComm);
    }

    public QuestionPostComment addChildComment(QuestionPostComment comment, Long parentCommId) {
        QuestionPostComment preparedComment = prepareCommentToPost(comment);
        QuestionPostComment parentComm = questionPostCommentRepository.findById(parentCommId).orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Respond To Doesn't Exist!"));
        QuestionPostComment mainComm = findMainByChildId(parentComm.getPost().getId(), parentComm.getId());

        preparedComment.setParent(parentComm);
        preparedComment.setParentCommData(new ParentCommData(parentCommId, parentComm.getUser().getName()));

        preparedComment.setMainComment(Objects.requireNonNullElse(mainComm, parentComm));

        questionPostCommentRepository.save(parentComm);

        return questionPostCommentRepository.save(preparedComment);
    }

    public void editComment(QuestionPostComment newComment, Long commentId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        QuestionPostComment comm = questionPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Edit To Doesn't Exist!"));

        if (newComment.getContent().isEmpty() || newComment.getContent().isBlank()) {
            throw new IllegalStateException("You Can't Post Empty Comments! Try Again!");
        }

        if (comm.getUser().getId().equals(user.getId())) {
            if (!comm.isEdited() && !comm.getContent().equals(newComment.getContent())) {
                comm.setEdited(true);
            }
            comm.setContent(newComment.getContent());

            questionPostCommentRepository.save(comm);
        } else {
            throw new IllegalStateException("You Can't Edit This Comment!");
        }
    }

    public QuestionPostComment prepareCommentToPost(QuestionPostComment comment) {
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

        QuestionPostComment comment = questionPostCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Comment Was Found!"));

        if (isCommLiked(id)) {
            comment.setLikes(comment.getLikes() - 1);
            List<User> usersFiltered = comment.getLikers().stream().filter(e -> !Objects.equals(e.getId(), user.getId())).collect(Collectors.toList());

            comment.setLikers(usersFiltered);
        } else {
            List<User> newLikers = comment.getLikers();
            newLikers.add(user);

            comment.setLikes(comment.getLikes() + 1);
            comment.setLikers(newLikers);
        }

        questionPostCommentRepository.save(comment);
    }

    public boolean isCommLiked(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        return questionPostCommentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Comment Was Found!"))
                .getLikers().stream().anyMatch(e -> e.getId().equals(user.getId()));
    }

    public QuestionPostComment findParentByChildId(Long articleId, Long childId) {
        List<QuestionPostComment> postComments = getAllCommentsByPost(articleId);
        QuestionPostComment commParent = null;

        for (QuestionPostComment comment : postComments) {
            List<QuestionPostComment> parentChildren = comment.getParentChildren();
            boolean isMainTheParent = parentChildren.stream()
                    .anyMatch(e -> Objects.equals(e.getId(), childId));
            if (isMainTheParent) {
                commParent = comment;
            } else {
                for (QuestionPostComment mainChildrenComm : comment.getParentChildren()) {
                    List<QuestionPostComment> children = mainChildrenComm.getParentChildren();
                    boolean isThisTheParent = children.stream().
                            anyMatch(e -> Objects.equals(e.getId(), childId));

                    commParent = isThisTheParent ? mainChildrenComm : null;
                }
            }
        }
        return commParent;
    }

    public QuestionPostComment findMainByChildId(Long articleId, Long childId) {
        List<QuestionPostComment> articleComments = getAllCommentsByPost(articleId);
        QuestionPostComment commMain = null;

        for (QuestionPostComment comment : articleComments) {
            List<QuestionPostComment> mainChildren = comment.getMainChildren();
            boolean isThisMain = mainChildren.stream()
                    .anyMatch(e -> Objects.equals(e.getId(), childId));
            if (isThisMain) {
                commMain = comment;
            } else {
                for (QuestionPostComment mainChildrenComm : comment.getMainChildren()) {
                    List<QuestionPostComment> children = mainChildrenComm.getMainChildren();
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

        QuestionPostComment comm = questionPostCommentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("The Comment You Are Trying To Delete Doesn't Exists!"));
        QuestionPostComment parentComm = findParentByChildId(comm.getPost().getId(), comm.getId());

        if (Objects.equals(comm.getUser().getId(), user.getId()) ||
                Objects.equals(comm.getPost().getOwner().getId(), user.getId())) {
            if (parentComm != null) {

                questionPostCommentRepository.save(parentComm);
            }

            questionPostCommentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("You Can't Delete This Comment!");
        }
    }
}
