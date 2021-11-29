package com.a.vocabulary15.presentation.ui.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.a.vocabulary15.R
import com.a.vocabulary15.domain.model.Element
import com.a.vocabulary15.presentation.ElementsActivity
import com.a.vocabulary15.presentation.common.Constants.COLLAPSE_ANIMATION
import com.a.vocabulary15.presentation.common.Constants.EXPAND_ANIMATION
import com.a.vocabulary15.presentation.common.Constants.FADE_IN_ANIMATION
import com.a.vocabulary15.presentation.common.Constants.FADE_OUT_ANIMATION
import com.a.vocabulary15.presentation.element.DeleteElementDialog
import com.a.vocabulary15.presentation.element.EditElementDialog

@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableCard(
    element: Element,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
    activity: ElementsActivity
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "transition")
    val cardBgColor by transition.animateColor(
        { tween(durationMillis = EXPAND_ANIMATION) },
        label = "bgColorTransition"
    ) {
        if (expanded) Color.Blue else Color.Blue

    }
    val cardPaddingHorizontal by transition.animateDp(
        { tween(durationMillis = EXPAND_ANIMATION) },
        label = "paddingTransition"
    ) {
        20.dp
    }
    val cardElevation by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION,
            easing = FastOutSlowInEasing
        )
    }, label = "elevation Transition") {
        20.dp
    }
    val cardRoundedCorners by transition.animateDp(
        {
            tween(
                durationMillis = EXPAND_ANIMATION,
                easing = FastOutSlowInEasing
            )
        },
        label = "corner Transition"
    ) {
        15.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(
            durationMillis = EXPAND_ANIMATION,
            easing = FastOutSlowInEasing
        )
    }, label = "corner Transition") {
        if (expanded) 0f else 180f
    }
    Card(
        backgroundColor = cardBgColor,
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = element.value,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp, 10.dp, 0.dp, 10.dp)
                        .align(Alignment.CenterStart)
                )
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterEnd)
                )
            }
            ExpandableContent(
                element = element, expanded = expanded,
                activity
            )
        }
    }
}


@Composable
fun CardArrow(
    modifier: Modifier,
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier, onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_expand_less_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
                tint = Color.White
            )
        }
    )
}

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(
    element: Element,
    expanded: Boolean = true,
    activity: ElementsActivity
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION))
    }

    AnimatedVisibility(
        visible = expanded,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        EditElementDialog(activity, element)
        DeleteElementDialog(activity, element)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Button(
                onClick = {
                    ContextCompat.startActivity(
                        activity,
                        Intent(Intent.ACTION_VIEW, Uri.parse(element.link)),
                        null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 2.dp, 0.dp, 10.dp)
            ) {
                Text(
                    text = element.link,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp, 5.dp, 0.dp, 5.dp),
                    color = colorResource(id = R.color.white)
                )
            }
            Row {
                IconButton(
                    onClick = {
                        activity.viewModel.isDeleteElementOpen.value = true
                    },//activity.viewModel.deleteElement(element.id) },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.Red)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete),
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.size(5.dp))
                IconButton(
                    onClick = {
                        activity.viewModel.isEditElementOpen.value = true
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.LightGray)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = Color.White
                    )
                }
            }
        }
    }
}