package com.mirafra.demo.ui.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mirafra.demo.common.ui.AppTextField
import com.mirafra.demo.common.ui.PrimaryButton
import com.mirafra.demo.ui.theme.appColors

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val colors = appColors()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.screenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.35f))

        Text(
            text = "Sign In",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colors.primaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to continue to Engineering Manager Copilot.",
            fontSize = 15.sp,
            color = colors.secondaryText,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(36.dp))

        AppTextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            label = "Work Email",
            placeholder = "you@company.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(20.dp))

        AppTextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            placeholder = "Password",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = colors.secondaryText
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Error ─────────────────────────────────────────
        when (val state = uiState) {
            is LoginUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }
            else -> Unit
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── Button / Loader ───────────────────────────────
        when (uiState) {
            is LoginUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                PrimaryButton(
                    text = "Sign In",
                    enabled = viewModel.email.isNotBlank() && viewModel.password.isNotBlank(),
                    onClick = viewModel::signIn,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Forgot password?",
                color = colors.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.weight(0.65f))
    }
}