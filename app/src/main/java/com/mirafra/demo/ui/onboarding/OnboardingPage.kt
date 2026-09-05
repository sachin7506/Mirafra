package com.mirafra.demo.ui.onboarding

import androidx.annotation.DrawableRes
import com.mirafra.demo.R

data class OnboardingPage(
    @DrawableRes val illustrationRes: Int,
    val featureTitle: String,
    val featureSubtitle: String
)

val onboardingPages = listOf(
    OnboardingPage(
        illustrationRes = R.drawable.ic_getstarted1,
        featureTitle = "Review architecture with AI",
        featureSubtitle = "Turn engineering documents into a focused review brief."
    ),
    OnboardingPage(
        illustrationRes = R.drawable.ic_getstarted2,
        featureTitle = "Find evidence-backed risks",
        featureSubtitle = "Every finding is linked back to the source document."
    ),
    OnboardingPage(
        illustrationRes = R.drawable.ic_getstarted3,
        featureTitle = "Make better decisions",
        featureSubtitle = "Capture decisions, owners and follow-ups in one place."
    )
)