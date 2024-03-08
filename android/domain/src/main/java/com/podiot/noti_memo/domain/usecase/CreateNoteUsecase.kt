package com.podiot.noti_memo.domain.usecase

import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import javax.inject.Inject

class CreateNoteUsecase @Inject constructor(val noteRepository: NoteRepository) {

    suspend fun insertNote(noteModel: NoteModel): Long = noteRepository.insertNote(noteModel)

}