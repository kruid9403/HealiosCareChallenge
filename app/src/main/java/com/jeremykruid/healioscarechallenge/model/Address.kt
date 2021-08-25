package com.jeremykruid.healioscarechallenge.model


import com.google.gson.annotations.SerializedName

data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: Geo?
)