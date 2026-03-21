package com.example.syllo_backend.Services;

import com.example.syllo_backend.Model.Subject;
import com.example.syllo_backend.Model.User;
import com.example.syllo_backend.Repository.SubjectRepository;
import com.example.syllo_backend.Repository.SubjectRepository;
import com.example.syllo_backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public Subject createSubject(String subjectName, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow();

        Subject subject = Subject.builder()
                .name(subjectName)
                .user(user)
                .build();

        return subjectRepository.save(subject);
    }

    public List<Subject> getSubjects(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return subjectRepository.findByUser(user);
    }
}
