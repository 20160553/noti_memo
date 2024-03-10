package com.podiot.noti_memo.domain.usecase

import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUsecase @Inject constructor(val noteRepository: NoteRepository) {

    suspend fun update(noteModel: NoteModel): Unit = noteRepository.update(noteModel)

}