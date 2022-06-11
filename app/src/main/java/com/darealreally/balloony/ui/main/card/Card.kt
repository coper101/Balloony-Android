package com.darealreally.balloony.ui.main.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colors.primary.copy(alpha = 0.05F),
                shape = RoundedCornerShape(15.dp)
            )
    ) {

        // Layer 1: BACKGROUND BLUR
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.1F),
                )
                .blur(10.dp)
        )

        // Layer 2: CONTENT:
        content()
    }
}

@Composable
fun CardDivider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .then(modifier)
            .height(0.5.dp)
            .background(
                color = MaterialTheme.colors.primary.copy(alpha = 0.1F)
            )
    )
}