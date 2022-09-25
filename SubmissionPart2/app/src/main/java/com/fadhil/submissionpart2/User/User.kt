package com.fadhil.submissionpart2.User

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    val login: String,


    val url: String,


    val avatar_url: String
): Parcelable
