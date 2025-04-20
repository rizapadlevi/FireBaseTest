package com.example.firebasetest.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasetest.viewmodels.MessageViewModel
import com.example.firebasetest.viewmodels.MessageViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(roomId: String){
    val context = LocalContext.current
    val factory = remember(roomId){ MessageViewModelFactory(roomId, context) }
    val messageViewModel: MessageViewModel = viewModel(factory=factory)

    val currentUser by messageViewModel.currentUser.observeAsState()

    val messages by messageViewModel.messages.observeAsState(emptyList())

    var text by remember{ mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(messages, key={it.id}){ message ->
                ChatMessageItem(message=message, currentUser=currentUser)
            }

        }
        Row(modifier=Modifier.fillMaxWidth().padding(8.dp)
            ,verticalAlignment=Alignment.CenterVertically)
        {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = TextStyle.Default.copy(fontSize = 16.sp),
                modifier = Modifier.weight(1f).padding(8.dp),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = "Tulis Sesuatu",
                            color = LocalContentColor.current.copy(alpha = 0.3f),
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
            IconButton(
                onClick = {
                    if(text.isNotEmpty()){
                        messageViewModel.sendMessage(text.trim())
                        text=""
                    }
                }
            ) {
                Icon(imageVector= Icons.AutoMirrored.Filled.Send,contentDescription="send")
            }
        }

    }
}

