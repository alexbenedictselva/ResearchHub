package com.alex.researchhub.service.savedpaper;
import java.util.List;
import com.alex.researchhub.dto.savedpaper.*;
public interface SavedPaperService {
    SavedPaperResponse createSavedPaper(SavedPaperRequest data);
    SavedPaperResponse getSavedPaper(Long id);
    List<SavedPaperResponse> getAllSavedPapers();
    SavedPaperResponse updateSavedPaper(Long id, SavedPaperRequest data);
    void deleteSavedPaper(Long id);
}
