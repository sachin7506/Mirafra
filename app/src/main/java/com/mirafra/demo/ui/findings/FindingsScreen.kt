package com.mirafra.demo.ui.findings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
//  Models
// ─────────────────────────────────────────────────────────────
enum class FindingPriority(val label: String) {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}

data class Finding(
    val id: String,
    val priority: FindingPriority,
    val title: String,
    val description: String,
    val section: String,
    val page: String? = null
)

private enum class FindingFilter(val label: String) {
    ALL("All"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun FindingsScreen(
    findings: List<Finding> = sampleFindings,
    onBack: () -> Unit = {},
    onFilterIconClick: () -> Unit = {},
    onFindingClick: (Finding) -> Unit = {}
) {
    val colors = appColors()
    var selectedFilter by remember { mutableStateOf(FindingFilter.ALL) }

    val highCount = findings.count { it.priority == FindingPriority.HIGH }
    val mediumCount = findings.count { it.priority == FindingPriority.MEDIUM }
    val lowCount = findings.count { it.priority == FindingPriority.LOW }

    val filtered = when (selectedFilter) {
        FindingFilter.ALL -> findings
        FindingFilter.HIGH -> findings.filter { it.priority == FindingPriority.HIGH }
        FindingFilter.MEDIUM -> findings.filter { it.priority == FindingPriority.MEDIUM }
        FindingFilter.LOW -> findings.filter { it.priority == FindingPriority.LOW }
    }

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
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.cardBackground, CircleShape),
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
                text = "Findings",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(colors.primarySoft)
                    .clickable { onFilterIconClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = colors.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // ── Filter chips ──────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                text = "All ${findings.size}",
                selected = selectedFilter == FindingFilter.ALL,
                onClick = { selectedFilter = FindingFilter.ALL }
            )
            FilterChip(
                text = "High $highCount",
                selected = selectedFilter == FindingFilter.HIGH,
                onClick = { selectedFilter = FindingFilter.HIGH }
            )
            FilterChip(
                text = "Medium $mediumCount",
                selected = selectedFilter == FindingFilter.MEDIUM,
                onClick = { selectedFilter = FindingFilter.MEDIUM }
            )
            FilterChip(
                text = "Low $lowCount",
                selected = selectedFilter == FindingFilter.LOW,
                onClick = { selectedFilter = FindingFilter.LOW }
            )
        }

        // ── List ──────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered, key = { it.id }) { finding ->
                FindingCard(finding = finding, onClick = { onFindingClick(finding) })
            }
            item { Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Sub-components
// ─────────────────────────────────────────────────────────────
@Composable
private fun FilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = appColors()
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) colors.primary else colors.cardBackground)
            .border(
                width = if (selected) 0.dp else 1.dp,
                color = colors.border,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 9.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else colors.secondaryText
        )
    }
}

// Priority badge colors — not part of the shared theme yet, so defined
// locally. Move these into appColors() (e.g. colors.high / colors.medium)
// once a design token exists for them.
private val HighBg = Color(0xFFFCE6E2)
private val HighFg = Color(0xFFE0432B)
private val MediumBg = Color(0xFFFBF0DC)
private val MediumFg = Color(0xFFD6870B)

@Composable
private fun PriorityBadge(priority: FindingPriority) {
    val colors = appColors()
    val (bg, fg) = when (priority) {
        FindingPriority.HIGH -> HighBg to HighFg
        FindingPriority.MEDIUM -> MediumBg to MediumFg
        FindingPriority.LOW -> colors.border to colors.secondaryText
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = priority.label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = fg
        )
    }
}

@Composable
private fun FindingCard(finding: Finding, onClick: () -> Unit) {
    val colors = appColors()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.cardBackground)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
                PriorityBadge(priority = finding.priority)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = finding.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText,
                    lineHeight = 21.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colors.secondaryText,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = finding.description,
            fontSize = 13.5.sp,
            color = colors.secondaryText,
            lineHeight = 19.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (finding.page != null) "${finding.section} · Page ${finding.page}" else finding.section,
            fontSize = 12.5.sp,
            color = colors.tertiaryText
        )
    }
}

// ─────────────────────────────────────────────────────────────
//  Sample data (swap for real data from the view model)
// ─────────────────────────────────────────────────────────────
private val sampleFindings = listOf(
    Finding(
        id = "1",
        priority = FindingPriority.MEDIUM,
        title = "Insufficient guidance on handling inactive source versions",
        description = "The retrieval rules default to active source versions and only include previous versions for change analysis, but no guidance is provided for other scenarios where inactive versions might be needed.",
        section = "Section 6.2"
    ),
    Finding(
        id = "2",
        priority = FindingPriority.MEDIUM,
        title = "Missing enforcement details for cross-project content restriction",
        description = "The rule prohibits using content from another project, yet the document does not describe how this restriction will be enforced or validated.",
        section = "Section 6.2"
    ),
    Finding(
        id = "3",
        priority = FindingPriority.HIGH,
        title = "Deferred production hardening and external integrations",
        description = "The first milestone defers production hardening and external integrations, leaving the system without essential operational capabilities.",
        section = "Section 9",
        page = "0"
    ),
    Finding(
        id = "4",
        priority = FindingPriority.MEDIUM,
        title = "AI must generate structured evidence-backed findings",
        description = "The acceptance checklist requires the AI to produce at least one structured finding with priority, confidence, suggested question, and evidence reference.",
        section = "Section 9",
        page = "0"
    ),
    Finding(
        id = "5",
        priority = FindingPriority.MEDIUM,
        title = "Deterministic AI response contract required",
        description = "The integration contract mandates a frozen JSON/schema contract and deterministic responses to enable validation by the backend.",
        section = "Section 9",
        page = "0"
    ),
    Finding(
        id = "6",
        priority = FindingPriority.LOW,
        title = "Minor terminology inconsistency across sections",
        description = "The document alternates between \"reviewer\" and \"validator\" when referring to the same role, which may cause confusion.",
        section = "Section 3.1"
    ),
)

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun FindingsScreenPreview() {
    MaterialTheme {
        FindingsScreen()
    }
}