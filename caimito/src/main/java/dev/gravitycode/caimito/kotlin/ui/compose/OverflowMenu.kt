@file:OptIn(ExperimentalComposeUiApi::class)

package dev.gravitycode.caimito.kotlin.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

@Composable
fun OverflowMenu(
    menuItems: List<String>,
    modifier: Modifier = Modifier,
    onMenuItemClicked: (String) -> Unit
) {

    val showMenu = remember { mutableStateOf(false) }

    IconButton(
        modifier = modifier
            .semantics { testTagsAsResourceId = true }
            .testTag("overflow"),
        onClick = { showMenu.value = !showMenu.value }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Overflow icon",
            tint = MaterialTheme.colorScheme.background
        )
    }
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = {
            showMenu.value = false
        }
    ) {
        for (menuItem in menuItems) {
            DropdownMenuItem(
                text = { Text(menuItem) },
                onClick = {
                    showMenu.value = false
                    onMenuItemClicked(menuItem)
                },
                modifier = Modifier.testTag(menuItem.lowercase())
            )
        }
    }
}