package com.example.mynotes

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.NoteDAO
import java.lang.IllegalArgumentException


@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(
    private val database: AppDb,
    private val application: Application
): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)){
            return NotesViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}