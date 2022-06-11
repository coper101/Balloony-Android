package com.darealreally.balloony.ui.main

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.darealreally.balloony.MockGraph
import com.darealreally.balloony.R
import com.darealreally.balloony.data.Balloon
import com.darealreally.balloony.ui.balloon.BalloonFront
import com.darealreally.balloony.ui.theme.BalloonyTheme
import kotlin.math.roundToInt

@Composable
fun BalloonList(
    modifier: Modifier = Modifier,
    balloons: List<Balloon> = MockGraph.balloons,
    setSelectedBalloonIdx: (Int) -> Unit = {}
) {
    // Props
    val blockWidth =
        dimensionResource(id = R.dimen.balloon_max_width).value +
        (dimensionResource(id = R.dimen.balloon_hor_padding).value * 2)

    val blockHeight = (
            dimensionResource(id = R.dimen.balloon_max_height).value + 17F + 52F) + 80F

    // State
    val scrollState = rememberScrollState()
    val xOffset = scrollState.value
    val maxXOffset = scrollState.maxValue

    val interactions = remember { mutableStateListOf<Interaction>() }
    val interactionSource = scrollState.interactionSource
    val dragState = interactionSource.collectIsDraggedAsState()
    val isDragged = dragState.value

    // Functions
    val getItemMid: (i: Int) -> Float = { i ->
        ( (i + 1F) + i ) / 10F
    }
    val getItemRange: (
        i: Int,
        maxX: Int
    ) -> IntRange = { i, maxX ->
        val mid = getItemMid(i)
        val fromFra = if (i == 0) 0F else mid - 0.1F
        val toFra = if (i == balloons.lastIndex) mid + 1F else mid + 0.09F
        val from = fromFra * maxX
        val to = toFra * maxX
        val range = from.toInt()..to.toInt()

//        Log.d("Balloon List",
//            """
//                > Info
//                max X Offset: $maxX
//                mid: $mid
//                fromFra: $fromFra
//                toFra: $toFra
//                from: $from
//                to: $to
//                range: $range
//
//                """.trimIndent()
//        )

        range
    }

    // Side Effect
    LaunchedEffect(Unit) {
        // focus to center balloon
        scrollState.animateScrollTo((scrollState.maxValue * 0.5).roundToInt())
        setSelectedBalloonIdx(2)
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect {
            when (it) {
                is DragInteraction.Stop -> {
                    interactions.add(it)
                }
            }
        }
    }

    LaunchedEffect(isDragged) {
        interactions.firstOrNull() ?: return@LaunchedEffect
        Log.d("Balloon List", "interactions.firstOrNull(): has Dragged")

        // set focused balloon when finished scrolling
        balloons.forEachIndexed { i, _ ->
            // find which range is the current scroll x offset
            getItemRange(i, maxXOffset)
                .contains(xOffset)
                .also { inRange ->
                    if (inRange) {
                        // scroll to this balloon's mid
                        val mid = (getItemMid(i) * maxXOffset).toInt()
                        scrollState.animateScrollTo(
                            value = mid,
                            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                        )
                        setSelectedBalloonIdx(i)
                        return@LaunchedEffect
                    }
                }
        } //: forEach
    }

    // UI
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        verticalAlignment = Alignment.Top
    ) {

        // additional content block to center the first balloon
        BlockSpacer(blockWidth)

        balloons.forEachIndexed { index, balloon ->

            val range = getItemRange(index, maxXOffset)
            val isFocused = range.contains(xOffset)

            val size =
                if (isFocused) balloon.frontViewSizeBig
                else balloon.frontViewSizeSmall

            val alpha: Float by animateFloatAsState(
                if (isFocused) 1F else 0.5F
            )
            val topPadding: Dp by animateDpAsState(
                if (isFocused) 65.dp else 1.dp
            )

            Box(
                modifier = Modifier
                    .padding(top = topPadding)
                    .height(blockHeight.dp)
                    .alpha(alpha)
            ) {
                BalloonFront(
                    gradientColors = balloon.gradientColors,
                    size = size,
                    requiredWidth = blockWidth
                )
            }
        }

        // additional content block to center the last balloon
        BlockSpacer(blockWidth)
    }
}

@Composable
fun BlockSpacer(
    blockWidth: Float
) =
    Box(
        modifier = Modifier
            .requiredWidth(blockWidth.dp)
            .background(Color.Transparent)
    )




/**
 * Preview Section
 */
@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun BalloonListPreview() {
    BalloonyTheme {
        BalloonList()
    }
}