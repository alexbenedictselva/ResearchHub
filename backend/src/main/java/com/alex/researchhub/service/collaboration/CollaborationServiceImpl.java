package com.alex.researchhub.service.collaboration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alex.researchhub.dto.collaboration.CollaborationRequest;
import com.alex.researchhub.dto.collaboration.CollaborationResponse;
import com.alex.researchhub.entity.Collaboration;
import com.alex.researchhub.entity.CollaborationStatus;
import com.alex.researchhub.entity.Project;
import com.alex.researchhub.entity.Researcher;
import com.alex.researchhub.mapper.CollaborationMapper;
import com.alex.researchhub.repository.CollaborationRepository;
import com.alex.researchhub.repository.ProjectRepository;
import com.alex.researchhub.repository.ResearcherRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollaborationServiceImpl implements CollaborationService {
    private final CollaborationRepository collaborationRepository;
    private final ProjectRepository projectRepository;
    private final ResearcherRepository researcherRepository;

    @Override
    public CollaborationResponse createCollaboration(CollaborationRequest data) {
        Project project = projectRepository.findById(data.getProject())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        Researcher sender = researcherRepository.findById(data.getSender())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        Researcher receiver = researcherRepository.findById(data.getReceiver())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Collaboration collaboration = CollaborationMapper.toEntity(data);
        collaboration.setProject(project);
        collaboration.setSender(sender);
        collaboration.setReceiver(receiver);
        collaboration.setStatus(data.getStatus() != null ? data.getStatus() : CollaborationStatus.PENDING);

        return CollaborationMapper.toResponse(collaborationRepository.save(collaboration));
    }

    @Override
    public CollaborationResponse getCollaboration(Long id) {
        Collaboration collaboration = collaborationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collaboration not found"));
        return CollaborationMapper.toResponse(collaboration);
    }

    @Override
    public List<CollaborationResponse> getAllCollaborations() {
        return collaborationRepository.findAll().stream()
                .map(CollaborationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CollaborationResponse updateCollaboration(Long id, CollaborationRequest data) {
        Collaboration collaboration = collaborationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collaboration not found"));

        if (data.getStatus() == CollaborationStatus.ACCEPTED) {
            collaboration.setAcceptedDate(LocalDateTime.now());
        } else if (data.getStatus() == CollaborationStatus.REJECTED) {
            collaboration.setAcceptedDate(null);
        }

        collaboration.setStatus(data.getStatus() != null ? data.getStatus() : collaboration.getStatus());

        return CollaborationMapper.toResponse(collaborationRepository.save(collaboration));
    }

    @Override
    public void deleteCollaboration(Long id) {
        collaborationRepository.deleteById(id);
    }
}
