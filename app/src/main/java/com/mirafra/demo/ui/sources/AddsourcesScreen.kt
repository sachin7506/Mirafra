package com.mirafra.demo.ui.sources

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirafra.demo.ui.theme.DemoTheme
import com.mirafra.demo.ui.theme.appColors

@Composable
fun AddSourcesScreen() {
    val colors = appColors()

    // dashed border color
    val dashColor = colors.primary.copy(alpha = 0.35f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.screenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

        // ── Top bar ───────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colors.primaryText
                )
            }
            Text(
                text = "Add Sources",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // ── Dashed upload zone ────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .drawBehind {
                        val stroke = Stroke(
                            width = 1.8.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10.dp.toPx(), 7.dp.toPx())
                            )
                        )
                        drawRoundRect(
                            color = dashColor,
                            style = stroke,
                            cornerRadius = CornerRadius(16.dp.toPx())
                        )
                    }
                    .background(
                        color = colors.primarySoft.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Cloud upload icon
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(52.dp)
                    )

                    Text(
                        text = "Drag and drop files here",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primaryText
                    )

                    Text(
                        text = "or",
                        fontSize = 14.sp,
                        color = colors.secondaryText
                    )

                    // Browse Files button
                    OutlinedButton(
                        onClick = { },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colors.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .height(44.dp)
                    ) {
                        Text(
                            text = "Browse Files",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Supported formats ─────────────────────────
            Text(
                text = "Supported formats",
                fontSize = 14.sp,
                color = colors.secondaryText
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FormatBadge(label = "PDF",  topColor = Color(0xFFFF3B30), isText = true)
                FormatBadge(label = "DOCX", topColor = Color(0xFF007AFF), isText = false)
                FormatBadge(label = "TXT",  topColor = Color(0xFF8E8E93), isText = false)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Up to 5 files at a time",
                fontSize = 13.sp,
                color = colors.secondaryText
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Max file size: 50 MB",
                fontSize = 13.sp,
                color = colors.secondaryText
            )
        }

        // ── Upload & Process button (disabled state) ──────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colors.disabled),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Upload & Process",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Format badge  (PDF / DOCX / TXT)
// ─────────────────────────────────────────────────────────────
@Composable
private fun FormatBadge(
    label: String,
    topColor: Color,
    isText: Boolean          // true = coloured text label, false = document icon
) {
    val colors = appColors()
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colors.secondaryBackground),
            contentAlignment = Alignment.Center
        ) {
            if (isText) {
                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = topColor
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = topColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = colors.secondaryText
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddSourcesScreenPreview() {
    DemoTheme {
        AddSourcesScreen()
    }
}