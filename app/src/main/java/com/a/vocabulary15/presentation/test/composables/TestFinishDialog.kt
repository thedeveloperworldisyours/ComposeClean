package com.a.vocabulary15.presentation.test.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.a.vocabulary15.R
import com.a.vocabulary15.presentation.test.TestEvent
import com.a.vocabulary15.presentation.test.TestViewModel

@Composable
fun TestFinishedDialog(
    navController: NavController,
    viewModel: TestViewModel
) {
    if (viewModel.state.value.isTestFinishOpen) {
        Dialog(onDismissRequest = { viewModel.onEvent(TestEvent.TestFinish(false)) }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                ConstraintLayout(modifier = Modifier.padding(15.dp).fillMaxWidth()){
                    val (firstSpace, title, secondSpace, score, threeSpace, question, fourthSpace, row, image) = createRefs()
                    Spacer(modifier = Modifier.padding(5.dp).constrainAs(firstSpace) {top.linkTo(parent.top)})
                    Image(
                        modifier = Modifier
                            .constrainAs(image) {
                                end.linkTo(parent.end)
                            },
                        painter = painterResource(id = R.drawable.ic_game_over),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.constrainAs(title) {top.linkTo(firstSpace.bottom)},
                        text = stringResource(id = R.string.test_finished),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp).constrainAs(secondSpace) {top.linkTo(title.bottom)})
                    Text(
                        modifier = Modifier.constrainAs(score) {top.linkTo(secondSpace.bottom)},
                        text = stringResource(
                            id = R.string.test_finished_score,
                            viewModel.state.value.right - viewModel.state.value.wrong
                        ),
                        color = viewModel.state.value.finalScoreColor,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp).constrainAs(threeSpace) {top.linkTo(score.bottom)})
                    Text(
                        modifier = Modifier.constrainAs(question) {top.linkTo(threeSpace.bottom)},
                        text = stringResource(id = R.string.test_finished_question),
                        color = Color.Black,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(15.dp).constrainAs(fourthSpace) {top.linkTo(question.bottom)})
                    Row(
                        modifier = Modifier.fillMaxWidth().constrainAs(row) {top.linkTo(fourthSpace.bottom)},
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                navController.navigateUp()
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp)
                                .padding(0.dp, 0.dp, 10.dp, 0.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.test_finished_no),
                                color = Color.White
                            )
                        }
                        Button(
                            onClick = {
                                viewModel.startAgain()
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .height(40.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.accept),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}