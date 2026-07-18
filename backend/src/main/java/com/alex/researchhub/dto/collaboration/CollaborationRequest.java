package com.alex.researchhub.dto.collaboration;

import java.time.LocalDateTime;

import com.alex.researchhub.entity.CollaborationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollaborationRequest {
    private Long id;

    @NotNull
    private Long project;

    @NotNull
    private Long sender;

    @NotNull
    private Long receiver;

    private CollaborationStatus status;

    private LocalDateTime acceptedDate;
}
