package org.nomadly.backend.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ParentCommData {

    private Long parent_data_id;

    private String name;
}
