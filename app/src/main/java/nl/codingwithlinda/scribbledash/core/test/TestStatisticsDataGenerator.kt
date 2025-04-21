package nl.codingwithlinda.scribbledash.core.test

import nl.codingwithlinda.scribbledash.R
import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.model.StatisticInfoUi

fun fakeStatisticInfo(icon: Int, iconBackgroundColor: Color) =
    StatisticInfoUi(
            icon = icon,
            iconBackgroundColor = iconBackgroundColor,
            info = "Nothing to track... for now",
            value = "0%",
        )

fun fakeListStatisticInfo() = listOf(
    fakeStatisticInfo(R.drawable.hourglass, Color.Blue),
    fakeStatisticInfo(R.drawable.lightning, Color.Cyan)
)