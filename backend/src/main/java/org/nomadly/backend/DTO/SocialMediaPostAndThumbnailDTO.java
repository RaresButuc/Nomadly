package org.nomadly.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomadly.backend.model.PostClasses.SocialMediaPost;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaPostAndThumbnailDTO {

    private SocialMediaPost post;

    private List<byte[]> thumbnail;

}
