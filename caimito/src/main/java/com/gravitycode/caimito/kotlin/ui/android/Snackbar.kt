package com.gravitycode.caimito.kotlin.ui.android

import androidx.compose.material3.SnackbarDuration

data class Snackbar(
    val message: String,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val text: String,
    val onClick: () -> Unit
)