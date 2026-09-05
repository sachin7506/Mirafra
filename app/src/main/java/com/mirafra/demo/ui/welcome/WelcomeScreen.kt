package com.mirafra.demo.ui.welcome


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mirafra.demo.R
import com.mirafra.demo.common.ui.PrimaryButton
import com.mirafra.demo.common.ui.SecondaryButton
import com.mirafra.demo.navigation.Screen
import com.mirafra.demo.ui.theme.appColors

@Composable
fun WelcomeScreen(navController: NavController) {
    val colors = appColors()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.screenBackground)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // ── App title ────────────────────────────────────
        Text(
            text = "Engineering\nManager Copilot",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 42.sp,
            color = colors.primaryText
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Tagline lines ─────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            listOf(
                "AI-powered reviews.",
                "Evidence-backed insights.",
                "Better decisions."
            ).forEach { line ->
                Text(
                    text = line,
                    fontSize = 16.sp,
                    color = colors.secondaryText
                )
            }
        }

        // ── Illustration ──────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_welcome_illustration),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentScale = ContentScale.Fit
            )
        }
        // ── Primary CTA ───────────────────────────────────
        PrimaryButton(
            text = "Get Started",
            onClick = { navController.navigate(Screen.Onboarding.route) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Secondary CTA ─────────────────────────────────
        SecondaryButton(
            text = "Sign In",
            onClick = { navController.navigate(Screen.Login.route) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Terms ─────────────────────────────────────────
        Text(
            text = buildAnnotatedString {
                append("By continuing, you agree to our\n")
                withStyle(SpanStyle(color = colors.primary)) {
                    append("Terms of Service and Privacy Policy")
                }
            },
            fontSize = 12.sp,
            color = colors.tertiaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}