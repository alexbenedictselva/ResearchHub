package com.alex.researchhub.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_members", uniqueConstraints = @UniqueConstraint(columnNames = { "project_id", "researcher_id" }))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "researcher_id", nullable = false)
    private Researcher researcher;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectMemberRole role;
}
