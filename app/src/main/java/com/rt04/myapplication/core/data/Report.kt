package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    var id: String? = null,
    val topik: String? = null,
    val masalah: String? = null,
    val nama: String? = null,
    val kategori: String? = null,
    val image: String? = null,
) : Parcelable
