package com.example.mynotes.db.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0,
    var title: String = "",
    var content: String = "",
    val creationDate: Date = Date()
)


data class NoteWithTags(
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(TagNoteCrossRef::class)
    )
    val tags: MutableList<Tag>
)
