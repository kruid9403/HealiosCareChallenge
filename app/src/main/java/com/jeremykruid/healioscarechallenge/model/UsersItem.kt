package com.jeremykruid.healioscarechallenge.model


import com.google.gson.annotations.SerializedName

data class UsersItem(
    val id: Int?,
    val name: String?,
    val username: String?,
    val email: String?,
    val address: Address?,
    val phone: String?,
    val website: String?,
    val company: Company?
)