package com.example.backend.services;

import com.example.backend.dto.NoteDto;
import com.example.backend.mappings.NoteDtoMapper;
import com.example.backend.repositories.NoteRepositoryH2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteDtoMapper noteDtoMapper;
    private final NoteRepositoryH2 noteRepository;
    private static final String NOTE_NOT_FOUND_MSG = "Note with id %s does not exist";
    public static final String ID_AND_NOTE_ID_IS_NOT_EQUAL = "Id (%s) and note.id (%s) is not equal";

    /**
     * Find all notes
     *
     * @return a collection of all notes
     */
    public Collection<NoteDto> findAll() {
        return noteRepository.findAll().stream().map(noteDtoMapper::toDto).toList();
    }

    /**
     * Find a note by id
     *
     * @param id the id of the note to find
     * @return the note with the given id
     */
    public NoteDto findById(String id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOTE_NOT_FOUND_MSG.formatted(id));
        }
        return noteDtoMapper.toDto(note.get());
    }

    /**
     * Find all notes by user id
     *
     * @param userId the id of the user
     * @return a collection of all notes with the given user id
     */
    public Collection<NoteDto> findAllByUserId(String userId) {
        return noteRepository.findAll().stream().filter(note -> note.getUserId().equals(userId)).map(noteDtoMapper::toDto).toList();
    }

    /**
     * Add a note
     *
     * @param noteDto the note to add
     * @return the id of the added note
     */
    public String add(@NotNull NoteDto noteDto) {
        return noteRepository.save(noteDtoMapper.toEntity(noteDto)).getId();
    }

    /**
     * Update a note
     *
     * @param id      the id of the note to update
     * @param noteDto the note to update
     */
    public void update(String id, NoteDto noteDto) {
        if (!id.equals(noteDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_AND_NOTE_ID_IS_NOT_EQUAL.formatted(id, noteDto.getId()));
        }
        var noteToUpdate = noteRepository.findById(id);
        if (noteToUpdate.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOTE_NOT_FOUND_MSG.formatted(id));
        }
        noteRepository.save(noteDtoMapper.toEntity(noteDto));
    }

    /**
     * Delete a note by id
     *
     * @param id the id of the note to delete
     */
    public void delete(String id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOTE_NOT_FOUND_MSG.formatted(id));
        }
        noteRepository.deleteById(id);
    }

    /**
     * Delete all notes
     */
    public void deleteAll() {
        noteRepository.deleteAll();
    }

    /**
     * Delete all notes by user id
     *
     * @param userId the id of the user
     */
    public void deleteAllByUserId(String userId) {
        var notes = noteRepository.findAll();
        notes.forEach(note -> {
            if (note.getUserId().equals(userId)) {
                noteRepository.deleteById(note.getId());
            }
        });
    }

}
