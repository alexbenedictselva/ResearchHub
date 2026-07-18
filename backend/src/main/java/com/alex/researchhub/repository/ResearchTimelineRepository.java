package com.alex.researchhub.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alex.researchhub.entity.ResearchTimeline;
public interface ResearchTimelineRepository extends JpaRepository<ResearchTimeline, Long> {}
