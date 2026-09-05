package com.mirafra.demo.ui.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirafra.demo.data.remote.model.response.Project
import com.mirafra.demo.ui.theme.appColors

@Composable
fun ProjectsScreen(
    onNewProject: () -> Unit,
    onProjectClick: (String) -> Unit,              // passes project id
    viewModel: ProjectsViewModel = hiltViewModel()
) {
    val colors  = appColors()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchQuery    by remember { mutableStateOf("") }
    var selectedNavTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = colors.screenBackground,
        bottomBar = {
            BottomNavBar(
                selectedIndex = selectedNavTab,
                onTabSelected = { selectedNavTab = it }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 20.dp)
        ) {

            // ── Header ────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Text("Projects", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = colors.primaryText)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Review your engineering projects with AI-backed evidence.",
                    fontSize = 14.sp,
                    color = colors.secondaryText,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            // ── Search + Filter ───────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search projects", color = colors.tertiaryText, fontSize = 15.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = colors.tertiaryText, modifier = Modifier.size(20.dp))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.weight(1f).height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor   = colors.cardBackground,
                            unfocusedContainerColor = colors.cardBackground,
                            focusedBorderColor      = colors.primary,
                            unfocusedBorderColor    = colors.border,
                            focusedTextColor        = colors.primaryText,
                            unfocusedTextColor      = colors.primaryText,
                            cursorColor             = colors.primary
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(colors.cardBackground)
                            .border(1.dp, colors.border, RoundedCornerShape(14.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter", tint = colors.primaryText, modifier = Modifier.size(22.dp))
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // ── New Project button ────────────────────────
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(colors.primary)
                        .clickable { onNewProject() },          // ✅ wired
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Project", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ── Section header ────────────────────────────
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recent Projects", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = colors.primaryText)
                    if (uiState is ProjectsUiState.Success) {
                        Text(
                            text = "${(uiState as ProjectsUiState.Success).projects.size}",
                            fontSize = 15.sp,
                            color = colors.secondaryText
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // ── States ────────────────────────────────────
            when (val state = uiState) {

                is ProjectsUiState.Loading -> item {
                    Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colors.primary)
                    }
                }

                is ProjectsUiState.Error -> item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        TextButton(onClick = viewModel::fetchProjects) {
                            Text("Retry", color = colors.primary)
                        }
                    }
                }

                is ProjectsUiState.Success -> {
                    val filtered = state.projects.filter {    // ✅ local search filter
                        it.projectName.contains(searchQuery, ignoreCase = true)
                    }

                    if (filtered.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                                Text("No projects found", color = colors.secondaryText, fontSize = 14.sp)
                            }
                        }
                    } else {
                        items(filtered.size) { index ->
                            ProjectCard(
                                project = filtered[index],
                                onClick = { onProjectClick(filtered[index].id) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                else -> Unit
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────
//  Project Card  (now uses real Project model)
// ─────────────────────────────────────────────────────────────
@Composable
private fun ProjectCard(
    project: Project,
    onClick: () -> Unit
) {
    val colors = appColors()

    val statusColor = when (project.status) {
        "active"    -> colors.primary
        "completed" -> colors.success
        else        -> colors.secondaryText
    }

    val statusLabel = when (project.aiReviewStatus) {
        "not_started" -> "AI review not started"
        "in_progress" -> "AI review in progress"
        "completed"   -> "AI review ready"
        else          -> project.status
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.cardBackground)
            .border(1.dp, colors.border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.primarySoft, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = colors.primary, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(project.projectName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colors.primaryText)
                Spacer(modifier = Modifier.height(2.dp))
                Text(statusLabel, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = statusColor)
            }

            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = colors.secondaryText, modifier = Modifier.size(20.dp))
        }

        HorizontalDivider(color = colors.border, thickness = 0.8.dp)

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatText(count = project.sourceCount,   label = "Sources")
            StatText(count = project.findingCount,  label = "Findings")
            StatText(count = project.decisionCount, label = if (project.decisionCount == 1) "Decision" else "Decisions")
        }
    }
}

@Composable
private fun StatText(count: Int, label: String) {
    val colors = appColors()
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = colors.primaryText, fontWeight = FontWeight.Bold)) { append("$count") }
            append(" ")
            withStyle(SpanStyle(color = colors.secondaryText)) { append(label) }
        },
        fontSize = 13.sp
    )
}

@Composable
private fun BottomNavBar(selectedIndex: Int, onTabSelected: (Int) -> Unit) {
    val colors = appColors()
    NavigationBar(containerColor = colors.cardBackground, tonalElevation = 0.dp) {
        listOf(
            Triple(Icons.Default.Home,        "Projects",  0),
            Triple(Icons.Default.Description, "Decisions", 1),
            Triple(Icons.Default.Settings,    "Settings",  2),
        ).forEach { (icon, label, index) ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick  = { onTabSelected(index) },
                icon     = { Icon(icon, contentDescription = label) },
                label    = { Text(label, fontSize = 11.sp) },
                colors   = NavigationBarItemDefaults.colors(
                    selectedIconColor   = colors.primary,
                    selectedTextColor   = colors.primary,
                    unselectedIconColor = colors.secondaryText,
                    unselectedTextColor = colors.secondaryText,
                    indicatorColor      = colors.primarySoft
                )
            )
        }
    }
}