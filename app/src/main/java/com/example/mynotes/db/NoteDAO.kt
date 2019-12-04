package com.example.mynotes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.db.Note

@Dao
interface NoteDAO{
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotesWithTags(): LiveData<List<NoteWithTags>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId= :noteId")
    fun getNoteWithTags(noteId: Long): LiveData<NoteWithTags>?

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}