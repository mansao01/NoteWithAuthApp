package com.example.noteappwithauthentication.data

import com.example.noteappwithauthentication.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val noteRepository: NoteRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.18.161:8000/"


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override val noteRepository: NoteRepository by lazy {
        NetworkNoteRepository(retrofitService)
    }
}