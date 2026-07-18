package com.alex.researchhub.service.collaboration;

import java.util.List;

import com.alex.researchhub.dto.collaboration.CollaborationRequest;
import com.alex.researchhub.dto.collaboration.CollaborationResponse;

public interface CollaborationService {
    CollaborationResponse createCollaboration(CollaborationRequest data);

    CollaborationResponse getCollaboration(Long id);

    List<CollaborationResponse> getAllCollaborations();

    CollaborationResponse updateCollaboration(Long id, CollaborationRequest data);

    void deleteCollaboration(Long id);
}
