package com.gravitycode.caimito.kotlin.ui.android

import android.widget.Toast

enum class ToastDuration(private val int: Int) {

    SHORT(Toast.LENGTH_SHORT),

    LONG(Toast.LENGTH_LONG);

    fun toInt() = int
}