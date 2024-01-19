package com.rt04.myapplication.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Username(
    var name: String? = null
): Parcelable
