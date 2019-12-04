package com.example.mynotes.db

import androidx.room.*


@Entity(
        primaryKeys = ["noteId", "tagId"],
        foreignKeys = [
            ForeignKey(entity = Note::class,
                parentColumns = ["noteId"],
                childColumns = ["noteId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
            ForeignKey(entity = Tag::class,
                parentColumns = ["tagId"],
                childColumns = ["tagId"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)])
data class TagNoteCrossRef(
    @ColumnInfo(index = true) val noteId: Long,
    @ColumnInfo(index = true) val tagId: Long
)
