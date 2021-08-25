package com.jeremykruid.healioscarechallenge.model


import com.google.gson.annotations.SerializedName

data class CommentItem(
    val postId: Int?,
    val id: Int?,
    val name: String?,
    val email: String?,
    val body: String?
)