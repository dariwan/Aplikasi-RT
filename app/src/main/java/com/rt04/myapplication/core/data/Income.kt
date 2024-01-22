package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Income(
    val jumlah : Double? = null,
    val nama : String? = null,
    val tanggal : String? = null,
): Parcelable
