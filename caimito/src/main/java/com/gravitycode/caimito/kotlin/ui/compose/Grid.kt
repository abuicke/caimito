package com.gravitycode.caimito.kotlin.ui.compose

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
@Preview(showSystemUi = true)
private fun GridPreview() {
    Grid(
        modifier = Modifier.fillMaxSize(),
        columns = 3,
        items = 9,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) { index ->
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = index.toString()
        )
    }
}

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    columns: Int,
    items: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    useDividers: Boolean = true,
    dividerColour: Color = DividerDefaults.color,
    content: @Composable BoxScope.(index: Int) -> Unit
) {
    Column(modifier = modifier) {
        val rows = calculateGridRows(columns, items)

        for (row in 0 until rows) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment
            ) {
                for (column in 0 until columns) {
                    val index = (row * columns) + column
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        if (index < items) {
                            content(index)
                        }
                    }
                    if (useDividers) {
                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp),
                            color = dividerColour
                        )
                    }
                }
            }
            if (useDividers) {
                HorizontalDivider(color = dividerColour)
            }
        }
    }
}

/**
 * TODO: Replace [require] with [com.gravitycode.caimito.kotlin.core.require]
 * */
@VisibleForTesting
internal fun calculateGridRows(columns: Int, items: Int): Int {
    require(columns > 0) { "columns cannot be less than one" }
    require(items > 0) { "items cannot be less than one" }
    return ceil(items.toDouble() / columns.toDouble()).toInt()
}