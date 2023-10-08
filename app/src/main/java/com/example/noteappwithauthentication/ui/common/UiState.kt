package com.example.noteappwithauthentication.ui.common

import com.example.noteappwithauthentication.data.network.response.CreateNoteReponse
import com.example.noteappwithauthentication.data.network.response.GetProfileResponse
import com.example.noteappwithauthentication.data.network.response.LoginResponse
import com.example.noteappwithauthentication.data.network.response.NoteDataItem
import com.example.noteappwithauthentication.data.network.response.RegisterResponse
import com.example.noteappwithauthentication.data.network.response.UpdateNoteResponse

sealed interface RegisterUiState {
    object StandBy : RegisterUiState
    object Loading : RegisterUiState
    data class Success(val registerResponse: RegisterResponse) : RegisterUiState
    data class Error(val msg: String) : RegisterUiState

}

sealed interface LoginUiState {
    object StandBy : LoginUiState
    object Loading : LoginUiState
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    data class Error(val msg: String) : LoginUiState

}

sealed interface HomeUiState {
    object Loading : HomeUiState

    data class Success(
        val noteDataItem: List<NoteDataItem>,
        val getProfileResponse: GetProfileResponse,
        val localToken: String
    ) : HomeUiState

    data class Error(val msg: String) : HomeUiState
}

sealed interface AddUiState {
    object StandBy : AddUiState
    object Loading : AddUiState

    data class Success(val createNoteResponse: CreateNoteReponse) : AddUiState

    data class Error(val msg: String) : AddUiState
}

sealed interface EditUiState {
    object StandBy : EditUiState
    object Loading : EditUiState

    data class Success(val updateNoteResponse: UpdateNoteResponse) : EditUiState

   data class Error(val message:String) : EditUiState
}