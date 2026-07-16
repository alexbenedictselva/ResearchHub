package com.alex.researchhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.researchhub.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Long>{
    
}
