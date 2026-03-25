package com.example.syllo_backend.Services;
import com.example.syllo_backend.Model.ResourceType;
import com.example.syllo_backend.Model.Subject;
import com.example.syllo_backend.Model.SubjectResource;
import com.example.syllo_backend.Model.User;
import com.example.syllo_backend.Repository.SubjectRepository;
import com.example.syllo_backend.Repository.SubjectResourceRepository;
import com.example.syllo_backend.Repository.UserRepository;
import com.example.syllo_backend.dto.CreateResourceRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final SubjectRepository subjectRepository;
    private final SubjectResourceRepository resourceRepository;
    private final UserRepository userRepository;
    // ✅ Create resource by raw text (manual paste)
    public SubjectResource createResource(CreateResourceRequest request,
                                          String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized subject access");
        }

        SubjectResource resource = SubjectResource.builder()
                .subject(subject)
                .type(request.getType())
                .title(request.getTitle())
                .rawText(request.getRawText())
                .build();

        return resourceRepository.save(resource);
    }
    // ✅ Upload file + extract text
    public SubjectResource uploadFile(Long subjectId,
                                      ResourceType type,
                                      String title,
                                      MultipartFile file,
                                      String userEmail) throws Exception {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized subject access");
        }

        // create uploads folder if not exists
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadDir.resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Apache Tika text extraction
        Tika tika = new Tika();
        String extractedText = tika.parseToString(filePath);

        SubjectResource resource = SubjectResource.builder()
                .subject(subject)
                .type(type)
                .title(title)
                .fileUrl(filePath.toString())
                .rawText(extractedText)
                .build();

        return resourceRepository.save(resource);
    }

    // ✅ Get all resources of a subject
    public List<SubjectResource> getResources(Long subjectId,
                                              String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (!subject.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized subject access");
        }

        return resourceRepository.findAll()
                .stream()
                .filter(r -> r.getSubject().getId().equals(subjectId))
                .toList();
    }
    //Search Service
    public List<SubjectResource> globalSearch(String keyword, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return resourceRepository.searchUserResources(user.getId(), keyword);
    }
}
