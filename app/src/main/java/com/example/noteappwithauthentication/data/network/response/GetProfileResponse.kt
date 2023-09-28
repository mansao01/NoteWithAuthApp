package com.example.noteappwithauthentication.data.network.response

import com.google.gson.annotations.SerializedName

data class GetProfileResponse(

	@field:SerializedName("loggedInId")
	val loggedInId: Int,

	@field:SerializedName("loggedInUserName")
	val loggedInUserName: String,

	@field:SerializedName("loggedInUserEmail")
	val loggedInUserEmail: String
)
