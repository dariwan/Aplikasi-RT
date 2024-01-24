package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kegiatan(
    var id: String? = null,
    val topik: String? = null,
    val tempat: String? = null,
    val deskripsi: String? = null,
) : Parcelable
