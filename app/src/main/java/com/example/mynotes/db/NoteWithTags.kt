package com.example.mynotes.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class NoteWithTags(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(TagNoteCrossRef::class)
    )
    val tags: List<Tag>
)