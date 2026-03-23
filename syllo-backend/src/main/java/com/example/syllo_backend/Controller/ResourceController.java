package com.example.syllo_backend.Controller;

import com.example.syllo_backend.Model.ResourceType;
import com.example.syllo_backend.Model.SubjectResource;
import com.example.syllo_backend.Services.ResourceService;
import com.example.syllo_backend.dto.CreateResourceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // ✅ Create resource by raw text
    @PostMapping
    public SubjectResource create(@RequestBody CreateResourceRequest request,
                                  Authentication authentication) {

        String email = authentication.getName();
        return resourceService.createResource(request, email);
    }

    // ✅ Upload file resource
    @PostMapping("/upload")
    public SubjectResource upload(@RequestParam Long subjectId,
                                  @RequestParam ResourceType type,
                                  @RequestParam String title,
                                  @RequestParam MultipartFile file,
                                  Authentication authentication) throws Exception {

        String email = authentication.getName();

        return resourceService.uploadFile(
                subjectId,
                type,
                title,
                file,
                email
        );
    }

    // ✅ Get subject resources
    @GetMapping("/{subjectId}")
    public List<SubjectResource> list(@PathVariable Long subjectId,
                                      Authentication authentication) {

        String email = authentication.getName();
        return resourceService.getResources(subjectId, email);
    }
}
