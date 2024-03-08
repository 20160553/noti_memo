package com.podiot.noti_memo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.usecase.CreateNoteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(val createNoteUsecase: CreateNoteUsecase) :
    ViewModel() {

    fun insertNote(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.IO) {
        createNoteUsecase.insertNote(noteModel)
    }
}