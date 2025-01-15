package org.nomadly.backend.model.CommentClasses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.ParentCommData;
import org.nomadly.backend.model.PostClasses.QuestionPost;
import org.nomadly.backend.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "question_post_comments")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class QuestionPostComment extends Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("parent-reference")
    private QuestionPostComment parent;

    @ManyToOne
    @JsonBackReference("main-reference")
    private QuestionPostComment mainComment;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @JsonManagedReference("parent-reference")
    private List<QuestionPostComment> parentChildren;

    @OneToMany(mappedBy = "mainComment", orphanRemoval = true)
    @JsonManagedReference("main-reference")
    private List<QuestionPostComment> mainChildren;

    @JsonIgnore
    @ManyToOne
    @JoinColumn
    private QuestionPost post;

    public QuestionPostComment(User user, ParentCommData parentCommData, String content, List<User> likers,
                               Long likes, boolean edited, LocalDateTime postTime, String stringPostTime,
                               QuestionPost post) {
        super(user, parentCommData, content, likers, likes, edited, postTime, stringPostTime);
        this.post = post;
    }
}
