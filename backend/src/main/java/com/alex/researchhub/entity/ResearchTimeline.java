package com.alex.researchhub.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "research_timelines")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResearchTimeline {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimelineStage stage;
    @Column(length = 3000)
    private String remarks;
    @Column(nullable = false)
    private LocalDate date;
    @PrePersist
    public void prePersist() { if (date == null) date = LocalDate.now(); }
}
