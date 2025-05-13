package nl.codingwithlinda.scribbledash.feature_statistics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.games_manager.GamesManager
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.model.StatisticInfoUi
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.statisticsColors

class StatisticsViewModel(
    private val gamesManager: GamesManager
): ViewModel() {

    private val _statistics = MutableStateFlow<List<StatisticInfoUi>>(emptyList())

    val statistics = _statistics.asStateFlow()
    init {
        viewModelScope.launch {

            val highestSpeedDrawAccuracy = gamesManager.highestAccuracyForGameMode(GameMode.SPEED_DRAW)
            val numSuccessSpeed = gamesManager.highestNumberOfSuccessesForMode(GameMode.SPEED_DRAW)

            val speedAccuracyInfo = StatisticInfoUi(
                icon = R.drawable.hourglass,
                iconBackgroundColor = statisticsColors[0],
                info = "Highest Speed Draw accuracy %",
                value = "$highestSpeedDrawAccuracy%"
            )
            val mostMehPlusSpeedInfo = StatisticInfoUi(
                icon = R.drawable.lightning,
                iconBackgroundColor = statisticsColors[1],
                info = "Most Meh+ drawings in Speed Draw",
                value = numSuccessSpeed.toString()
            )
            val highestEndlessAccuracy = gamesManager.highestAccuracyForGameMode(GameMode.ENDLESS_MODE)
            val numSuccessEndless = gamesManager.highestNumberOfSuccessesForMode(GameMode.ENDLESS_MODE)

            val endlessAccuracyInfo = StatisticInfoUi(
                icon = R.drawable.star,
                iconBackgroundColor = statisticsColors[2],
                info = "Highest Endless Mode accuracy %",
                value = "$highestEndlessAccuracy%"
            )
            val mostDrawingsEndlessInfo = StatisticInfoUi(
                icon = R.drawable.game_level_3,
                iconBackgroundColor = statisticsColors[3],
                info = "Most Meh+ drawings in Endless Mode",
                value = numSuccessEndless.toString()
            )

            val results = listOf(
                speedAccuracyInfo,
                mostMehPlusSpeedInfo,
                endlessAccuracyInfo,
                mostDrawingsEndlessInfo
            )
            _statistics.update {
               results
            }
        }
    }
}