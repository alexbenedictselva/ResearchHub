package com.alex.researchhub.dto.savedpaper;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SavedPaperRequest {
    private Long id;
    @NotNull private Long researcher;
    @NotBlank private String paperId;
    @NotBlank private String title;
    private LocalDateTime savedAt;
}
