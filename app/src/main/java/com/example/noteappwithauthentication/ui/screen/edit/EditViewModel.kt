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
import com.example.noteappwithauthentication.ui.common.EditUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EditViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var uiState: EditUiState by mutableStateOf(EditUiState.StandBy)
        private set

    fun getUiState() {
        uiState = EditUiState.StandBy
    }

    fun updateNote(
        token: String,
        id: Int,
        updateNoteRequest: UpdateNoteRequest
    ) {
        viewModelScope.launch {
            uiState = EditUiState.Loading
            uiState = try {
                val result = noteRepository.updateNote(token, id, updateNoteRequest)
                EditUiState.Success(result)
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
                EditViewModel(noteRepository = noteRepository)
            }
        }
    }
}