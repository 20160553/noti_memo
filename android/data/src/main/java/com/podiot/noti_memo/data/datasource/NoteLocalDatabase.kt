package com.podiot.noti_memo.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.podiot.noti_memo.data.dao.NoteDao
import com.podiot.noti_memo.data.model.Note

@Database(entities = [Note::class], version = 2)
abstract class NoteLocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}