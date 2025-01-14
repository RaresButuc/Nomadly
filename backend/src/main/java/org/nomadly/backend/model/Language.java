package org.nomadly.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.nomadly.backend.model.PostClasses.QuestionPost;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String languageNameEnglish;

    @NotNull
    @NotBlank
    private String languageNameNative;

    @NotNull
    @NotBlank
    private String cca2;

    @OneToMany(mappedBy = "language")
    @JsonIgnore
    private List<QuestionPost> articles;
}
