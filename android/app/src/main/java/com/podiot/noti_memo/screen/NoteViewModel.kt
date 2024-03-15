package com.podiot.noti_memo.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.usecase.DeleteNoteUsecase
import com.podiot.noti_memo.domain.usecase.GetNoteUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUsecase: GetNoteUsecase,
    private val deleteNoteUsecase: DeleteNoteUsecase
) : ViewModel() {

    private val _noteList = MutableStateFlow<List<NoteModel>>(emptyList())
    var noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getNoteUsecase.getNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    if (!listOfNotes.isNullOrEmpty()) {
                        _noteList.value = listOfNotes
                    }
                }
        }
    }

    fun search(keyword: String) = viewModelScope.launch(Dispatchers.IO) {
        _noteList.value = getNoteUsecase.search(keyword)
    }

    fun delete(noteModel: NoteModel) = viewModelScope.launch(Dispatchers.Default) {
        deleteNoteUsecase.delete(noteModel)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.Default) {
        deleteNoteUsecase.deleteAll()
    }

}