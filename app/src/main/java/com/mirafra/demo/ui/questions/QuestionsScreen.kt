package com.mirafra.demo.ui.questions

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
data class QuestionItem(
    val id: String,
    val text: String,
    val askedBy: String = "AI",
    val isAnswered: Boolean = false
)

private enum class QuestionFilter(val label: String) {
    AI("AI"),
    UNANSWERED("Unanswered"),
    ANSWERED("Answered")
}

// Status badge colors — not part of the shared theme yet, defined locally.
// Move into appColors() once a design token exists for them.
private val UnansweredBg = Color(0xFFFCE6E2)
private val UnansweredFg = Color(0xFFE0432B)

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun QuestionsScreen(
    questions: List<QuestionItem> = sampleQuestions,
    onBack: () -> Unit = {},
    onFilterIconClick: () -> Unit = {},
    onQuestionClick: (QuestionItem) -> Unit = {}
) {
    val colors = appColors()
    var selectedFilter by remember { mutableStateOf(QuestionFilter.AI) }

    val aiCount = questions.count { it.askedBy == "AI" }
    val unansweredCount = questions.count { !it.isAnswered }
    val answeredCount = questions.count { it.isAnswered }

    val filtered = when (selectedFilter) {
        QuestionFilter.AI -> questions.filter { it.askedBy == "AI" }
        QuestionFilter.UNANSWERED -> questions.filter { !it.isAnswered }
        QuestionFilter.ANSWERED -> questions.filter { it.isAnswered }
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
                text = "Questions",
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
            QuestionFilterChip(
                text = "AI $aiCount",
                selected = selectedFilter == QuestionFilter.AI,
                onClick = { selectedFilter = QuestionFilter.AI }
            )
            QuestionFilterChip(
                text = "Unanswered $unansweredCount",
                selected = selectedFilter == QuestionFilter.UNANSWERED,
                onClick = { selectedFilter = QuestionFilter.UNANSWERED }
            )
            QuestionFilterChip(
                text = "Answered $answeredCount",
                selected = selectedFilter == QuestionFilter.ANSWERED,
                onClick = { selectedFilter = QuestionFilter.ANSWERED }
            )
        }

        // ── List ──────────────────────────────────────────
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered, key = { it.id }) { question ->
                QuestionCard(question = question, onClick = { onQuestionClick(question) })
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
private fun QuestionFilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
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

@Composable
private fun QuestionCard(question: QuestionItem, onClick: () -> Unit) {
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
            Text(
                text = question.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                lineHeight = 21.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            if (question.isAnswered) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                        .background(colors.success.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Answered",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.success
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(7.dp))
                        .background(UnansweredBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "Unanswered",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = UnansweredFg
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Asked by ${question.askedBy}",
            fontSize = 13.sp,
            color = colors.secondaryText
        )
    }
}

// ─────────────────────────────────────────────────────────────
//  Sample data (swap for real data from the view model)
// ─────────────────────────────────────────────────────────────
private val sampleQuestions = listOf(
    QuestionItem(
        id = "1",
        text = "When will security hardening be integrated into the production environment?"
    ),
    QuestionItem(
        id = "2",
        text = "What is the timeline for implementing enterprise SSO?"
    ),
    QuestionItem(
        id = "3",
        text = "When will production hardening be addressed?"
    ),
    QuestionItem(
        id = "4",
        text = "What external integrations are planned and what is their schedule?"
    ),
    QuestionItem(
        id = "5",
        text = "What mitigations and testing procedures are in place for the listed security threats?"
    ),
    QuestionItem(
        id = "6",
        text = "When will the JSON/schema contract be agreed and frozen?"
    ),
    QuestionItem(
        id = "7",
        text = "What is the validation behavior when evidence is missing or unsupported?"
    ),
    QuestionItem(
        id = "8",
        text = "How will mock data be synchronized with final API responses?"
    ),
)

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun QuestionsScreenPreview() {
    MaterialTheme {
        QuestionsScreen()
    }
}