package nl.codingwithlinda.scribbledash.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

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