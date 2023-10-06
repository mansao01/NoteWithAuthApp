package com.example.noteappwithauthentication.ui.screen.add

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteappwithauthentication.NoteApplication
import com.example.noteappwithauthentication.data.NoteRepository
import com.example.noteappwithauthentication.data.network.request.CreateNoteRequest
import com.example.noteappwithauthentication.preferences.AuthTokenManager
import com.example.noteappwithauthentication.ui.common.AddUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AddViewModel(
    private val noteRepository: NoteRepository,
    private val authTokenManager: AuthTokenManager

) : ViewModel() {
    var uiState: AddUiState by mutableStateOf(AddUiState.StandBy)
        private set
    fun getUiState(){
        uiState = AddUiState.StandBy
    }
    fun addNote(noteRequest: CreateNoteRequest) {
        viewModelScope.launch {
            val token = authTokenManager.getAccessToken()
            uiState = AddUiState.Loading
            uiState = try {
                val result = noteRepository.createNote("Bearer $token", createNoteRequest = noteRequest)
                AddUiState.Success(result)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is IOException -> "Network error occurred"
                    is HttpException -> {
                        when (e.code()) {
                            400 -> e.response()?.errorBody()?.string().toString()
                            // Add more cases for specific HTTP error codes if needed
                            else -> "HTTP error: ${e.code()}"
                        }
                    }

                    else -> "An unexpected error occurred"
                }
                AddUiState.Error(errorMessage)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
                val noteRepository = application.container.noteRepository
                val authTokenManager = application.authTokenManager
                AddViewModel(noteRepository = noteRepository, authTokenManager = authTokenManager)
            }
        }
    }
}