package com.example.noteappwithauthentication.data.response

import com.google.gson.annotations.SerializedName

data class DeleteNoteResponse(
    @field:SerializedName("msg")
    val msg: String
)
