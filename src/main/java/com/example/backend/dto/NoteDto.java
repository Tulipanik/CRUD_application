package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NoteDto {
    @Schema(description = "Note primary key")
    @JsonView({Views.Get.class, Views.Put.class})
    String id;

    @Schema(description = "Note title", example = "My note")
    @JsonView({Views.Get.class, Views.Put.class, Views.Post.class})
    String title;

    @Schema(description = "Note content", example = "Some text")
    @JsonView({Views.Get.class, Views.Put.class, Views.Post.class})
    String content;

    @Schema(description = "User id", example = "1")
    @JsonView({Views.Get.class, Views.Put.class, Views.Post.class})
    String userId;
}
