package com.example.noteappwithauthentication.data.network.request

data class CreateNoteRequest(
    val title: String,
    val description: String,
    val userId: String
)
