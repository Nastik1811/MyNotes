package com.example.mynotes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val noteId: Long,
    private val application: Application
): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteDetailViewModel::class.java)){
            return NoteDetailViewModel(noteId, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}