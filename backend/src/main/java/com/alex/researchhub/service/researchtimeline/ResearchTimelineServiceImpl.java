package com.alex.researchhub.service.researchtimeline;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alex.researchhub.dto.researchtimeline.*;
import com.alex.researchhub.entity.*;
import com.alex.researchhub.mapper.ResearchTimelineMapper;
import com.alex.researchhub.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor
public class ResearchTimelineServiceImpl implements ResearchTimelineService {
    private final ResearchTimelineRepository researchTimelineRepository; private final ProjectRepository projectRepository;
    public ResearchTimelineResponse createResearchTimeline(ResearchTimelineRequest data) { ResearchTimeline timeline = ResearchTimelineMapper.toEntity(data); timeline.setProject(project(data.getProject())); return ResearchTimelineMapper.toResponse(researchTimelineRepository.save(timeline)); }
    public ResearchTimelineResponse getResearchTimeline(Long id) { return ResearchTimelineMapper.toResponse(timeline(id)); }
    public List<ResearchTimelineResponse> getAllResearchTimelines() { return researchTimelineRepository.findAll().stream().map(ResearchTimelineMapper::toResponse).toList(); }
    public ResearchTimelineResponse updateResearchTimeline(Long id, ResearchTimelineRequest data) { ResearchTimeline timeline = timeline(id); timeline.setProject(project(data.getProject())); timeline.setStage(data.getStage()); timeline.setRemarks(data.getRemarks()); timeline.setDate(data.getDate() != null ? data.getDate() : timeline.getDate()); return ResearchTimelineMapper.toResponse(researchTimelineRepository.save(timeline)); }
    public void deleteResearchTimeline(Long id) { researchTimelineRepository.delete(timeline(id)); }
    private ResearchTimeline timeline(Long id) { return researchTimelineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Research timeline not found")); }
    private Project project(Long id) { return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found")); }
}
