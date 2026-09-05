package com.mirafra.demo.ui.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mirafra.demo.ui.theme.appColors

private const val OBJECTIVE_LIMIT  = 300
private const val ANALYSIS_LIMIT   = 200

@Composable
fun CreateProjectScreen(
    onBack: () -> Unit,
    onProjectCreated: () -> Unit,
    viewModel: CreateProjectViewModel = hiltViewModel()
) {
    val colors  = appColors()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (uiState is CreateProjectUiState.Success) {
            onProjectCreated()
            viewModel.resetState()
        }
    }

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
                text = "Create Project",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primaryText,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // ── Form ──────────────────────────────────────────
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ── Project Name ──────────────────────────────
            FormLabel(text = "Project Name")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.projectName,
                onValueChange = viewModel::onProjectNameChange,
                placeholder = {
                    Text("e.g. Payment Platform Review", color = colors.tertiaryText, fontSize = 15.sp)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Objective ─────────────────────────────────
            FormLabel(text = "Objective")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.objective,
                onValueChange = { if (it.length <= OBJECTIVE_LIMIT) viewModel.onObjectiveChange(it) },
                placeholder = {
                    Text("What is the goal of this review?", color = colors.tertiaryText, fontSize = 15.sp)
                },
                modifier = Modifier.fillMaxWidth().height(130.dp),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )
            Text(
                text = "${viewModel.objective.length}/$OBJECTIVE_LIMIT",
                fontSize = 12.sp,
                color = colors.tertiaryText,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Analysis Focus ────────────────────────────
            FormLabel(text = "Analysis Focus (Optional)")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.analysisFocus,
                onValueChange = { if (it.length <= ANALYSIS_LIMIT) viewModel.onAnalysisFocusChange(it) },
                placeholder = {
                    Text("e.g. Focus on security and scalability", color = colors.tertiaryText, fontSize = 15.sp)
                },
                modifier = Modifier.fillMaxWidth().height(130.dp),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors()
            )
            Text(
                text = "${viewModel.analysisFocus.length}/$ANALYSIS_LIMIT",
                fontSize = 12.sp,
                color = colors.tertiaryText,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Helps AI focus the review on what matters most.",
                fontSize = 13.sp,
                color = colors.secondaryText
            )

            // ── Error ─────────────────────────────────────
            if (uiState is CreateProjectUiState.Error) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = (uiState as CreateProjectUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ── Button / Loader ───────────────────────────
            when (uiState) {
                is CreateProjectUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (viewModel.isFormValid) colors.primary
                                else colors.disabled
                            )
                            .clickable(
                                enabled = viewModel.isFormValid,
                                onClick = viewModel::createProject
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Create Project",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars))
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = appColors().primaryText
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor   = appColors().cardBackground,
    unfocusedContainerColor = appColors().cardBackground,
    focusedBorderColor      = appColors().primary,
    unfocusedBorderColor    = appColors().border,
    focusedTextColor        = appColors().primaryText,
    unfocusedTextColor      = appColors().primaryText,
    cursorColor             = appColors().primary
)