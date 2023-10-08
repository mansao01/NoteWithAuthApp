package com.example.noteappwithauthentication.data.network.request

data class UpdateNoteRequest(
    val title:String,
    val description:String,
    val userId:Int
)
