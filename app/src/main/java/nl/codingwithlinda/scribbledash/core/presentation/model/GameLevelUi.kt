package nl.codingwithlinda.scribbledash.core.presentation.model

import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

data class GameLevelUi(
    val gameLevel: GameLevel,
    val title: UiText,
    val imageResourceId: Int
)
