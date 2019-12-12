package com.example.mynotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.Tag
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    private val repository: NotesRepository = NotesRepository(application, viewModelScope)
    var notesWithTags: LiveData<List<NoteWithTags>> = repository.allNotesWithTags
    val tags: List<Tag> = repository.allTags

    fun getNotesWithSelectedTag(name: String):LiveData<List<NoteWithTags>>{
        return repository.getNotesWithSelectedTag(name)
    }

}