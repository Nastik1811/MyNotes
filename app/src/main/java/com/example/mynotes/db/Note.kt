package com.example.mynotes.db

import android.animation.TypeConverter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "notes")
data class Note (
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0,
    var title: String,
    var content: String?,
    val creationDate: Date = Date(System.currentTimeMillis())
)