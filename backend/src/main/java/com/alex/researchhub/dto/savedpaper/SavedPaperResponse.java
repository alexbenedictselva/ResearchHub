package com.alex.researchhub.dto.savedpaper;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SavedPaperResponse {
    private Long id;
    private Long researcher;
    private String paperId;
    private String title;
    private LocalDateTime savedAt;
}
