package com.example.noteappwithauthentication.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import com.example.noteappwithauthentication.R

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = null
        )
        Text(
            text = "Refresh",
            textDecoration = TextDecoration.Underline,
            modifier = modifier
        )
    }
}