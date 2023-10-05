package com.example.noteappwithauthentication.ui.component

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyFAB(
    modifier: Modifier = Modifier,
    navigate: () -> Unit,
    imageVector: ImageVector
) {
    FloatingActionButton(onClick = { navigate() }, modifier = modifier) {
        Icon(imageVector = imageVector, contentDescription = "add")
    }
}