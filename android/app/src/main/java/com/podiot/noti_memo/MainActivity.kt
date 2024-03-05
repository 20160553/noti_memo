package com.podiot.noti_memo

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.podiot.noti_memo.data.model.Note
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

                    Greeting("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var list = remember {
        mutableStateListOf<Note>()
    }
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
                list.add(Note(content = value))
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
            items(list) { note ->
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
                modifier = Modifier.padding(20.dp).weight(1f)
            )
            Button(onClick = {
                list.add(Note(content = value))
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