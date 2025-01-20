package org.nomadly.backend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private String fileName;
    private String isThumbnail;

    public boolean getIsThumbnailAsBoolean() {
        return Boolean.parseBoolean(isThumbnail);
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "fileName='" + fileName + '\'' +
                ", isThumbnail='" + isThumbnail + '\'' +
                '}';
    }

}
