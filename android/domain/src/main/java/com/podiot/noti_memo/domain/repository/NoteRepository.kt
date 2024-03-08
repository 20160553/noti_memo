package com.podiot.noti_memo.domain.repository

import com.podiot.noti_memo.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<List<NoteModel>>

    suspend fun getRecentFiveNotes(): List<NoteModel>

    suspend fun getFavoriteList(bool: Boolean): List<NoteModel>

    suspend fun getNoteListSelectedTime(ymd: Int): List<NoteModel>

    suspend fun countNote(): Int

    suspend fun getNoteUsingUid(uid: Int?): NoteModel

    suspend fun countNoteSelectedTime(ymd: Int): Int

    suspend fun insertNote(noteModel: NoteModel): Long

    suspend fun deleteAll()

    suspend fun delete(noteModel: NoteModel)

    suspend fun findByResult(search: String): List<NoteModel>

    suspend fun update(noteModel: NoteModel)
}