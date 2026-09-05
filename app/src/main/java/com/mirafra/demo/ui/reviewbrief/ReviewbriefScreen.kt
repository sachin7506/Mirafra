package com.mirafra.demo.ui.reviewbrief

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MoreVert
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
enum class ReviewPriority(val label: String) { HIGH("High"), MEDIUM("Medium"), LOW("Low") }

private enum class BriefTab(val label: String) { SUMMARY("Summary"), FINDINGS("Findings"), QUESTIONS("Questions"), CHANGES("Changes") }

data class BriefQuestion(val id: String, val text: String, val priority: ReviewPriority)

data class BriefFinding(val id: String, val title: String, val priority: ReviewPriority)

enum class ChangeType { ADDED, MODIFIED, REMOVED }

data class BriefChange(val id: String, val type: ChangeType, val title: String)

// Priority / change colors — not part of the shared theme yet, defined
// locally. Move into appColors() once a design token exists for them.
private val HighColor = Color(0xFFE0432B)
private val MediumColor = Color(0xFFD6870B)
private val LowColor = Color(0xFF6B7080)
private val AddedColor = Color(0xFF1E9E5A)
private val AddedBg = Color(0xFFE3F6EA)
private val ModifiedColor = Color(0xFFD6870B)
private val ModifiedBg = Color(0xFFFBF0DC)
private val RemovedColor = Color(0xFFE0432B)
private val RemovedBg = Color(0xFFFCE6E2)

private fun ReviewPriority.color(): Color = when (this) {
    ReviewPriority.HIGH -> HighColor
    ReviewPriority.MEDIUM -> MediumColor
    ReviewPriority.LOW -> LowColor
}

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun ReviewBriefScreen(
    reviewStatus: String = "Completed",
    reviewedAt: String = "28 August 2026 at 12:37 PM",
    summary: String = "The document defines a clear AI-assisted review workflow with structured findings, questions, and decisions. Six findings were raised, one of high priority around deferred production hardening. Four open questions need stakeholder input before the next milestone.",
    findings: List<BriefFinding> = sampleFindings,
    questions: List<BriefQuestion> = sampleQuestions,
    changes: List<BriefChange> = sampleChanges,
    onBack: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onQuestionClick: (BriefQuestion) -> Unit = {},
    onFindingClick: (BriefFinding) -> Unit = {},
    onChangeClick: (BriefChange) -> Unit = {}
) {
    val colors = appColors()
    var selectedTab by remember { mutableStateOf(BriefTab.QUESTIONS) }

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
                text = "Review Brief",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )

            Box(
                modifier = Modifier.size(40.dp),
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

            // ── Status card ────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(colors.success.copy(alpha = 0.12f), CircleShape)
                        .border(1.5.dp, colors.success.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .background(colors.success, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "AI Review completed",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = reviewedAt,
                        fontSize = 12.5.sp,
                        color = colors.secondaryText
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colors.success.copy(alpha = 0.12f))
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = reviewStatus,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.success
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Tab row ────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BriefTabChip(
                    text = "Summary",
                    selected = selectedTab == BriefTab.SUMMARY,
                    onClick = { selectedTab = BriefTab.SUMMARY }
                )
                BriefTabChip(
                    text = "Findings ${findings.size}",
                    selected = selectedTab == BriefTab.FINDINGS,
                    onClick = { selectedTab = BriefTab.FINDINGS }
                )
                BriefTabChip(
                    text = "Questions ${questions.size}",
                    selected = selectedTab == BriefTab.QUESTIONS,
                    onClick = { selectedTab = BriefTab.QUESTIONS }
                )
                BriefTabChip(
                    text = "Changes ${changes.size}",
                    selected = selectedTab == BriefTab.CHANGES,
                    onClick = { selectedTab = BriefTab.CHANGES }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Tab content card ───────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.cardBackground)
                    .border(1.dp, colors.border, RoundedCornerShape(16.dp))
                    .padding(18.dp)
            ) {
                when (selectedTab) {
                    BriefTab.SUMMARY -> {
                        Text(
                            text = summary,
                            fontSize = 14.sp,
                            color = colors.primaryText,
                            lineHeight = 21.sp
                        )
                    }

                    BriefTab.FINDINGS -> {
                        findings.forEachIndexed { index, finding ->
                            BriefFindingRow(finding = finding, onClick = { onFindingClick(finding) })
                            if (index != findings.lastIndex) Spacer(modifier = Modifier.height(18.dp))
                        }
                    }

                    BriefTab.QUESTIONS -> {
                        questions.forEachIndexed { index, question ->
                            BriefQuestionRow(question = question, onClick = { onQuestionClick(question) })
                            if (index != questions.lastIndex) Spacer(modifier = Modifier.height(18.dp))
                        }
                    }

                    BriefTab.CHANGES -> {
                        changes.forEachIndexed { index, change ->
                            BriefChangeRow(change = change, onClick = { onChangeClick(change) })
                            if (index != changes.lastIndex) Spacer(modifier = Modifier.height(18.dp))
                        }
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
private fun BriefTabChip(text: String, selected: Boolean, onClick: () -> Unit) {
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
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else colors.secondaryText
        )
    }
}

@Composable
private fun BriefQuestionRow(question: BriefQuestion, onClick: () -> Unit) {
    val colors = appColors()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(1.5.dp, colors.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = question.text,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.primaryText,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = question.priority.label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = question.priority.color()
            )
        }
    }
}

