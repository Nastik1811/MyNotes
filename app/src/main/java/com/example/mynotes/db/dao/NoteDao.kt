package com.example.mynotes.db.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.TagNoteCrossRef

@Dao
interface NoteDao{
    @Transaction
    @Query("SELECT * FROM notes ORDER BY creationDate ASC")
    fun getAllNotesSortByDate(): LiveData<List<NoteWithTags>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId= :noteId")
    fun getNoteWithTags(noteId: Long): NoteWithTags

    @Transaction
    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getAllNotesSortByTitle(): LiveData<List<NoteWithTags>>


    @Query("SELECT * FROM notes WHERE noteId IN(SELECT noteId FROM TagNoteCrossRef WHERE tagId = (SELECT tagId from tags where name = :name))")
    fun getNotesWithTag(name: String): LiveData<List<NoteWithTags>>

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