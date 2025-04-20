package com.example.firebasetest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebasetest.data.Room

@Composable
fun RoomItem(room: Room, onJoinClicked: (Room) -> Unit,
             onNameChanged: (String, String) -> Unit,
             onDeleteClick: (String) -> Unit)
{
    var showEditDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = room.name, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                onClick = { onJoinClicked(room) }
            ) {
                Text("Join")
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Options")
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {
                        showEditDialog = true
                        showMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = "Edit"
                        )
                    })
                DropdownMenuItem(
                    text = { Text("Delete") },
                    onClick = {
                        onDeleteClick(room.id)
                        showMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Delete,
                            contentDescription = "Delete"
                        )
                    })
            }
        }
    }
    if (showEditDialog) {
        EditRoomNameDialog(
            currentName = room.name,
            onNameChanged = { newName ->
                onNameChanged(room.id, newName)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}


/*
@Preview(showBackground = true)
@Composable
fun PrevRoomItem(){
    RoomItem(Room("id.com","name")){}
}

*/