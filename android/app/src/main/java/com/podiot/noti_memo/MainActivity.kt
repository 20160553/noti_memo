package com.podiot.noti_memo

import android.app.PendingIntent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.podiot.noti_memo.screen.CreateNoteViewModel
import com.podiot.noti_memo.screen.NoteScreen
import com.podiot.noti_memo.screen.NoteViewModel
import com.podiot.noti_memo.service.NotificationService
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

//                    val context = LocalContext.current
                    val notificationService = NotificationService(
                        applicationContext,
                        PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
                    )

                    notificationService.launcher =
                        rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestPermission()
                        ) { isGranted ->
                            /** 권한 요청시 동의 했을 경우 **/
                            if (isGranted) {
                                notificationService.showNotification("됨?")
                            }
                            /** 권한 요청시 거부 했을 경우 **/
                            else {
                                Toast.makeText(applicationContext, "권한을 허용해주세요", Toast.LENGTH_SHORT ).show()
                            }
                        }

                    LaunchedEffect(key1 = Unit) {
                        notificationService.showNotification("ㅇㅇ?")
                    }

//                    initNotification()
                    NoteApp(noteViewModel, createNoteViewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NoteApp(noteViewModel: NoteViewModel, createNoteViewModel: CreateNoteViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val notes = noteViewModel.noteList.collectAsState().value
    val scrollState = rememberLazyListState()

    var expendedNoteId by remember { mutableStateOf<Int?>(null) }
    var noteContentEditFlag by remember {
        mutableStateOf(false)
    }
    var previousNotesSize by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = notes) {
        if (notes.size >= previousNotesSize) {
            scrollState.animateScrollToItem(0)
        }
        previousNotesSize = notes.size
    }

    NoteScreen(
        notes = notes,
        expendedNoteId = expendedNoteId,
        noteContentEditFlag = noteContentEditFlag,
        scrollState = scrollState,
        onSearch = { keyword ->
            noteViewModel.search(keyword)
        },
        onInsertNote = { note ->
            createNoteViewModel.insertNote(note)
            keyboardController?.hide()
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
