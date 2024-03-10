package com.podiot.noti_memo.domain.usecase

import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUsecase @Inject constructor(val noteRepository: NoteRepository) {

    suspend fun delete(noteModel: NoteModel): Unit = noteRepository.delete(noteModel)

    suspend fun deleteAll(): Unit = noteRepository.deleteAll()

}