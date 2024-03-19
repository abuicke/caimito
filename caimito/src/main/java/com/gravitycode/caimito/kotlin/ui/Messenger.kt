package com.gravitycode.caimito.kotlin.ui

import androidx.compose.material3.SnackbarDuration
import com.gravitycode.caimito.kotlin.ui.android.Snackbar
import com.gravitycode.caimito.kotlin.ui.android.ToastDuration

interface Messenger {

    fun showToast(message: String, duration: ToastDuration = ToastDuration.SHORT)

    fun showSnackbar(snackbar: Snackbar)

    fun showSnackbar(message: String, duration: SnackbarDuration = SnackbarDuration.Short)
}