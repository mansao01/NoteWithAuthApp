package com.example.noteappwithauthentication.ui.screen.home

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
import com.example.noteappwithauthentication.preferences.AuthTokenManager
import com.example.noteappwithauthentication.ui.common.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val noteRepository: NoteRepository,
    private val authTokenManager: AuthTokenManager
):ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set


    init {
        getNotesAndProfile()
    }
    private fun getNotesAndProfile() {
        viewModelScope.launch {
            val localToken  = authTokenManager.getAccessToken()
            val userId = authTokenManager.getUserId()
            uiState = HomeUiState.Loading
            uiState = try {
                val result = noteRepository.getNotesById("Bearer $localToken", userId!!)
                val resultProfile = noteRepository.profile("Bearer $localToken")
                HomeUiState.Success(result.data, resultProfile, localToken !!)
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
                HomeUiState.Error(errorMessage)
            }
        }
    }
    fun removeAccessToken() {
        uiState = HomeUiState.Loading
        viewModelScope.launch {
            authTokenManager.clearTokens()
        }
    }

    fun saveIsLoginState(isLogin: Boolean) {
        uiState = HomeUiState.Loading
        viewModelScope.launch {
            authTokenManager.saveIsLoginState(isLogin)
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NoteApplication)
                val noteRepository = application.container.noteRepository
                val authTokenManager = application.authTokenManager
                HomeViewModel(noteRepository = noteRepository, authTokenManager = authTokenManager)
            }
        }
    }
}