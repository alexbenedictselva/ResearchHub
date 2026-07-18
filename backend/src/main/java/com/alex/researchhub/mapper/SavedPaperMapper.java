package com.alex.researchhub.mapper;

import com.alex.researchhub.dto.savedpaper.*;
import com.alex.researchhub.entity.SavedPaper;

public class SavedPaperMapper {
    public static SavedPaper toEntity(SavedPaperRequest request) {
        return SavedPaper.builder().id(request.getId()).paperId(request.getPaperId()).title(request.getTitle()).savedAt(request.getSavedAt()).build();
    }
    public static SavedPaperResponse toResponse(SavedPaper paper) {
        return SavedPaperResponse.builder().id(paper.getId()).paperId(paper.getPaperId()).title(paper.getTitle())
                .savedAt(paper.getSavedAt()).researcher(paper.getResearcher() == null ? null : paper.getResearcher().getId()).build();
    }
}