@Composable
private fun BriefFindingRow(finding: BriefFinding, onClick: () -> Unit) {
    val colors = appColors()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(finding.priority.color(), CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = finding.title,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.primaryText,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = finding.priority.label,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = finding.priority.color()
        )
    }
}

@Composable
private fun BriefChangeRow(change: BriefChange, onClick: () -> Unit) {
    val (bg, fg, label) = when (change.type) {
        ChangeType.ADDED -> Triple(AddedBg, AddedColor, "Added")
        ChangeType.MODIFIED -> Triple(ModifiedBg, ModifiedColor, "Modified")
        ChangeType.REMOVED -> Triple(RemovedBg, RemovedColor, "Removed")
    }
    val colors = appColors()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .background(bg)
                .padding(horizontal = 9.dp, vertical = 4.dp)
        ) {
            Text(text = label, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = fg)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = change.title,
            fontSize = 14.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.primaryText,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

// ─────────────────────────────────────────────────────────────
//  Sample data (swap for real data from the view model)
// ─────────────────────────────────────────────────────────────
private val sampleQuestions = listOf(
    BriefQuestion(
        id = "1",
        text = "Should the document provide explicit guidance on when and how to use inactive source versions beyond change analysis?",
        priority = ReviewPriority.MEDIUM
    ),
    BriefQuestion(
        id = "2",
        text = "What mechanisms will ensure that content from other projects is not inadvertently used?",
        priority = ReviewPriority.MEDIUM
    ),
    BriefQuestion(
        id = "3",
        text = "When will production hardening and external integrations be implemented?",
        priority = ReviewPriority.HIGH
    ),
    BriefQuestion(
        id = "4",
        text = "How will the deterministic behavior of the AI response be validated?",
        priority = ReviewPriority.MEDIUM
    ),
)

private val sampleFindings = listOf(
    BriefFinding("1", "Insufficient guidance on handling inactive source versions", ReviewPriority.MEDIUM),
    BriefFinding("2", "Missing enforcement details for cross-project content restriction", ReviewPriority.MEDIUM),
    BriefFinding("3", "Deferred production hardening and external integrations", ReviewPriority.HIGH),
    BriefFinding("4", "AI must generate structured evidence-backed findings", ReviewPriority.MEDIUM),
    BriefFinding("5", "Deterministic AI response contract required", ReviewPriority.MEDIUM),
    BriefFinding("6", "Minor terminology inconsistency across sections", ReviewPriority.LOW),
)

private val sampleChanges = listOf(
    BriefChange("1", ChangeType.ADDED, "Rate limiting requirements added to API contract"),
    BriefChange("2", ChangeType.MODIFIED, "Source retrieval rules updated to active-only default"),
    BriefChange("3", ChangeType.REMOVED, "Legacy webhook fallback removed"),
    BriefChange("4", ChangeType.ADDED, "New acceptance checklist for AI findings"),
    BriefChange("5", ChangeType.MODIFIED, "Question priority levels redefined"),
)

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun ReviewBriefScreenPreview() {
    MaterialTheme {
        ReviewBriefScreen()
    }
}