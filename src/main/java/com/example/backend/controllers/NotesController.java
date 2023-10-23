package com.example.backend.controllers;

import com.example.backend.dto.NoteDto;
import com.example.backend.dto.Views;
import com.example.backend.services.NoteService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {
    private final NoteService noteService;

    @Operation(
            summary = "Find all Notes",
            description = "Get all Note objects.",
            tags = {"get"}
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Get.class)
    Collection<NoteDto> findAll() {
        log.info("Found all notes: {}", noteService.findAll());
        return noteService.findAll();
    }

    @Operation(
            summary = "Find a Note by Id",
            description = "Get a Note object by specifying its id.",
            tags = {"get"}
    )
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the note", content = @Content(schema = @Schema(implementation = NoteDto.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content(schema = @Schema()))
    })
    @JsonView(Views.Get.class)
    NoteDto findById(@PathVariable @NotNull String id) {
        log.info("Finding note by id {}", id);
        return noteService.findById(id);
    }

    @Operation(
            summary = "Find all Notes by User Id",
            description = "Get all Note objects by specifying its user id.",
            tags = {"get"}
    )
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @JsonView(Views.Get.class)
    Collection<NoteDto> findAllByUserId(@PathVariable @NotNull String userId) {
        return noteService.findAllByUserId(userId);
    }

    @Operation(
            summary = "Create a Note",
            description = "Create a Note object.",
            tags = {"post"}
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(Views.Post.class)
    void add(@RequestBody @JsonView(value = Views.Post.class) @NotNull NoteDto note) {
        log.debug("Create note: {}", note);
        noteService.add(note);
    }

    @Operation(
            summary = "Update a Note by Id",
            description = "Update a Note object by specifying its id.",
            tags = {"put"}
    )
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the note", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content(schema = @Schema()))
    })
    @JsonView(Views.Put.class)
    void update(@PathVariable @NotNull String id, @RequestBody @NotNull NoteDto noteDto) {
        noteService.update(id, noteDto);
    }

    @Operation(
            summary = "Delete a Note by Id",
            description = "Delete a Note object by specifying its id.",
            tags = {"delete"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the note", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content(schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable @NotNull String id) {
        noteService.delete(id);
    }

    @Operation(
            summary = "Delete all Notes",
            description = "Delete all Note objects.",
            tags = {"delete"}
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll() {
        noteService.deleteAll();
    }

    @Operation(
            summary = "Delete all Notes by User Id",
            description = "Delete all Note objects by specifying its user id.",
            tags = {"delete"}
    )
    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAllByUserId(@PathVariable @NotNull String userId) {
        noteService.deleteAllByUserId(userId);
    }
}
