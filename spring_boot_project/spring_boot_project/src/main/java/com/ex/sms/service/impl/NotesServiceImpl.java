package com.ex.sms.service.impl;

import com.ex.sms.entity.Notes;
import com.ex.sms.repository.NotesRepository;
import com.ex.sms.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Value("${notes.upload.dir}") // Injected from application.properties
    private String notesUploadDir;

    @Override
    public Notes saveNotes(Notes notes) {
        return notesRepository.save(notes);
    }

    @Override
    public List<Notes> getAllNotes() {
        return notesRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteNote(Long noteId) {
        Optional<Notes> notesOptional = notesRepository.findById(noteId);

        if (notesOptional.isPresent()) {
            Notes note = notesOptional.get();

            // IMPORTANT: If your Notes entity has a 'filePath' field storing the FULL path,
            // use that for deletion instead of constructing it with 'filename'.
            // Example: Path filePath = Paths.get(note.getFilePath()).normalize();
            Path filePath = Paths.get(notesUploadDir).resolve(note.getFilename()).normalize(); // Uses filename with base dir

            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    System.out.println("Deleted notes file: " + filePath.toString());
                } else {
                    System.out.println("Notes file not found for deletion: " + filePath.toString());
                }
            } catch (IOException e) {
                System.err.println("Error deleting notes file: " + filePath.toString() + " - " + e.getMessage());
                // Re-throw if you want the transaction to roll back or notify upstream
                // throw new IOException("Failed to delete physical file", e);
            }

            notesRepository.deleteById(noteId);
            System.out.println("Deleted note record from DB with ID: " + noteId);
        } else {
            throw new IllegalArgumentException("Note with ID " + noteId + " not found.");
        }
    }
}