package com.ex.sms.service;

import com.ex.sms.entity.*;
import java.util.*;

public interface NotesService
{
    Notes saveNotes(Notes notes);
    List<Notes> getAllNotes();
    void deleteNote(Long noteId); // <--- NEW METHOD
}