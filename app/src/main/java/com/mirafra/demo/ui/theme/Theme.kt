package com.mirafra.demo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// ─────────────────────────────────────────────────────────────
//  Material 3 colour schemes — wired to your AppColors palette
// ─────────────────────────────────────────────────────────────
private val AppLightColorScheme = lightColorScheme(
    primary             = PrimaryLight,
    onPrimary           = WhiteText,
    primaryContainer    = PrimarySoftLight,
    onPrimaryContainer  = PrimaryTextLight,

    secondary           = PrimaryDarkLight,
    onSecondary         = WhiteText,

    background          = ScreenBgLight,
    onBackground        = PrimaryTextLight,

    surface             = CardBgLight,
    onSurface           = PrimaryTextLight,
    surfaceVariant      = SecondaryBgLight,
    onSurfaceVariant    = SecondaryTextLight,

    outline             = BorderLight,
    outlineVariant      = DisabledLight,

    error               = DangerLight,
    onError             = WhiteText,
)

private val AppDarkColorScheme = darkColorScheme(
    primary             = PrimaryDark,
    onPrimary           = WhiteText,
    primaryContainer    = PrimarySoftDark,
    onPrimaryContainer  = PrimaryTextDark,

    secondary           = PrimaryDarkDark,
    onSecondary         = WhiteText,

    background          = ScreenBgDark,
    onBackground        = PrimaryTextDark,

    surface             = CardBgDark,
    onSurface           = PrimaryTextDark,
    surfaceVariant      = SecondaryBgDark,
    onSurfaceVariant    = SecondaryTextDark,

    outline             = BorderDark,
    outlineVariant      = DisabledDark,

    error               = DangerDark,
    onError             = WhiteText,
)

// ─────────────────────────────────────────────────────────────
//  DemoTheme — wrap your app (or individual screens) with this
// ─────────────────────────────────────────────────────────────
@Composable
fun DemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors   = if (darkTheme) DarkAppColors     else LightAppColors
    val colorScheme = if (darkTheme) AppDarkColorScheme else AppLightColorScheme

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            content     = content
        )
    }
}