package com.alex.researchhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.researchhub.entity.Collaboration;

public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {
}
