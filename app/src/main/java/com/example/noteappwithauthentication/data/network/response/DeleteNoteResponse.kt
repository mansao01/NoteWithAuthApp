package com.example.noteappwithauthentication.data.network.response

import com.google.gson.annotations.SerializedName

data class DeleteNoteResponse(
    @field:SerializedName("msg")
    val msg: String
)
