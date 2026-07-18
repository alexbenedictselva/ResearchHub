package com.alex.researchhub.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alex.researchhub.entity.SavedPaper;
public interface SavedPaperRepository extends JpaRepository<SavedPaper, Long> {}
