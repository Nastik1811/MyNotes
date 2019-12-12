package com.example.mynotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.Tag
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    val notesWithTags: LiveData<List<NoteWithTags>>
    val notes: LiveData<List<Note>>
    val tags: List<Tag>

    init{
        notesWithTags = repository.allNotesWithTags
        notes = repository.allNotes
        tags = repository.allTags
    }

}