package com.example.mynotes.db

import androidx.room.*
import com.example.mynotes.db.Tag

@Dao
interface TagDAO{
    @Query("SELECT name from tags")
    fun getAllTags(): List<String>

    @Insert
    fun addTag(tag: Tag)

    @Update
    fun updateTag(tag: Tag)

    @Delete
    fun deleteTag(tag: Tag)

    @Insert
    fun addTagToNote(crossRef: TagNoteCrossRef)

    @Delete
    fun removeTagFromNote(crossRef: TagNoteCrossRef)

}