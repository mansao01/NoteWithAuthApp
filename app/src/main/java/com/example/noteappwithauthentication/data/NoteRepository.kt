package com.example.noteappwithauthentication.data

import com.example.noteappwithauthentication.data.network.ApiService
import com.example.noteappwithauthentication.data.network.request.CreateNoteRequest
import com.example.noteappwithauthentication.data.network.request.LoginRequest
import com.example.noteappwithauthentication.data.network.request.LogoutRequest
import com.example.noteappwithauthentication.data.network.request.RegisterRequest
import com.example.noteappwithauthentication.data.network.response.CreateNoteReponse
import com.example.noteappwithauthentication.data.network.response.DeleteNoteResponse
import com.example.noteappwithauthentication.data.network.response.GetNotesByIdResponse
import com.example.noteappwithauthentication.data.network.response.GetProfileResponse
import com.example.noteappwithauthentication.data.network.response.LoginResponse
import com.example.noteappwithauthentication.data.network.response.LogoutResponse
import com.example.noteappwithauthentication.data.network.response.RegisterResponse

interface NoteRepository {
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse

    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun profile(token: String): GetProfileResponse

    suspend fun logout(logoutRequest: LogoutRequest): LogoutResponse

    suspend fun getNotesById(token: String, id: Int): GetNotesByIdResponse

    suspend fun createNote(token: String, createNoteRequest: CreateNoteRequest): CreateNoteReponse

    suspend fun deleteNote(token: String, id: Int): DeleteNoteResponse
}

class NetworkNoteRepository(
    private val apiService: ApiService
) : NoteRepository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        apiService.register(registerRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        apiService.login(loginRequest)

    override suspend fun profile(token: String): GetProfileResponse = apiService.profile(token)
    override suspend fun logout(logoutRequest: LogoutRequest) = apiService.logout(logoutRequest)

    override suspend fun getNotesById(token: String, id: Int): GetNotesByIdResponse =
        apiService.getNotesById(token, id)

    override suspend fun createNote(
        token: String,
        createNoteRequest: CreateNoteRequest
    ): CreateNoteReponse =
        apiService.createNote(token, createNoteRequest)

    override suspend fun deleteNote(token: String, id: Int): DeleteNoteResponse =
        apiService.deleteNote(token, id)
}