package com.example.mynotes.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tags")
data class Tag (
    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0,
    var name: String
)
{
    override fun toString(): String {
        return "#$name"
    }
}