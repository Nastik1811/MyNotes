package com.example.mynotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.Tag
import kotlinx.coroutines.launch


class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    private enum class SortingMode {DATE, TITLE }


    val notesWithTags: LiveData<List<NoteWithTags>> = repository.getAllNotesSortByDate()
    val tags: List<Tag> = repository.getAllTags()
    private var sortingMode = SortingMode.DATE

m
    fun sort() : LiveData<List<NoteWithTags>> {
        if (sortingMode == SortingMode.DATE) {
            sortingMode = SortingMode.TITLE
            return repository.getAllNotesSortByTitle()
        }
        else{
            sortingMode = SortingMode.DATE
            return repository.getAllNotesSortByDate()
        }
    }

    fun getNotesWithSelectedTag(name: String) : LiveData<List<NoteWithTags>>  {
        return if(name == "") {
            repository.getAllNotesSortByDate()
        }else{
            repository.getNotesWithSelectedTag(name)
        }
    }

}