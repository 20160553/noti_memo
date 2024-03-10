package com.podiot.noti_memo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.usecase.CreateNoteUsecase
import com.podiot.noti_memo.domain.usecase.UpdateNoteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val createNoteUsecase: CreateNoteUsecase, private val updateNoteUsecase: UpdateNoteUsecase) :
    ViewModel() {

    fun insertNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        createNoteUsecase.insertNote(noteModel)
    }

    fun updateNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        updateNoteUsecase.update(noteModel)
    }
}