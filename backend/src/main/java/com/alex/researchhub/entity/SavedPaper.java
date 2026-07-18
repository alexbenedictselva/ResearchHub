package com.alex.researchhub.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "saved_papers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SavedPaper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "researcher_id", nullable = false)
    private Researcher researcher;
    @Column(nullable = false)
    private String paperId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDateTime savedAt;
    @PrePersist
    public void prePersist() { if (savedAt == null) savedAt = LocalDateTime.now(); }
}
