package com.mirafra.demo.ui.projectdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mirafra.demo.ui.theme.appColors

// ─────────────────────────────────────────────────────────────
//  Simple UI models (swap for real data from the view model)
// ─────────────────────────────────────────────────────────────
data class ProjectStat(val value: Int, val label: String)

data class ProjectSource(
    val name: String,
    val fileType: String,
    val status: String   // "Completed" | "Processing" | "Failed"
)

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun ProjectDetailScreen(
    projectStatus: String = "Completed",
    projectNameLines: List<String> = listOf("Erfvwfrv", "Evqev", "Eaves..."),
    stats: List<ProjectStat> = listOf(
        ProjectStat(2, "Sources"),
        ProjectStat(6, "Findings"),
        ProjectStat(0, "Decisions"),
        ProjectStat(4, "Questions"),
    ),
    sources: List<ProjectSource> = listOf(
        ProjectSource("Engineering_Manager_Copilot...", "docx", "Completed"),
        ProjectSource("EMDocx.docx", "docx", "Completed"),
    ),
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onViewBrief: () -> Unit = {},
    onSourceClick: (ProjectSource) -> Unit = {}
) {
    val colors = appColors()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.screenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

        // ── Top bar ───────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.cardBackground, CircleShape)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = colors.primaryText
                    )
                }
            }

            Text(
                text = "Project",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                modifier = Modifier.align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = colors.primaryText
                    )
                }
            }
        }

        HorizontalDivider(color = colors.border, thickness = 0.8.dp)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Status hero card (compact) ─────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(colors.primary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = projectStatus,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = projectNameLines.joinToString(" · "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.85f),
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Stats row (single row, 4 across) ───────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stats.forEach { stat ->
                    StatCard(stat = stat, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Latest Review ──────────────────────────────
            SectionHeader(text = "Latest Review")
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(colors.primarySoft, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "AI review completed",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Latest project review",
                        fontSize = 13.sp,
                        color = colors.secondaryText
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = onViewBrief,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.primary),
                    border = androidx.compose.foundation.BorderStroke(1.dp, colors.border),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text("View Brief", fontSize = 13.5.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Source Activity ─────────────────────────────
            SectionHeader(text = "Source Activity")
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            ) {
                sources.forEachIndexed { index, source ->
                    SourceRow(source = source, onClick = { onSourceClick(source) })
                    if (index != sources.lastIndex) {
                        HorizontalDivider(color = colors.border, thickness = 0.8.dp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Sub-components
// ─────────────────────────────────────────────────────────────
@Composable
private fun StatCard(stat: ProjectStat, modifier: Modifier = Modifier) {
    val colors = appColors()
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colors.cardBackground)
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${stat.value}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colors.primary
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = stat.label,
            fontSize = 11.sp,
            color = colors.secondaryText,
            maxLines = 1
        )
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = appColors().primaryText
    )
}

@Composable
private fun SourceRow(source: ProjectSource, onClick: () -> Unit) {
    val colors = appColors()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Description,
            contentDescription = null,
            tint = colors.secondaryText,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = source.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.primaryText,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = source.fileType,
                fontSize = 13.sp,
                color = colors.tertiaryText
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(colors.success.copy(alpha = 0.12f))
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Text(
                text = source.status,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.success
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun ProjectDetailScreenPreview() {
    MaterialTheme {
        ProjectDetailScreen()
    }
}