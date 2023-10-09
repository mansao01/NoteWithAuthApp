package com.example.noteappwithauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.noteappwithauthentication.preferences.AuthViewModel
import com.example.noteappwithauthentication.ui.NoteApp
import com.example.noteappwithauthentication.ui.theme.NoteAppWithAuthenticationTheme

class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModel.Factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
            !authViewModel.isLoading.value
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

