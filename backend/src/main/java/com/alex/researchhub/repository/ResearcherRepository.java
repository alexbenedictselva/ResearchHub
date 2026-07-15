package com.alex.researchhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.researchhub.entity.Researcher;
import java.util.List;


public interface ResearcherRepository extends JpaRepository<Researcher, Long> {
    
    Optional<Researcher> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
