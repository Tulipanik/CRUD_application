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
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
  public Collection<NoteDto> findAll() {
    log.info("Finding all notes");
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
  public NoteDto findById(@PathVariable @NotNull String id) {
    log.info("Finding note by id {}", id);
    log.info("Found note: {}", noteService.findById(id));
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
  public Collection<NoteDto> findAllByUserId(@PathVariable @NotNull String userId) {
    log.info("Finding all notes by user id {}", userId);
    log.info("Found all notes by user id: {}", noteService.findAllByUserId(userId));
    return noteService.findAllByUserId(userId);
  }

  @Operation(
      summary = "Create a Note",
      description = "Create a Note object.",
      tags = {"post"}
  )
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView(Views.Get.class)
  public NoteDto add(@RequestBody @Valid @JsonView(value = Views.Post.class) NoteDto note) {
    if (note.getUserId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
    }
    if (note.getContent() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required");
    }
    if (note.getTitle() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
    }

    log.info("Adding note: {}", note);
    var id = noteService.add(note);
    var addedNote = noteService.findById(id);
    log.info("Added note: with id {} {}", id, addedNote);
    return addedNote;
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
  public void update(@PathVariable @NotNull String id, @RequestBody @NotNull NoteDto noteDto) {
    log.info("Updating note with id {}: {}", id, noteDto);
    noteService.update(id, noteDto);
    log.info("Updated note with id {}: {}", id, noteService.findById(id));
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
  public void delete(@PathVariable @NotNull String id) {
    log.info("Deleting note with id {}", id);
    noteService.delete(id);
    log.info("Deleted note with id {}", id);
  }

  @Operation(
      summary = "Delete all Notes",
      description = "Delete all Note objects.",
      tags = {"delete"}
  )
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAll() {
    log.info("Deleting all notes");
    noteService.deleteAll();
    log.info("Deleted all notes");
  }

  @Operation(
      summary = "Delete all Notes by User Id",
      description = "Delete all Note objects by specifying its user id.",
      tags = {"delete"}
  )
  @DeleteMapping("/user/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAllByUserId(@PathVariable @NotNull String userId) {
    log.info("Deleting all notes by user id {}", userId);
    noteService.deleteAllByUserId(userId);
    log.info("Deleted all notes by user id {}", userId);
  }
}
