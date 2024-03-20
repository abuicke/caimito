package dev.gravitycode.caimito.kotlin.ui.android

import android.content.Context
import android.widget.Toast

interface Toaster {

    companion object {

        fun create(context: Context): Toaster = ToasterImpl(context)
    }

    fun toast(text: String, duration: ToastDuration = ToastDuration.SHORT)
}

private class ToasterImpl(private val context: Context) : Toaster {

    override fun toast(text: String, duration: ToastDuration) {
        Toast.makeText(context, text, duration.toInt()).show()
    }
}