package com.example.noteappwithauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteappwithauthentication.preferences.AuthViewModel
import com.example.noteappwithauthentication.ui.NoteApp
import com.example.noteappwithauthentication.ui.theme.NoteAppWithAuthenticationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel by viewModels<AuthViewModel> {
            AuthViewModel.Factory
        }
        setContent {
            NoteAppWithAuthenticationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NoteApp(startDestination = authViewModel.startDestination.value)
                }
            }
        }
    }
}

