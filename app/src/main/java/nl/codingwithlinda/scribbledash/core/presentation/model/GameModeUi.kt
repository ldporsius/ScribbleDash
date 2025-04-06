package nl.codingwithlinda.scribbledash.core.presentation.model

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

data class GameModeUi(
    val gameMode: GameMode,
    val title: UiText,
    val imageResourceId: Int,
    val color: Color
)
