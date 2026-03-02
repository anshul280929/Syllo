package com.example.syllo_backend.Repository;

import com.example.syllo_backend.Model.Subject;
import com.example.syllo_backend.Model.SubjectResource;
import com.example.syllo_backend.Model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectResourceRepository extends JpaRepository<SubjectResource, Long> {
    List<SubjectResource> findBySubjectAndType(Subject subject, ResourceType type);
}
