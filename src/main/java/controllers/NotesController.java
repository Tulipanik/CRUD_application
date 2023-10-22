package controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Note;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.NoteService;

import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NotesController {
    private final NoteService noteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Map<String, Note> findAll() {
        log.info("Found all notes: {}", noteService.findAll());
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Note findById(@PathVariable @NotNull String id) {
        log.info("Finding note by id {}", id);
        return noteService.findById(id);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    Collection<Note> findAllByUserId(@PathVariable @NotNull String userId) {
        return noteService.findAllByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void add(@RequestBody @NotNull Note note) {
        log.info("Adding note {}", note);
        noteService.add(note);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable @NotNull String id, @RequestBody @NotNull Note note) {
        noteService.update(id, note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable @NotNull String id) {
        noteService.delete(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll() {
        noteService.deleteAll();
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAllByUserId(@PathVariable @NotNull String userId) {
        noteService.deleteAllByUserId(userId);
    }
}
