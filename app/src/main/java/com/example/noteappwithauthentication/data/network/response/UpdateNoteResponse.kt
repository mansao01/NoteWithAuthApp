package com.example.noteappwithauthentication.data.network.response

import com.google.gson.annotations.SerializedName

data class UpdateNoteResponse(

	@field:SerializedName("msg")
	val msg: String
)
