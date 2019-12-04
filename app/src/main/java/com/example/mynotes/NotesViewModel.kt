package com.example.mynotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.Note
import com.example.mynotes.db.NoteDAO

class NotesViewModel(
    val database: AppDb,
    application: Application
): AndroidViewModel(application) {


    val notes = database.noteDao.getAllNotes()

    fun addNewNote(){
        val note = Note(title = "title1", content = "content")
    }



}