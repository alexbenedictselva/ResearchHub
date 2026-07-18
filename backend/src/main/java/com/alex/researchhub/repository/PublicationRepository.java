package com.alex.researchhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.researchhub.entity.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
