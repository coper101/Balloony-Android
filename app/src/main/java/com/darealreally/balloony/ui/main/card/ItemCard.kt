package com.darealreally.balloony.ui.main.card

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darealreally.balloony.MockGraph
import com.darealreally.balloony.R
import com.darealreally.balloony.Utils.round
import com.darealreally.balloony.data.Balloon
import com.darealreally.balloony.data.Feature
import com.darealreally.balloony.data.features
import com.darealreally.balloony.ui.theme.BalloonyTheme
import com.darealreally.balloony.ui.theme.Quicksand
import com.darealreally.balloony.ui.theme.Red
import kotlinx.coroutines.launch
import com.darealreally.balloony.data.Size as Sizes

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    balloon: Balloon = MockGraph.balloons[0]
) {
    // States
    val scrollState = rememberScrollState()
    var quantity by remember { mutableStateOf(1) }

    // Side Effect
    LaunchedEffect(balloon) {
        Log.d("Item Card", "balloon changed")
        // reset all preferences
        scrollState.animateScrollTo(0)
        quantity = 1
    }

    // UI
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Row 1: HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Col 1: NAME
                AnimatedContent(
                    targetState = balloon.name
                ) { name ->
                    Text(
                        text = name,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Quicksand,
                        fontSize = 30.sp,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }

                // Col 2: SALES BANNER
                SalesBanner()

            }

            CardDivider()

            // Row 2: DESCRIPTION + INPUT
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                // Col 1: SIZE SELECTOR
                SizeSpinner(
                    modifier = Modifier.weight(1F),
                    scrollState = scrollState
                )

                // Col 2: FEATURES
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    features.forEach {
                        FeatureRow(it)
                    }
                }

            }

            // Row 3: INPUTS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // Col 1: QUANTITY STEPPER
                StepperQuantity(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    quantity = quantity,
                    setQuantity = { quantity = it }
                )

                // Col 2: BUY BUTTON
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary,
                        contentColor = MaterialTheme.colors.background
                    )
                ) {
                    AnimatedContent(
                        targetState = balloon.price
                    ) { price ->
                        val totalPrice = price * quantity
                        Text(
                            text = "BUY $${ totalPrice.round(2)}",
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Quicksand,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(0.dp))

        }

    }
}

@Composable
fun SalesBanner() {
    Box(
        modifier = Modifier.size(43.dp, 54.dp)
    ) {

        // Layer 1: BANNER
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val (width, height) = size
            val bannerPath = Path().apply {
                moveTo(0F, 0F)
                lineTo(width, 0F)
                lineTo(width, height)
                lineTo(width * 0.5F, height * 0.8F)
                lineTo(0F, height)
                lineTo(0F, 0F)
            }
            drawPath(bannerPath, Red)
        }

        // Layer 2: TITLE
        Text(
            text = "30% OFF",
            fontWeight = FontWeight.SemiBold,
            fontFamily = Quicksand,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 3.dp)
        )

        // Layer 3: Shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colors.primary.copy(alpha = 0.3F),
                            MaterialTheme.colors.primary.copy(alpha = 0F),
                        )
                    ),
                )
        )

    }
}

@Composable
fun FeatureRow(
    feature: Feature = features[0]
) {
    val (iconId, title) = feature

    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Col 1: ICON
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxHeight()
        )

        Spacer(modifier = Modifier.width(9.dp))

        // Col 2: NAME
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontFamily = Quicksand,
            fontSize = 12.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(bottom = 2.dp)
        )
    }
}

@Composable
fun StepperQuantity(
    modifier: Modifier = Modifier,
    quantity: Int,
    setQuantity: (Int) -> Unit
) {
    // UI
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Col 1: DEDUCT
        IconButton(
            onClick = {
                if (quantity > 0) {
                    setQuantity(quantity - 1)
                }
            },
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = "Deduct",
                tint = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        // COl 2: QUANTITY
        Text(
            text = "$quantity",
            fontWeight = FontWeight.SemiBold,
            fontFamily = Quicksand,
            fontSize = 24.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))

        // Col 3: ADD
        IconButton(
            onClick = {
                setQuantity(quantity + 1)
            },
            modifier = Modifier
                .size(30.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "Add",
                tint = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}

@Composable
fun SizeSpinner(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState()
) {
    // Props
    val scope = rememberCoroutineScope()
    val sizes = Sizes.values()
    val height = 100
    val blockHeight = 100 / 3
    val blockHeightPx = with(LocalDensity.current) { blockHeight.dp.toPx() }
    val primaryColor = MaterialTheme.colors.primary

    // State
    val dragState = scrollState.interactionSource.collectIsDraggedAsState()

    // Side Effect
    LaunchedEffect(dragState.value) {
        if (dragState.value) {
            return@LaunchedEffect
        }

        val yOffset = scrollState.value
        Log.d("Size Spinner", "yOffset: $yOffset")

        val yOffsetToScroll =
            if (yOffset <= 0.5 * blockHeightPx)
                0
            else if (yOffset <= 1.5 * blockHeightPx)
                blockHeightPx
            else
                2 * blockHeightPx

        Log.d("Size Spinner", "yOffsetToScroll: $yOffsetToScroll")
        scope.launch {
           scrollState.animateScrollTo(yOffsetToScroll.toInt())
        }
    }

    // UI
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        // Col 1: POINTER
        Icon(
            painter = painterResource(id = R.drawable.ic_pointer),
            contentDescription = "Pointer",
            tint = MaterialTheme.colors.primary.copy(alpha = 0.2F),
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 7.dp)
        )

        // Col 2: SIZES
        Column(
            modifier = Modifier
                .height(height.dp)
                .width(50.dp)
                .verticalScroll(scrollState)
                .graphicsLayer(alpha = 0.99F)
                .drawWithCache {
                    onDrawWithContent {
                        drawRect(
                            topLeft = Offset(0F, 0F),
                            size = size,
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    primaryColor.copy(alpha = 0.3F),
                                    primaryColor.copy(alpha = 0F),
                                )
                            ),
                            blendMode = BlendMode.SrcAtop // this will mask the text
                        )
                        drawContent()
                    }
                }
        ) {
            Spacer(
                modifier = Modifier
                    .height(blockHeight.dp)
                    .fillMaxWidth()
            )

            sizes.forEach { size ->
                Text(
                    text = "${size.number} inch",
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Quicksand,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .height(blockHeight.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(
                modifier = Modifier
                    .height(blockHeight.dp)
                    .fillMaxWidth()
            )
        }
    }
}




/**
 * Preview Section
 */
@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun ItemCardPreview() {
    BalloonyTheme {
        ItemCard(
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

@Preview
@Composable
fun SaleBannerPreview() {
    BalloonyTheme {
        SalesBanner()
    }
}

@Preview
@Composable
fun FeatureRowPreview() {
    BalloonyTheme {
        FeatureRow()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun SizeSpinnerPreview() {
    BalloonyTheme {
        SizeSpinner()
    }
}