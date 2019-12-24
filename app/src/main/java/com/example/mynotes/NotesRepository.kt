package com.example.mynotes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.db.AppDb
import com.example.mynotes.db.dao.NoteDao
import com.example.mynotes.db.dao.TagDao
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.NoteWithTags
import com.example.mynotes.db.entity.Tag
import com.example.mynotes.db.entity.TagNoteCrossRef
import kotlinx.coroutines.CoroutineScope

class NotesRepository(application: Application, viewModelScope: CoroutineScope) {

    // A Repository manages queries and allows you to use multiple backends.
    // In the most common example, the Repository implements the logic for deciding
    // whether to fetch data from a network or use results cached in a local database.
    private val noteDao = AppDb.getInstance(application, viewModelScope).noteDao()
    private val tagDao = AppDb.getInstance(application, viewModelScope).tagDao()

    fun getAllTags(): List<Tag>{
        return tagDao.getAllTags()
    }

    fun getAllNotesSortByTitle(): LiveData<List<NoteWithTags>> {
        return noteDao.getAllNotesSortByTitle()
    }

    fun getAllNotesSortByDate(): LiveData<List<NoteWithTags>>{
        return noteDao.getAllNotesSortByDate()
    }

    fun getNoteWithTags(id: Long) : NoteWithTags
    {
        return noteDao.getNoteWithTags(id)
    }

    fun getNotesWithSelectedTag(tagName: String): LiveData<List<NoteWithTags>>{
        return noteDao.getNotesWithTag(tagName)
    }

    fun getTagByName(name: String): Tag{
        return tagDao.getTagByName(name)
    }

    suspend fun addNote(note: Note):Long {
        return noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun addTagToNote(noteId: Long, tagId:Long){
        noteDao.addTagToNote(TagNoteCrossRef(noteId, tagId))
    }

    suspend fun removeTag(noteId: Long, tagId:Long){
        noteDao.removeTagFromNote(TagNoteCrossRef(noteId, tagId))
    }

    suspend fun addTag(tag: Tag) {
        tagDao.addTag(tag)
    }

    suspend fun deleteTag(tag: Tag){
        tagDao.deleteTag(tag)
    }




}