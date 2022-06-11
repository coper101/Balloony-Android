package com.darealreally.balloony.ui.main.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darealreally.balloony.R
import com.darealreally.balloony.ui.theme.BalloonyTheme
import com.darealreally.balloony.ui.theme.Quicksand

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            // Row 1: HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 21.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Col 1: NAME
                Text(
                    text = "Reviews",
                    fontWeight = FontWeight.Medium,
                    fontFamily = Quicksand,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 3.dp)
                )

                // Col 2: RATING BAR
                RatingBar()
            }

            CardDivider(
                modifier = Modifier
                    .padding(horizontal = 21.dp)
                    .padding(top = 10.dp)
            )

            // Row 2: CUSTOMER REVIEW
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 21.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                // Col 1: REVIEW
                Text(
                    text = "Delivery was quick and it came with a thank you note. Thanks.",
                    fontWeight = FontWeight.Medium,
                    fontFamily = Quicksand,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.primary
                )

                // Col 2: RATING & CUSTOMER INFO
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    // Col 1: REVIEW COUNT
                    Text(
                        text = "1/100",
                        fontWeight = FontWeight.Medium,
                        fontFamily = Quicksand,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                    )

                    Spacer(modifier = Modifier.weight(1F))

                    // Col 2: RATING
                    RatingBar(
                        ratingNum = 4,
                        height = 15
                    )

                    // Col 3: CUSTOMER NAME
                    Text(
                        text = "John Doe",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Quicksand,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary.copy(alpha = 0.5F)
                    )
                }
            }

            CardDivider(
                modifier = Modifier.padding(top = 20.dp)
            )

            // Row 3: SEE ALL BUTTON
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clickable {},
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    // Col 1: TITLE
                    Text(
                        text = "SEE ALL",
                        fontWeight = FontWeight.Medium,
                        fontFamily = Quicksand,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    // Col 2: ICON
                    Icon(
                        painter = painterResource(id = R.drawable.ic_left_arrow),
                        contentDescription = "See All Reviews",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(17.dp)
                    )
                } //: Row
            } //: Box

        } //: Column
    } //: Card
}

@Composable
fun RatingBar(
    ratingNum: Int = 5,
    height: Int = 20
) {
    Row {
        repeat(5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Rate",
                tint = MaterialTheme.colors.primary.copy(
                    alpha = if ((it + 1) > ratingNum) 0.2F else 1F
                ),
                modifier = Modifier.height(height.dp)
            )
        }
    }
}


/**
 * Preview Section
 */
@Preview(showBackground = true, backgroundColor = 0xFF10102E)
@Composable
fun ReviewCardPreview() {
    BalloonyTheme {
        ReviewCard(
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

@Preview
@Composable
fun RatingBarPreview() {
    BalloonyTheme {
        RatingBar()
    }
}
