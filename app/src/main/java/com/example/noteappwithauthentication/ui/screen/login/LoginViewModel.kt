package com.example.noteappwithauthentication.ui.screen.login

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
import com.example.noteappwithauthentication.data.network.request.LoginRequest
import com.example.noteappwithauthentication.preferences.AuthTokenManager
import com.example.noteappwithauthentication.ui.common.LoginUiState
import com.example.noteappwithauthentication.ui.screen.register.RegisterViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val noteRepository: NoteRepository,
    private val authTokenManager: AuthTokenManager
):ViewModel() {


    var uiState: LoginUiState by mutableStateOf(LoginUiState.StandBy)
        private set

    fun getUiState() {
        uiState = LoginUiState.StandBy
    }

    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            uiState = LoginUiState.Loading
            uiState = try {
                val result = noteRepository.login(loginRequest)
                result.accessToken.let { authTokenManager.saveAccessToken(it) }
                result.refreshToken.let { authTokenManager.saveRefreshToken(it) }
                authTokenManager.saveIsLoginState(true)
                LoginUiState.Success(result)

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
                LoginUiState.Error(errorMessage)
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
                LoginViewModel(noteRepository = noteRepository, authTokenManager = authTokenManager)
            }
        }
    }
}