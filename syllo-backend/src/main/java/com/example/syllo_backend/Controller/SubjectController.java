package com.example.syllo_backend.Controller;

import com.example.syllo_backend.Model.Subject;
import com.example.syllo_backend.Services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public Subject create(@RequestParam String name,
                          Authentication authentication) {
        String email = authentication.getName();
        return subjectService.createSubject(name, email);
    }

    @GetMapping
    public List<Subject> list(Authentication authentication){
        String email=authentication.getName();
        return subjectService.getSubjects(email);
    }

}
