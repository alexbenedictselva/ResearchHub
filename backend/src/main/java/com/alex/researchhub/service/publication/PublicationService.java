package com.alex.researchhub.service.publication;

import java.util.List;

import com.alex.researchhub.dto.publication.PublicationRequest;
import com.alex.researchhub.dto.publication.PublicationResponse;

public interface PublicationService {

    PublicationResponse createPublication(PublicationRequest data);

    PublicationResponse getPublication(Long id);

    List<PublicationResponse> getAllPublications();

    PublicationResponse updatePublication(Long id, PublicationRequest data);

    void deletePublication(Long id);
}
