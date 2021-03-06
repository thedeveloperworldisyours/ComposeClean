package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.a.vocabulary15.R
import com.a.vocabulary15.util.TestTags

@Composable
fun TestScoreCard(
    newImage: String,
    right: Int,
    wrong: Int
) {
    Card(
        backgroundColor = Color.Blue,
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .testTag(TestTags.SCORE)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(Color.Blue)
        ) {
            val (imageTest, imageStart, imageEnd, iconRight, iconLeft, textRight, textWrong) = createRefs()
            Text(
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxHeight()
                    .constrainAs(textRight) {
                        end.linkTo(iconRight.start)
                    },
                text = right.toString(),
                fontSize = 35.sp,
                color = Color.White
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Blue)
                    .constrainAs(iconRight) {
                        end.linkTo(imageStart.start)
                    },
                painter = painterResource(id = R.drawable.ic_green_24),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White)
                    .constrainAs(imageStart) {
                        end.linkTo(imageTest.start)
                    },
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White)
                    .constrainAs(imageTest) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = rememberImagePainter(newImage),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.White)
                    .constrainAs(imageEnd) {
                        start.linkTo(imageTest.end)
                    },
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Blue)
                    .constrainAs(iconLeft) {
                        start.linkTo(imageEnd.end)
                    },
                painter = painterResource(id = R.drawable.ic_red_24),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxHeight()
                    .constrainAs(textWrong) {
                        start.linkTo(iconLeft.end)
                    },
                text = wrong.toString(),
                fontSize = 35.sp,
                color = Color.White
            )
        }
    }
}