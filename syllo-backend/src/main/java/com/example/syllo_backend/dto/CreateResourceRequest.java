package com.example.syllo_backend.dto;

import com.example.syllo_backend.Model.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateResourceRequest {
    private Long subjectId;

    private ResourceType type;

    private String title;

    private String rawText;
}
