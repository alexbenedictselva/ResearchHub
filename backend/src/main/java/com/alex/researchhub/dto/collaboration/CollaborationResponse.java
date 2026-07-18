package com.alex.researchhub.dto.collaboration;

import java.time.LocalDateTime;

import com.alex.researchhub.entity.CollaborationStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollaborationResponse {
    private Long id;
    private Long project;
    private Long sender;
    private Long receiver;
    private CollaborationStatus status;
    private LocalDateTime acceptedDate;
}
