package com.podiot.noti_memo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.podiot.noti_memo.domain.model.NoteModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NoteScreen(
    notes: List<NoteModel>,
    expendedNoteId: Int? = null,
    noteContentEditFlag: Boolean,
    scrollState: LazyListState,
    onSearch: (keyword: String) -> Unit,
    onInsertNote: (note: NoteModel) -> Unit,
    onClickNote: (note: NoteModel) -> Unit,
    onUpdateNote: (note: NoteModel) -> Unit,
    onShareNote: (note: NoteModel) -> Unit,
    onDeleteNote: (note: NoteModel) -> Unit
) {
    var value by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        SearchLayout(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .background(Color.Yellow),
            onSearch = onSearch
        )
        NoteLazyColumn(
            notes = notes,
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray),
            clickedNoteId = expendedNoteId,
            scrollState = scrollState,
            noteContentEditFlag = noteContentEditFlag,
            onClickNote = onClickNote,
            onUpdateNote = onUpdateNote,
            onShareNote = onShareNote,
            onDeleteNote = onDeleteNote
        )
        NoteInsertLayout(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .background(Color.Yellow),
            onInsertNote = onInsertNote
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLayout(
    modifier: Modifier = Modifier,
    onSearch: (keyword: String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        TextField(
            value = value,
            onValueChange = {
                value = it
                onSearch(value)
            },
            label = { Text("Enter text") },
            maxLines = 10,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        Button(onClick = {
            /* TODO Search Favorite */
        }) {
            Text(text = "클릭", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteLazyColumn(
    modifier: Modifier = Modifier,
    notes: List<NoteModel>,
    clickedNoteId: Int?,
    scrollState: LazyListState,
    noteContentEditFlag: Boolean,
    onClickNote: (note: NoteModel) -> Unit,
    onUpdateNote: (note: NoteModel) -> Unit,
    onShareNote: (note: NoteModel) -> Unit,
    onDeleteNote: (note: NoteModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        state = scrollState
    ) {
        itemsIndexed(
            items = notes,
            key = { index, note ->
                note.uid ?: 0
            }
        ) { index, note ->
            Surface(
                Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = Color(0xFFDFE6EB),
            ) {
                Column(
                    Modifier
                        .clickable {
                            /* TODO OnClick */
                            onClickNote(note)
                        }
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    var value by remember {
                        mutableStateOf(note.content ?: "null")
                    }
                    val clicked = note.uid == clickedNoteId

                    if (!noteContentEditFlag && clicked) {
                        value = "${note.content}"
                    }
                    if (noteContentEditFlag && clicked) {
                        TextField(value = value, onValueChange = {
                            value = it
                        })
                    } else {
                        Text(text = "${note.content}", style = MaterialTheme.typography.titleMedium)
                    }
                    if (clicked) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .background(Color.Magenta)
                        )

                        Button(onClick = {
                            onUpdateNote(
                                note.copy(
                                    content = value,
                                    ymd = SimpleDateFormat(
                                        "yyyyMMdd",
                                        Locale.getDefault()
                                    ).format(System.currentTimeMillis())
                                        .toInt(),
                                    time = System.currentTimeMillis()
                                )
                            )
                        }) {
                            Text(text = "수정")
                        }
                        Button(onClick = {
                            onShareNote(note)
                        }) {
                            Text(text = "공유")
                        }
                        Button(onClick = {
                            onDeleteNote(note)
                        }) {
                            Text(text = "삭제")
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInsertLayout(
    modifier: Modifier = Modifier,
    onInsertNote: (note: NoteModel) -> Unit
) {
    var value by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            maxLines = 10,
            textStyle = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(20.dp)
                .weight(1f)
        )
        Button(onClick = {
            onInsertNote(
                NoteModel(
                    content = value,
                    ymd = SimpleDateFormat(
                        "yyyyMMdd",
                        Locale.getDefault()
                    ).format(System.currentTimeMillis())
                        .toInt(),
                    time = System.currentTimeMillis(),
                )
            )
            value = ""
        }) {
            Text(text = "클릭")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(
        listOf(),
        null,
        false,
        LazyListState(),
        onSearch = {},
        onInsertNote = {},
        onClickNote = {},
        onUpdateNote = {},
        onShareNote = {},
        onDeleteNote = {}
    )
}