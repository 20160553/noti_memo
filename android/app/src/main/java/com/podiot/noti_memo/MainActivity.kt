package com.podiot.noti_memo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.podiot.noti_memo.screen.CreateNoteViewModel
import com.podiot.noti_memo.screen.NoteScreen
import com.podiot.noti_memo.screen.NoteViewModel
import com.podiot.noti_memo.ui.theme.Noti_memoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Noti_memoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val noteViewModel = viewModel<NoteViewModel>()
                    val createNoteViewModel = viewModel<CreateNoteViewModel>()

                    NoteApp(noteViewModel, createNoteViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteApp(noteViewModel: NoteViewModel, createNoteViewModel: CreateNoteViewModel) {

    val notes = noteViewModel.noteList.collectAsState().value
    var expendedNoteId by remember { mutableStateOf<Int?>(null) }
    var noteContentEditFlag by remember {
        mutableStateOf(false)
    }

    NoteScreen(
        notes = notes,
        expendedNoteId = expendedNoteId,
        noteContentEditFlag = noteContentEditFlag,
        onSearch = { keyword ->
            noteViewModel.search(keyword)
        },
        onInsertNote = { note ->
            createNoteViewModel.insertNote(note)
        },
        onClickNote = {
            noteContentEditFlag = false
            expendedNoteId = when (expendedNoteId == it.uid) {
                true -> null
                false -> it.uid
            }
        },
        onUpdateNote = { note ->
            if (noteContentEditFlag) {
                noteContentEditFlag = false
                createNoteViewModel.updateNote(note)
            } else {
                noteContentEditFlag = true
            }
        },
        onShareNote = { note ->

        },
        onDeleteNote = { note ->
            noteContentEditFlag = false
            expendedNoteId = null
            noteViewModel.delete(note)
        }
    )

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Noti_memoTheme {
        NoteScreen(
            listOf(),
            onSearch = {},
            onInsertNote = {},
            onClickNote = {},
            onUpdateNote = {},
            onShareNote = {},
            onDeleteNote = {},
            noteContentEditFlag = false
        )
    }
}