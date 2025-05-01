package nl.codingwithlinda.scribbledash.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.codingwithlinda.scribbledash.R

val fontBagelFatOne = Font(R.font.bagel_fat_one)
val fontBagelFatOneFamily = FontFamily(fontBagelFatOne)

val fontOutfit = Font(R.font.outfit)
val fontOutfitFamily = FontFamily(fontOutfit)

val headlineXSmall = TextStyle(
    fontFamily = fontBagelFatOneFamily,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    letterSpacing = .5.sp
)
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontBagelFatOneFamily,
        fontSize = 66.sp,
        lineHeight = 80.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = fontBagelFatOneFamily,
        fontSize = 40.sp,
        lineHeight = 44.sp,
        letterSpacing = -1.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontBagelFatOneFamily,
        fontSize = 34.sp,
        lineHeight = 48.sp,
        letterSpacing = .1.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontBagelFatOneFamily,
        fontSize = 26.sp,
        lineHeight = 30.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = fontBagelFatOneFamily,
        fontSize = 18.sp,
        lineHeight = 26.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,

    ),
    bodySmall = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,

    ),
    labelLarge = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,

        ),
    labelMedium = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        ),

    labelSmall = TextStyle(
        fontFamily = fontOutfitFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    )

)