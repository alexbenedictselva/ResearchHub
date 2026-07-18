package com.alex.researchhub.service.savedpaper;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alex.researchhub.dto.savedpaper.*;
import com.alex.researchhub.entity.*;
import com.alex.researchhub.mapper.SavedPaperMapper;
import com.alex.researchhub.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
@Service @RequiredArgsConstructor
public class SavedPaperServiceImpl implements SavedPaperService {
    private final SavedPaperRepository savedPaperRepository; private final ResearcherRepository researcherRepository;
    public SavedPaperResponse createSavedPaper(SavedPaperRequest data) { SavedPaper paper = SavedPaperMapper.toEntity(data); paper.setResearcher(researcher(data.getResearcher())); return SavedPaperMapper.toResponse(savedPaperRepository.save(paper)); }
    public SavedPaperResponse getSavedPaper(Long id) { return SavedPaperMapper.toResponse(paper(id)); }
    public List<SavedPaperResponse> getAllSavedPapers() { return savedPaperRepository.findAll().stream().map(SavedPaperMapper::toResponse).toList(); }
    public SavedPaperResponse updateSavedPaper(Long id, SavedPaperRequest data) { SavedPaper paper = paper(id); paper.setPaperId(data.getPaperId()); paper.setTitle(data.getTitle()); paper.setSavedAt(data.getSavedAt() != null ? data.getSavedAt() : paper.getSavedAt()); paper.setResearcher(researcher(data.getResearcher())); return SavedPaperMapper.toResponse(savedPaperRepository.save(paper)); }
    public void deleteSavedPaper(Long id) { savedPaperRepository.delete(paper(id)); }
    private SavedPaper paper(Long id) { return savedPaperRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Saved paper not found")); }
    private Researcher researcher(Long id) { return researcherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Researcher not found")); }
}
