package com.example.noteappwithauthentication.data.network

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("v1/register")
    suspend fun register(
      @Body  registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("v1/register")
    suspend fun login(
       @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("v1/profile")
    suspend fun profile(
        @Header("Authorization")
        token: String
    ): GetProfileResponse

    @POST("v1/logout")
    suspend fun logout(
       @Body logoutRequest: LogoutRequest
    ): LogoutResponse

    @GET("v1/notes/{id}")
    suspend fun getNotesById(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int
    ): GetNotesByIdResponse

    @POST("v1/note")
    suspend fun createNote(
        @Header("Authorization")
        token: String,
      @Body  createNoteRequest: CreateNoteRequest
    ): CreateNoteReponse

    @POST("v1/note/{id}")
    suspend fun deleteNote(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int
    ): DeleteNoteResponse
}