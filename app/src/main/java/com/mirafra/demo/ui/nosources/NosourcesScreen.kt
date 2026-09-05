package com.mirafra.demo.ui.nosources


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirafra.demo.ui.theme.appColors


private val PrimaryGradientEnd = Color(0xFF2A44C4)

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun NoSourcesScreen(
    projectName: String = "Demo",
    objective: String = "Some",
    analysisFocus: String = "",
    onBack: () -> Unit = {},
    onAddSources: () -> Unit = {},
    onAddSourcesLater: () -> Unit = {}
) {
    val colors = appColors()

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
                .background(colors.cardBackground)
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = colors.primaryText,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = projectName,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText
            )
        }

        HorizontalDivider(color = colors.border, thickness = 0.8.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            // ── Empty-state icon ───────────────────────────
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(88.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(colors.primarySoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Folder,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(38.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "No sources added yet",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Add project documents to start your AI-powered review.",
                fontSize = 15.sp,
                color = colors.secondaryText,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            // ── Add Sources (primary, gradient) ────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(colors.primary, PrimaryGradientEnd)
                        )
                    )
                    .clickable { onAddSources() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add Sources",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── I'll Add Sources Later (secondary) ─────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colors.primarySoft)
                    .clickable { onAddSourcesLater() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "I'll Add Sources Later",
                    color = colors.primary,
                    fontSize = 15.5.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── About this project ──────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "About this project",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Objective",
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = objective.ifBlank { "Not set" },
                    fontSize = 14.5.sp,
                    color = colors.secondaryText
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Analysis Focus",
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )
                if (analysisFocus.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = analysisFocus,
                        fontSize = 14.5.sp,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun NoSourcesScreenPreview() {
    MaterialTheme {
        NoSourcesScreen()
    }
}