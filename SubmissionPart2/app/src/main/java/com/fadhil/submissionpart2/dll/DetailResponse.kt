package com.fadhil.submissionpart2.dll

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("followers_url")
    val followers_url: String,

    @field:SerializedName("following_url")
    val following_url: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("bio")
    val bio: String




)
