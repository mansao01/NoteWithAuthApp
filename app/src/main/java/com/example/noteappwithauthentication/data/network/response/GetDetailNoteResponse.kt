package com.example.noteappwithauthentication.data.network.response

import com.google.gson.annotations.SerializedName

data class GetDetailNoteResponse(

	@field:SerializedName("data")
	val data: DataDetailNote
)

data class DataDetailNote(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
