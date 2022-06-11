package com.darealreally.balloony.ui.balloon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.darealreally.balloony.MockGraph
import com.darealreally.balloony.R
import com.darealreally.balloony.ui.theme.BalloonyTheme

@Composable
fun BalloonTop(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = MockGraph.balloons[0].gradientColors,
    length: Float = MockGraph.balloons[0].topViewLength
) {
    // Props
    val lengthPx = with(LocalDensity.current) { length.dp.toPx() }

    // UI
    Box(
        modifier = modifier
            .requiredSize(length.dp)
            .clip(CircleShape)
    ) {

        // Layer 1: SOLID
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(gradientColors)
                )
        )

        // Layer 2: SHINE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary.copy(alpha = 0.6F),
                            MaterialTheme.colors.primary.copy(alpha = 0F)
                        ),
                        center = Offset(lengthPx / 3, lengthPx / 3),
                        radius = lengthPx * 0.7F
                    )
                )
        )

    } //: Box
}

@Composable
fun BalloonFront(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = MockGraph.balloons[0].gradientColors,
    size: Size = MockGraph.balloons[0].frontViewSizeBig,
    requiredWidth: Float = size.width
) {
    // Props
    val tieSize = Size(
        dimensionResource(id = R.dimen.tie_width).value,
        dimensionResource(id = R.dimen.tie_height).value
    )
    val stringSize = Size(
        dimensionResource(id = R.dimen.string_width).value,
        dimensionResource(id = R.dimen.string_height).value,
    )

    // UI
    Box(
        modifier = modifier
            .requiredHeight(
                (size.height + tieSize.height + stringSize.height).dp
            )
            .requiredWidth(requiredWidth.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Layer 1: BALLOON
        Balloon(
            modifier = Modifier
                .zIndex(1F),
            gradientColors = gradientColors,
            size = size
        )

        // Layer 2: TIE
        Tie(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(stringSize.height + 7).dp)
                .zIndex(2F),
            color = gradientColors[1],
            size = tieSize
        )

        // Layer 3: String
        String(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(10).dp)
                .zIndex(0F),
            size = stringSize
        )
        
    }
}

@Composable
fun String(
    modifier: Modifier = Modifier,
    size: Size,
) {
    // Props
    val (width, height) = size
    val primaryColor = MaterialTheme.colors.primary

    // UI
    Canvas(
        modifier = modifier
            .size(width.dp, height.dp)
    ) {
        val (widthPx, heightPx) = this.size

        val mid = widthPx / 2
        val p1 = Offset(mid, 0F)
        val p2 = Offset(widthPx, 0.25F * heightPx)
        val p3 = Offset(0F, 0.75F * heightPx)
        val p4 = Offset(widthPx, heightPx)

        val stringPath = Path().apply {
            moveTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
            lineTo(p4.x, p4.y)
        }

        drawPath(
            path = stringPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    primaryColor,
                    primaryColor.copy(alpha = 0.1F)
                ),
            ),
            style = Stroke(
                width = 2F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.cornerPathEffect(radius = 30F)
            )
        )

    }
}

@Composable
fun Balloon(
    modifier: Modifier = Modifier,
    gradientColors: List<Color>,
    size: Size
) {
    // Props
    val (width, height) = size
    val primaryColor = MaterialTheme.colors.primary

    // UI
    Canvas(
        modifier = Modifier
            .size(width.dp, height.dp)
            .then(modifier)
    ) {
        val (widthPx, heightPx) = this.size
        // Layer 1: SOLID
        drawOval(
            brush = Brush.verticalGradient(gradientColors),
            size = this.size
        )

        // Layer 2: SHINE
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.7F),
                    primaryColor.copy(alpha = 0F)
                ),
                center = Offset(widthPx / 3, heightPx / 4),
                radius = widthPx * 0.7F
            ),
            size = this.size
        )
    } //: Canvas
}

@Composable
fun Tie(
    modifier: Modifier = Modifier,
    size: Size,
    color: Color
) {
    // Props
    val (width, height) = size
    val primaryColor = MaterialTheme.colors.primary

    // UI
    Canvas(
        modifier = modifier
            .size(width.dp, height.dp)
    ) {
        val (widthPx, heightPx) = this.size

        // Blowing Tube
        val trianglePath = Path().apply {
            val mid = widthPx / 2
            val p1 = Offset(mid, 0F)
            val p2 = Offset(0F, heightPx)
            val p3 = Offset(widthPx, heightPx)
            moveTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
            lineTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
        }

        // Tie
        val linePath = Path().apply {
            val x = 0.26F
            val y = 0.45F * heightPx
            val p1 = Offset(x * widthPx, y)
            val p2 = Offset(widthPx * 0.5F, 0.52F * heightPx)
            val p3 = Offset((1 - x) * widthPx, y)
            moveTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
        }

        this.drawIntoCanvas {
            it.drawOutline(
                outline = Outline.Generic(trianglePath),
                Paint().apply {
                    this.color = color
                    style = PaintingStyle.Fill
                    pathEffect = PathEffect.cornerPathEffect(10F)
                }
            )
        }

        drawPath(
            path = linePath,
            color = primaryColor,
            style = Stroke(
                width = 2F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.cornerPathEffect(radius= 50F)
            )
        )
    }
}





/**
 * Preview Section
 */
@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun BalloonTopPreview() {
    BalloonyTheme {
        BalloonTop()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun BalloonFrontPreview() {
    BalloonyTheme {
        BalloonFront()
    }
}
