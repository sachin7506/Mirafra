package com.mirafra.demo.common.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mirafra.demo.ui.theme.appColors

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    val colors = appColors()
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(pageCount) { index ->
            val isActive = index == currentPage
            val dotWidth by animateDpAsState(
                targetValue = if (isActive) 28.dp else 8.dp,
                animationSpec = tween(durationMillis = 250),
                label = "dot_width_$index"
            )
            Box(
                modifier = Modifier
                    .width(dotWidth)
                    .height(8.dp)
                    .background(
                        color = if (isActive) colors.primary else colors.border,
                        shape = if (isActive) RoundedCornerShape(4.dp) else CircleShape
                    )
            )
        }
    }
}