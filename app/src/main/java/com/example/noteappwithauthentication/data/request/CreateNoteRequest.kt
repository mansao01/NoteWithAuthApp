package com.example.noteappwithauthentication.data.request

data class CreateNoteRequest(
    val title: String,
    val description: String,
    val userId: String
)
