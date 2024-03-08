package com.podiot.noti_memo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.screen.CreateNoteViewModel
import com.podiot.noti_memo.screen.NoteViewModel
import com.podiot.noti_memo.ui.theme.Noti_memoTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

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

                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val noteViewModel = viewModel<NoteViewModel>()
    val createNoteViewModel = viewModel<CreateNoteViewModel>()

    val notes = noteViewModel.noteList.collectAsState().value
    var value by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .background(Color.Yellow),
            verticalAlignment = Alignment.Bottom,
        ) {
            TextField(
                value = value,
                onValueChange = { value = it },
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

        LazyColumn(
            Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Gray),
            horizontalAlignment = Alignment.Start,

            ) {
            items(notes) { note ->
                Surface(
                    Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    color = Color(0xFFDFE6EB),
                ) {
                    Column(
                        modifier
                            .clickable {
                                /* TODO OnClick */
                            }
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "${note.content}", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }

        Row(
            Modifier
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .background(Color.Yellow),
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
                Log.d("add", "add $value")
                createNoteViewModel.insertNote(
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
            }) {
                Text(text = "클릭")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Noti_memoTheme {
        Greeting("Android")
    }
}