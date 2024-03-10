package com.podiot.noti_memo.domain.repository

import com.podiot.noti_memo.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<List<NoteModel>>

    suspend fun getRecentNotes(): List<NoteModel>

    suspend fun getFavoriteList(bool: Boolean): List<NoteModel>

    suspend fun insertNote(noteModel: NoteModel): Long

    suspend fun deleteAll()

    suspend fun delete(noteModel: NoteModel)

    suspend fun search(keyword: String): List<NoteModel>

    suspend fun update(noteModel: NoteModel)
}