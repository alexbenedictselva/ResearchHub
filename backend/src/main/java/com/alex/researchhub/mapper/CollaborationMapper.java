package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.collaboration.CollaborationRequest;
import com.alex.researchhub.dto.collaboration.CollaborationResponse;
import com.alex.researchhub.entity.Collaboration;
import com.alex.researchhub.entity.CollaborationStatus;

public class CollaborationMapper {
    public static Collaboration toEntity(CollaborationRequest request) {
        if (request == null) {
            return null;
        }

        return Collaboration.builder()
                .id(request.getId())
                .status(request.getStatus() != null ? request.getStatus() : CollaborationStatus.PENDING)
                .acceptedDate(request.getAcceptedDate())
                .build();
    }

    public static CollaborationResponse toResponse(Collaboration collaboration) {
        if (collaboration == null) {
            return null;
        }

        return CollaborationResponse.builder()
                .id(collaboration.getId())
                .project(collaboration.getProject() != null ? collaboration.getProject().getId() : null)
                .sender(collaboration.getSender() != null ? collaboration.getSender().getId() : null)
                .receiver(collaboration.getReceiver() != null ? collaboration.getReceiver().getId() : null)
                .status(collaboration.getStatus())
                .acceptedDate(collaboration.getAcceptedDate())
                .build();
    }
}
