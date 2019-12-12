package com.example.mynotes.db.dao

import androidx.room.*
import com.example.mynotes.db.entity.Tag

@Dao
interface TagDao{
    @Query("SELECT * from tags")
    fun getAllTags(): List<Tag>

    @Insert
    suspend fun addTag(tag: Tag)

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("DELETE FROM tags WHERE tagId >= 0")
    suspend fun deleteAllTags()

}