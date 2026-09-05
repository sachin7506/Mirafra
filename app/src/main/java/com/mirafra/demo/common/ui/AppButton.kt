package com.mirafra.demo.common.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirafra.demo.ui.theme.appColors

private val ButtonShape = RoundedCornerShape(14.dp)
private val ButtonHeight = 56.dp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val colors = appColors()
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.primary,
            contentColor = colors.whiteText,
            disabledContainerColor = colors.disabled,
            disabledContentColor = colors.secondaryText
        )
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val colors = appColors()
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(ButtonHeight),
        shape = ButtonShape,
        border = BorderStroke(
            width = 1.5.dp,
            color = if (enabled) colors.primary else colors.disabled
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (enabled) colors.primary else colors.disabled,
            disabledContentColor = colors.disabled
        )
    ) {
        if (leadingIcon != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                leadingIcon()
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        } else {
            Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        }
    }
}