package com.example.noteappwithauthentication.data.network.response

import com.google.gson.annotations.SerializedName

data class CreateNoteReponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
