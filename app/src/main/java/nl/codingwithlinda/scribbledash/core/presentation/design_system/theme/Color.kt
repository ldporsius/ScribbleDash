package nl.codingwithlinda.scribbledash.core.presentation.design_system.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.R

val primary = Color(0xFF238cff)
val onPrimary = Color(0xFFffffff)
val secondary = Color(0xFFab5cfa)
val tertiaryContainer = Color(0xFFfa852c)
val error = Color(0xFFef1242)
val success = Color(0xFF0dd280)

val backgroundLight = Color(0xFFfefaf6)
val backgroundDark = Color(0xFFfff1e2)
val onBackground = Color(0xFF514437)
val onBackgroundVariant = Color(0xFF7f7163)
val surfaceHigh = Color(0xFFffffff)
val surfaceLow = Color(0xFFeee7e0)
val surfaceLowest = Color(0xFFe1d5ca)
val onSurface = Color(0xFFa5978a)
val onSurfaceVariant = Color(0xFFf6f1ec)

val backgroundGradient = Brush.verticalGradient(
    colors = listOf(backgroundLight, backgroundDark)

)

val oneRoundWonderColor = Color(0xFF0dd280)
val speedDrawColor = Color(0xFF238cff)
val endlessModeColor = Color(0xFFfa852c)

val topscoreColor = Color(0xFFED6363)

val statisticsColors = listOf(
    Color(0xFF8E24AA).copy(alpha = 0.1f),
    Color(0xFF238cff).copy(alpha = 0.1f),
    Color(0xFFfa852c).copy(alpha = 0.1f),
    Color(0xFFFDD835).copy(alpha = 0.1f),
)


// Basic colors
val White = Color(0xFFFFFFFF) // Default, always unlocked
val LightGray = Color(0xFFE0E0E0)
val PaleBeige = Color(0xFFF5F5DC)
val SoftPowderBlue = Color(0xFFB0C4DE)
val LightSageGreen = Color(0xFFD3E8D2)
val PalePeach = Color(0xFFF4E1D9)
val SoftLavender = Color(0xFFE7D8E9)

// Premium colors
val FadedOlive = Color(0xFFB8CBB8)
val MutedMauve = Color(0xFFD1B2C1)
val DustyBlue = Color(0xFFA3BFD9)
val SoftKhaki = Color(0xFFD8D6C1)
val MutedCoral = Color(0xFFF2C5C3)
val PaleMint = Color(0xFFD9EDE1)
val SoftLilac = Color(0xFFE2D3E8)

// Legendary textures
//val WoodTextureResId = R.drawable.bg_wood_texture
//val VintageNotebookResId = R.drawable.bg_vintage_notebook

// Basic Pen Colors
val MidnightBlack = Color(0xFF101820) // Default, always unlocked
val CrimsonRed = Color(0xFFB22234)
val SunshineYellow = Color(0xFFF9D85D)
val OceanBlue = Color(0xFF1D4E89)
val EmeraldGreen = Color(0xFF4CAF50)
val FlameOrange = Color(0xFFF57F20)

// Premium Pen Colors
val RoseQuartz = Color(0xFFF4A6B8)
val RoyalPurple = Color(0xFF6A0FAB)
val TealDream = Color(0xFF008C92)
val GoldenGlow = Color(0xFFFFD700)
val CoralReef = Color(0xFFFF6F61)
val MajesticIndigo = Color(0xFF4B0082)
val CopperAura = Color(0xFFB87333)

// Legendary Pen Colors
val RainbowPenBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFB02FB), // Magenta
        Color(0xFF0000FF), // Blue
        Color(0xFF00EEFF), // Cyan
        Color(0xFF008000), // Green
        Color(0xFFFFFF00), // Yellow
        Color(0xFFFFA500), // Orange
        Color(0xFFFF0000), // Red
    )
)