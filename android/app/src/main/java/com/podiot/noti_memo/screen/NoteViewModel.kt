package com.podiot.noti_memo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(val noteRepository: NoteRepository) : ViewModel() {

    private val _noteList = MutableStateFlow<List<NoteModel>>(emptyList())
    var noteList = _noteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    if (!listOfNotes.isNullOrEmpty()) {
                        _noteList.value = listOfNotes
                    }
                }
        }
    }


}