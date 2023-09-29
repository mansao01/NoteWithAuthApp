package com.example.noteappwithauthentication.preferences

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.noteappwithauthentication.NoteApplication
import com.example.noteappwithauthentication.ui.navigation.Screen
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authTokenManager: AuthTokenManager
):ViewModel() {
    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Login.route)
    val startDestination: State<String> = _startDestination

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            authTokenManager.getIsLoginState().collect { isLogin ->
                if (isLogin) {
                    _startDestination.value = Screen.Home.route
                } else {
                    _startDestination.value = Screen.Login.route

                }
            }
            _isLoading.value = false
        }
    }

    fun saveIsLoginState(isLogin: Boolean) {
        viewModelScope.launch {
            authTokenManager.saveIsLoginState(isLogin)
        }
    }

//    suspend fun getAccessToken() {
//        authTokenManager.getAccessTokenFlow().collect { token ->
//            _accessToken.value = token
//        }
//    }

    fun removeAccessToken() {
        viewModelScope.launch {
            authTokenManager.clearTokens()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as  NoteApplication)
                val authTokenManager = application.authTokenManager
                AuthViewModel(authTokenManager = authTokenManager)
            }
        }
    }
}