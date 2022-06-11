package com.darealreally.balloony.ui.splash

import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darealreally.balloony.R
import com.darealreally.balloony.data.Balloon
import com.darealreally.balloony.ui.ScreenContent
import com.darealreally.balloony.ui.StatelessApp
import com.darealreally.balloony.ui.balloon.BalloonTop
import com.darealreally.balloony.ui.theme.*

@Composable
fun SplashScreen(
    setContent: (ScreenContent) -> Unit
) {
    // Props
    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Blue, LightBlue),
                    start = Offset(Float.POSITIVE_INFINITY, 0F),
                    end = Offset(0F, Float.POSITIVE_INFINITY)
                )
            ),
        contentAlignment = Alignment.BottomCenter
    ) {

        // Layer 1: TITLE
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // Row 1:
            GradientTitle(
                modifier = Modifier
                    .padding(top = (screenHeightDp * 0.2).dp),
                setContent = setContent
            )

            // Row 2:
            Text(
                text = "Gradient Colored Balloons",
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontFamily = Quicksand,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(start = 50.dp, top = 7.dp)
            )
        }

        // Layer 2: Balloon Circling
        BalloonCircling(
            modifier = Modifier.offset(x = 50.dp, y = 180.dp)
        )
    }

}

@Composable
fun GradientTitle(
    modifier: Modifier = Modifier,
    setContent: (ScreenContent) -> Unit = {}
) {
    // Props
    val appName = stringResource(id = R.string.app_name)
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val screenWidthPx = with(LocalDensity.current) { screenWidthDp.dp.toPx() }
    val textBgWidthDp = 1771 * 2
    val textBgHeightDp = 90
    val textBgWidthPx = with(LocalDensity.current) { textBgWidthDp.dp.toPx() }

    val assetManager = LocalContext.current.assets
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = with(LocalDensity.current) { 64.sp.toPx() }
        color = Color.White.toArgb()
        typeface = Typeface.createFromAsset(assetManager, "quicksand_semi_bold.ttf")
    }

    // State
    val textBgXOffset = remember {
        Animatable(
            initialValue = (textBgWidthPx / 2F) - screenWidthPx,
        )
    }

    // Side Effect
    LaunchedEffect(Unit) {
        textBgXOffset.animateTo(
            targetValue = -screenWidthPx,
            animationSpec = repeatable(
                iterations = 6, // each iteration includes 1 revere
                repeatMode = RepeatMode.Reverse,
                animation = tween(durationMillis = 1500)
            )
        ) {
            // display main screen after animation is finished
            if (value == -1078.0F) {
                setContent(ScreenContent.Main)
            }
        }
    }

    Canvas(
        modifier = modifier
            .requiredSize(textBgWidthDp.dp, textBgHeightDp.dp)
            .graphicsLayer(alpha = 0.99F) // for mask to work
    ) {

        // APP NAME
        drawContext.canvas.nativeCanvas.drawText(
            appName,
            (size.width / 2) - 78,
            180F,
            paint
        )

        // GRADIENT BACKGROUND
        val startX = textBgXOffset.value // start left edge of screen
        drawRect(
            topLeft = Offset(startX, 0F),
            size = Size(size.width, size.height),
            brush = Brush.horizontalGradient(
                colors = listOf(
                    ColorCombinations.combination4.second,
                    ColorCombinations.combination4.first,
                    ColorCombinations.combination5.second,
                    ColorCombinations.combination1.second,
                    ColorCombinations.combination1.first,
                    ColorCombinations.combination5.first,
                    ColorCombinations.combination2.second,
                ),
                startX = startX,
                endX = size.width
            ),
            blendMode = BlendMode.SrcAtop // this will mask the text
        )

    }
}

@Composable
fun BalloonCircling(
    modifier: Modifier = Modifier,
    balloons: List<Balloon> = StatelessApp().balloons
) {
    // Props
    val primaryColor = MaterialTheme.colors.primary

    // UI
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {

        // Layer 1: DOTTED STRINGS
        Canvas(
            modifier = Modifier
                .size(492.dp)
        ) {
            val dotLength = 10F.dp.toPx()
            val spaceLength = 10F.dp.toPx()
            val strokeWidth = 1F.dp.toPx()

            val radius = size.width / 2
            val smallRadius = 0.6F * radius

            val drawCircle: (radius: Float) -> Unit = {
                drawCircle(
                    color = primaryColor.copy(alpha = 0.2F),
                    center = size.center,
                    radius = it,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(dotLength, spaceLength)
                        )
                    )
                )
            }

            drawCircle(radius)

            translate(
                left = (radius - smallRadius) * 0.7F,
                top = (radius - smallRadius) * 0.7F
            ) {
                drawCircle(smallRadius)
            }

        } //: Canvas

        // Layer 2: Balloons
        BalloonTop(
            gradientColors = balloons[4].gradientColors,
            length = balloons[4].topViewLength,
            modifier = Modifier.offset(x = -(130).dp, y = -(350F).dp)
        )

        BalloonTop(
            gradientColors = balloons[1].gradientColors,
            length = balloons[1].topViewLength,
            modifier = Modifier.offset(x = -(200).dp, y = -(100).dp)
        )

        BalloonTop(
            gradientColors = balloons[3].gradientColors,
            length = balloons[3].topViewLength,
            modifier = Modifier.offset(x = -(10).dp, y = -(420).dp)
        )

        BalloonTop(
            gradientColors = balloons[0].gradientColors,
            length = balloons[0].topViewLength,
            modifier = Modifier.offset(x = (120).dp, y = -(360).dp)
        )

        BalloonTop(
            gradientColors = balloons[2].gradientColors,
            length = balloons[2].topViewLength,
            modifier = Modifier.offset(x = (10).dp, y = -(225).dp)
        )

    }
}



/**
 * Preview Section
 */
@Preview
@Composable
fun SplashScreenPreview() {
    BalloonyTheme {
        SplashScreen(setContent = {})
    }
}

@Preview
@Composable
fun GradientTitlePreview() {
    BalloonyTheme {
        GradientTitle()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun BalloonCirclingPreview() {
    BalloonyTheme {
        BalloonCircling()
    }
}
