package com.example.mynotes.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.TagNoteCrossRef

@Dao
interface NoteDao{
    @Transaction
    @Query("SELECT * FROM notes")
    fun getNotesWithTags(): LiveData<List<NoteWithTags>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId= :noteId")
    fun getNoteWithTags(noteId: Long): NoteWithTags

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>



    @Insert
    suspend fun addNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes WHERE noteId >= 0")
    suspend fun deleteAllNotes()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTagToNote(crossRef: TagNoteCrossRef)

    @Delete
    suspend fun removeTagFromNote(crossRef: TagNoteCrossRef)

}