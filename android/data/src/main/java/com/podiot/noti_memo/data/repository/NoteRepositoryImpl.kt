package com.podiot.noti_memo.data.repository

import android.util.Log
import com.podiot.noti_memo.data.dao.NoteDao
import com.podiot.noti_memo.data.util.toNote
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(val noteDao: NoteDao) : NoteRepository {
    override suspend fun getNotes(): Flow<List<NoteModel>> =
        noteDao.getAll().map { notes -> notes.map { note -> note.toNoteModel() } }
            .flowOn(Dispatchers.IO).conflate()

    override suspend fun getRecentNotes(): List<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteList(bool: Boolean): List<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(noteModel: NoteModel): Long =
        noteDao.insertNote(noteModel.toNote())

    override suspend fun deleteAll() = noteDao.deleteAll()

    override suspend fun delete(noteModel: NoteModel) = noteDao.delete(noteModel.toNote())

    override suspend fun search(keyword: String): List<NoteModel> =
        noteDao.search("%$keyword%").map { note ->
            note.toNoteModel()
        }

    override suspend fun update(noteModel: NoteModel) = noteDao.update(noteModel.toNote())
}