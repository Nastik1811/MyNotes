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


class NoteDetailViewModel(noteId: Long, application: Application): AndroidViewModel(application) {
    private enum class OpenMode {UPDATE, CREATE}

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    private var openMode: OpenMode
    var note: Note
    val tags: MutableList<Tag>
    val allTags: List<Tag>

    init {
        if (noteId > 0) {
            val noteWithTags = repository.getNoteWithTags(noteId)
            note = noteWithTags.note
            tags = noteWithTags.tags
            openMode = OpenMode.UPDATE
            removeTags()
        }
        else {
            note = Note()
            tags = mutableListOf()
            openMode = OpenMode.CREATE
        }
        allTags = repository.allTags
    }

    fun saveNote() = viewModelScope.launch {
        if(note.title == "") note.title = DateTimeConverter.getFormattedDate(note.creationDate)
        var noteId = note.noteId
        when(openMode){
            OpenMode.UPDATE -> repository.updateNote(note)
            OpenMode.CREATE -> noteId = repository.addNote(note)
        }
        tags.forEach{
            repository.addTagToNote(noteId, it.tagId)
        }
    }

    fun removeTags() = viewModelScope.launch {
        tags.forEach {
            repository.removeTag(note.noteId, it.tagId)
        }
    }

    fun deleteNote() = viewModelScope.launch {
        if (openMode == OpenMode.UPDATE ) repository.deleteNote(note)
        }


    fun addTag(tag: Tag) = viewModelScope.launch {
        tags.add(tag)
    }


}