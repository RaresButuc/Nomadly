package org.nomadly.backend.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailStructure {
    private String subject;
    private String message;
}
