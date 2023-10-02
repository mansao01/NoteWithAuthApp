package com.example.noteappwithauthentication.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.noteappwithauthentication.data.network.response.NoteDataItem

@Composable
fun NoteListItem(
    note:NoteDataItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Column {
            Text(text = note.title, modifier = Modifier
                .padding(top = 4.dp)
                .padding(start = 8.dp))
            Text(
                text = note.description,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}