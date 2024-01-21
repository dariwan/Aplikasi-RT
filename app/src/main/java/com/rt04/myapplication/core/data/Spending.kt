package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Spending(
    val jumlah : String? = null,
    val nama : String? = null,
    val tanggal : String? = null,
): Parcelable
