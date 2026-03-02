package com.example.syllo_backend.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type;

    @Column(nullable = false)
    private String title;

    @Column
    private String fileUrl;

    @Lob
    private String rawText; // extracted text (used later for AI & search)
}
