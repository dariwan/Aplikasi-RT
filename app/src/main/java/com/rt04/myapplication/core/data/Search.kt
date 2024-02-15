package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(
    val tittle: String,
    val link: String,
    val adress: String,
    val phone_number: String
): Parcelable
