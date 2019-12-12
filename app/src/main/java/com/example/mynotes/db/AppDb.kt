package com.example.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mynotes.db.dao.NoteDao
import com.example.mynotes.db.dao.TagDao
import com.example.mynotes.db.entity.Note
import com.example.mynotes.db.entity.Tag
import com.example.mynotes.db.entity.TagNoteCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [Note::class, Tag::class, TagNoteCrossRef::class], version = 1,  exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val noteDao = database.noteDao()
                    val tagDao = database.tagDao()
                    listOf(
                        Note(1, title = "First note", content = "Hello world! "),
                        Note(2, title = "Intent", content = "An Intent provides a facility for performing late " +
                                "runtime binding between the code in different applications. Its most significant use is in the " +
                                "launching of activities, where it can be thought of as the glue between activities. It is basically " +
                                "a passive data structure holding an abstract description of an action to be performed."),
                        Note(3, title = "Third note", content = "hello world")
                    ).forEach{
                        noteDao.addNote(it)
                    }

                    listOf(
                        Tag(1, name = "android"),
                        Tag(2, name = "tag1"),
                        Tag(3, name = "tag2"),
                        Tag(4, name = "tag3")
                    ).forEach{
                        tagDao.addTag(it)
                    }

                    listOf(
                        TagNoteCrossRef(1, 2),
                        TagNoteCrossRef(1, 3),
                        TagNoteCrossRef(2, 1)
                    ).forEach {
                        noteDao.addTagToNote(it)
                    }
                }
            }
        }
    }

    companion object {
        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile
        private var INSTANCE: AppDb? = null
        fun getInstance(context: Context, scope: CoroutineScope): AppDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDb::class.java,
                        "notes_db"
                    )
                        .allowMainThreadQueries()
                        .addCallback(NoteDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}