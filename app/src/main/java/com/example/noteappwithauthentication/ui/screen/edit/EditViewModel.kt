package com.example.noteappwithauthentication.ui.screen.edit

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
import com.example.noteappwithauthentication.data.network.request.UpdateNoteRequest
import com.example.noteappwithauthentication.preferences.AuthTokenManager
import com.example.noteappwithauthentication.ui.common.EditUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EditViewModel(
    private val noteRepository: NoteRepository,
    private val authTokenManager: AuthTokenManager
) : ViewModel() {

    var uiState: EditUiState by mutableStateOf(EditUiState.Loading)
        private set

    fun getDetailNote(noteId: Int) {
        viewModelScope.launch {
            val localToken = authTokenManager.getAccessToken()
            uiState = EditUiState.Loading
            uiState = try {
                val result = noteRepository.getDetailNoteById("Bearer $localToken", noteId)
                EditUiState.StandBy(result)
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
                EditUiState.ErrorGetNoteDetail(errorMessage)
            }
        }
    }

    fun updateNote(
        id: Int,
        updateNoteRequest: UpdateNoteRequest
    ) {
        viewModelScope.launch {
            val localToken = authTokenManager.getAccessToken()
            uiState = EditUiState.Loading
            uiState = try {
                val result = noteRepository.updateNote("Bearer $localToken", id, updateNoteRequest)
                EditUiState.Success(result)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is IOException -> "Network error occurred"
                    is HttpException -> {
                        when (e.code()) {
                            400 -> e.response()?.errorBody()?.string().toString()
                            401 -> e.response()?.errorBody()?.string().toString()
                            // Add more cases for specific HTTP error codes if needed
                            else -> "HTTP error: ${e.code()}"
                        }
                    }

                    else -> "An unexpected error occurred"
                }
                EditUiState.Error(errorMessage)
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
                EditViewModel(noteRepository = noteRepository, authTokenManager = authTokenManager)
            }
        }
    }
}