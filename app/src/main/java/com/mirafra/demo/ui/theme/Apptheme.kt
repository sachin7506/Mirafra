package com.mirafra.demo.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Immutable
data class AppColors(
    // Brand
    val primary: Color,
    val primaryDark: Color,
    val primaryColor: Color,

    // Backgrounds
    val screenBackground: Color,
    val cardBackground: Color,
    val secondaryBackground: Color,

    // Text
    val primaryText: Color,
    val secondaryText: Color,
    val tertiaryText: Color,
    val whiteText: Color,

    // Borders & States
    val border: Color,
    val disabled: Color,

    // Status
    val success: Color,
    val danger: Color,
    val info: Color,
    val warning: Color,

    // Soft tint
    val primarySoft: Color,
) {
    /** Mirrors Swift's primaryGradient — leading → trailing */
    val primaryGradient: Brush
        get() = Brush.horizontalGradient(listOf(primary, primaryDark))
}

// ─────────────────────────────────────────────────────────────
//  Light theme colors
// ─────────────────────────────────────────────────────────────
val LightAppColors = AppColors(
    primary             = PrimaryLight,
    primaryDark         = PrimaryDarkLight,
    primaryColor        = PrimaryColorLight,
    screenBackground    = ScreenBgLight,
    cardBackground      = CardBgLight,
    secondaryBackground = SecondaryBgLight,
    primaryText         = PrimaryTextLight,
    secondaryText       = SecondaryTextLight,
    tertiaryText        = TertiaryTextLight,
    whiteText           = WhiteText,
    border              = BorderLight,
    disabled            = DisabledLight,
    success             = SuccessLight,
    danger              = DangerLight,
    info                = InfoLight,
    warning             = WarningLight,
    primarySoft         = PrimarySoftLight,
)

// ─────────────────────────────────────────────────────────────
//  Dark theme colors
// ─────────────────────────────────────────────────────────────
val DarkAppColors = AppColors(
    primary             = PrimaryDark,
    primaryDark         = PrimaryDarkDark,
    primaryColor        = PrimaryColorDark,
    screenBackground    = ScreenBgDark,
    cardBackground      = CardBgDark,
    secondaryBackground = SecondaryBgDark,
    primaryText         = PrimaryTextDark,
    secondaryText       = SecondaryTextDark,
    tertiaryText        = TertiaryTextDark,
    whiteText           = WhiteText,
    border              = BorderDark,
    disabled            = DisabledDark,
    success             = SuccessDark,
    danger              = DangerDark,
    info                = InfoDark,
    warning             = WarningDark,
    primarySoft         = PrimarySoftDark,
)

// ─────────────────────────────────────────────────────────────
//  CompositionLocal — lets any Composable read the current colors
// ─────────────────────────────────────────────────────────────
val LocalAppColors = staticCompositionLocalOf { LightAppColors }

/**
 * Shorthand to access AppColors from any Composable:
 *
 * ```kotlin
 * val colors = appColors()
 * Text(color = colors.primaryText)
 * ```
 */
@Composable
fun appColors(): AppColors = LocalAppColors.current