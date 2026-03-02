package com.example.syllo_backend.Repository;

import com.example.syllo_backend.Model.Subject;
import com.example.syllo_backend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByUser(User user);
}
