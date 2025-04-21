package nl.codingwithlinda.scribbledash.feature_statistics.presentation.model

import androidx.compose.ui.graphics.Color

data class StatisticInfoUi(
    val icon: Int,
    val iconBackgroundColor: Color,
    val info: String,
    val value: String,
)
