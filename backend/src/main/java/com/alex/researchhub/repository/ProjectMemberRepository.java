package com.alex.researchhub.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alex.researchhub.entity.ProjectMember;
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {}
