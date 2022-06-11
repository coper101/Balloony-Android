package com.darealreally.balloony.ui.main

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.darealreally.balloony.MockGraph
import com.darealreally.balloony.R
import com.darealreally.balloony.ui.main.card.ItemCard
import com.darealreally.balloony.ui.main.card.ReviewCard
import com.darealreally.balloony.ui.theme.BalloonyTheme
import com.darealreally.balloony.ui.theme.Blue
import com.darealreally.balloony.ui.theme.LightBlue
import com.darealreally.balloony.ui.theme.Quicksand
import com.google.accompanist.insets.LocalWindowInsets
import kotlin.math.roundToInt

@Composable
fun MainScreen(
   mainUiState: MainUiState = MainUiState(MockGraph.balloons)
) {
    // Props
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val screenHeightPx = with(LocalDensity.current) { screenHeightDp.dp.toPx() }

    val navBarHeightDp = dimensionResource(id = R.dimen.nav_bar)
    val navBarHeightPx = with(LocalDensity.current) { navBarHeightDp.toPx() }
    val balloonListHeightPx = with(LocalDensity.current) { (177F + 17F + 52F + 80F).dp.toPx() }

    val topInsetPx = LocalWindowInsets.current.systemBars.top
    val paddingTopPx = with(LocalDensity.current) { 50.dp.toPx() }

    val balloonMaxYOffset = navBarHeightPx + paddingTopPx + topInsetPx
    val cardMaxYOffset =  balloonMaxYOffset + balloonListHeightPx

    val topInsetDp = with(LocalDensity.current) {
        LocalWindowInsets.current.systemBars.top.toDp() - 15.dp
    }

    // State
    var selectedBalloonIdx by remember { mutableStateOf(0) }
    var startAnimating by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = startAnimating, label = "start")
    var yOffsetContent by remember { mutableStateOf(0F) }

    // Animations
    val balloonYOffset = transition.animateFloat(
        label = "balloons",
        transitionSpec = { tween(durationMillis = 1500) }
    ) {
        if (it) balloonMaxYOffset
        else screenHeightPx * 0.95F
    }

    val cardYOffset = transition.animateFloat(
        label = "cards",
        transitionSpec = { tween(durationMillis = 1500) }
    ) {
        if (it) cardMaxYOffset
        else screenHeightPx * 0.7F
    }

    // Side Effect
    LaunchedEffect(Unit) {
        startAnimating = true
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    0F to Blue,
                    0.4F to LightBlue,
                    1F to Blue,
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        // Layer 1: APP BAR
        NavigationBar(
            modifier = Modifier
                .padding(top = topInsetDp)
                .zIndex(1F),
            height = navBarHeightDp
        )

        // Layer 2: CONTENT
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0F)
                .offset {
                    IntOffset(
                        x = 0,
                        y = yOffsetContent.roundToInt()
                    )
                }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { amountChanged ->
                        val newYOffsetContent = yOffsetContent + amountChanged

                        // prevent from going beyond screen bounds
                        val range = -balloonListHeightPx..0F
                        if (newYOffsetContent in range) {
                            yOffsetContent = newYOffsetContent
                        }
                    }
                ),
        ) {
            // Layer 1: BALLOONS
            BalloonList(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = 0,
                            y = balloonYOffset.value.roundToInt()
                        )
                    },
                balloons = mainUiState.balloons,
                setSelectedBalloonIdx = { selectedBalloonIdx = it }
            )

            // Layer 2: CARDS
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = cardYOffset.value.roundToInt()
                        )
                    },
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // Row 1:
                ItemCard(
                    balloon = mainUiState.balloons[selectedBalloonIdx]
                )
                // Row 2:
                ReviewCard()
            }
        }
    }
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    height: Dp = dimensionResource(id = R.dimen.nav_bar)
) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Col 1: MENU
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = {}),
            tint = MaterialTheme.colors.primary
        )

        // Col 2: APP NAME
        Text(
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.Medium,
            fontFamily = Quicksand,
            fontSize = 30.sp,
            color = MaterialTheme.colors.primary
        )

        // Col 3: SEARCH
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = {}),
            tint = MaterialTheme.colors.primary
        )

    } //: Row
}



/**
 * Preview Section
 */
@Preview
@Composable
fun MainScreenPreview() {
    BalloonyTheme {
        MainScreen()
    }
}