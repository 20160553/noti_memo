package com.podiot.noti_memo.domain.usecase

import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteUsecase @Inject constructor(val noteRepository: NoteRepository) {

    suspend fun getNotes(): Flow<List<NoteModel>> = noteRepository.getNotes()

    suspend fun search(keyword: String) = noteRepository.search(keyword)

//    suspend fun getRecentNotes(): List<NoteModel>
//
//    suspend fun getFavoriteList(bool: Boolean): List<NoteModel>
//
//    suspend fun findByResult(search: String): List<NoteModel>
}