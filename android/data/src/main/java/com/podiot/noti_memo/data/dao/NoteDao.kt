package com.podiot.noti_memo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.podiot.noti_memo.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY time DESC")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY time DESC LIMIT 3")
    fun getRecentNotes(): List<Note>

    @Query("SELECT * FROM note WHERE favorite=:bool")
    fun getFavoriteList(bool: Boolean): List<Note>

    @Query("SELECT COUNT(*) FROM note")
    fun countNote(): Int

    @Insert
    fun insertNote(note: Note): Long

    @Query("DELETE FROM note")
    fun deleteAll()

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note WHERE content LIKE :keyword ORDER BY time DESC")
    fun search(keyword: String): List<Note>

    @Update
    fun update(note: Note)
}