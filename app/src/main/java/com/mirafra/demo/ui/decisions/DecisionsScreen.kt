package com.mirafra.demo.ui.decisions


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
enum class DecisionStatus { PENDING, DECIDED }

data class DecisionItem(
    val id: String,
    val title: String,
    val status: DecisionStatus,
    val projectName: String
)

private enum class DecisionFilter(val label: String) {
    ALL("All"),
    PENDING("Pending"),
    DECIDED("Decided")
}

// ─────────────────────────────────────────────────────────────
//  Screen
// ─────────────────────────────────────────────────────────────
@Composable
fun DecisionsScreen(
    decisions: List<DecisionItem> = emptyList(),
    onDecisionClick: (DecisionItem) -> Unit = {},
    selectedNavTab: Int = 1,
    onTabSelected: (Int) -> Unit = {}
) {
    val colors = appColors()
    var selectedFilter by remember { mutableStateOf(DecisionFilter.ALL) }

    val pendingCount = decisions.count { it.status == DecisionStatus.PENDING }
    val decidedCount = decisions.count { it.status == DecisionStatus.DECIDED }

    val filtered = when (selectedFilter) {
        DecisionFilter.ALL -> decisions
        DecisionFilter.PENDING -> decisions.filter { it.status == DecisionStatus.PENDING }
        DecisionFilter.DECIDED -> decisions.filter { it.status == DecisionStatus.DECIDED }
    }

    Scaffold(
        containerColor = colors.screenBackground,
        bottomBar = {
            DecisionsBottomNavBar(
                selectedIndex = selectedNavTab,
                onTabSelected = onTabSelected
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {

            // ── Header ────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Decisions",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primaryText
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Track and manage project decisions",
                    fontSize = 14.sp,
                    color = colors.secondaryText
                )
                Spacer(modifier = Modifier.height(18.dp))
            }

            // ── Filter chips ──────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DecisionFilterChip(
                    text = "All ${decisions.size}",
                    selected = selectedFilter == DecisionFilter.ALL,
                    onClick = { selectedFilter = DecisionFilter.ALL }
                )
                DecisionFilterChip(
                    text = "Pending $pendingCount",
                    selected = selectedFilter == DecisionFilter.PENDING,
                    onClick = { selectedFilter = DecisionFilter.PENDING }
                )
                DecisionFilterChip(
                    text = "Decided $decidedCount",
                    selected = selectedFilter == DecisionFilter.DECIDED,
                    onClick = { selectedFilter = DecisionFilter.DECIDED }
                )
            }

            HorizontalDivider(color = colors.border, thickness = 0.8.dp)

            // ── Content ─────────────────────────────────────
            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .border(1.5.dp, colors.tertiaryText, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = colors.tertiaryText,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "No decisions available",
                            fontSize = 15.sp,
                            color = colors.secondaryText
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered, key = { it.id }) { decision ->
                        DecisionCard(decision = decision, onClick = { onDecisionClick(decision) })
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Sub-components
// ─────────────────────────────────────────────────────────────
@Composable
private fun DecisionFilterChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val colors = appColors()
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) colors.primary else colors.primarySoft)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) Color.White else colors.primaryText
        )
    }
}

@Composable
private fun DecisionCard(decision: DecisionItem, onClick: () -> Unit) {
    val colors = appColors()
    val statusColor = if (decision.status == DecisionStatus.DECIDED) colors.success else colors.primary
    val statusLabel = if (decision.status == DecisionStatus.DECIDED) "Decided" else "Pending"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.cardBackground)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = decision.title,
                fontSize = 15.5.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                lineHeight = 21.sp,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(7.dp))
                    .background(statusColor.copy(alpha = 0.12f))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = statusLabel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = statusColor
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = decision.projectName,
            fontSize = 13.sp,
            color = colors.secondaryText
        )
    }
}

@Composable
private fun DecisionsBottomNavBar(selectedIndex: Int, onTabSelected: (Int) -> Unit) {
    val colors = appColors()
    NavigationBar(containerColor = colors.cardBackground, tonalElevation = 0.dp) {
        listOf(
            Triple(Icons.Default.Home, "Projects", 0),
            Triple(Icons.Default.Description, "Decisions", 1),
            Triple(Icons.Default.Settings, "Settings", 2),
        ).forEach { (icon, label, index) ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.primary,
                    unselectedIconColor = colors.secondaryText,
                    unselectedTextColor = colors.secondaryText,
                    indicatorColor = colors.primarySoft
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Preview
// ─────────────────────────────────────────────────────────────
@Preview(showBackground = true, widthDp = 392, heightDp = 812)
@Composable
private fun DecisionsScreenPreview() {
    MaterialTheme {
        DecisionsScreen()
    }
}

@Preview(showBackground = true, widthDp = 392, heightDp = 812, name = "With decisions")
@Composable
private fun DecisionsScreenWithDataPreview() {
    MaterialTheme {
        DecisionsScreen(
            decisions = listOf(
                DecisionItem("1", "Ship Milestone 1 without SSO", DecisionStatus.DECIDED, "Demo"),
                DecisionItem("2", "Freeze JSON contract before Milestone 2", DecisionStatus.PENDING, "Demo"),
                DecisionItem("3", "Defer production hardening to Milestone 2", DecisionStatus.PENDING, "Demo"),
            )
        )
    }
}