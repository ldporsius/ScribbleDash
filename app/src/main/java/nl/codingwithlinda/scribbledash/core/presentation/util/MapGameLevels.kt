package nl.codingwithlinda.scribbledash.core.presentation.util

import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.presentation.model.GameLevelUi

fun GameLevel.toUi(): GameLevelUi {
    return when (this) {
        GameLevel.BEGINNER -> GameLevelUi(
            gameLevel = GameLevel.BEGINNER,
            title = UiText.StringResource(R.string.beginner),
            imageResourceId = R.drawable.game_level_1
        )
        GameLevel.CHALLENGING -> GameLevelUi(
            gameLevel = GameLevel.CHALLENGING,
            title = UiText.StringResource(R.string.challenging),
            imageResourceId = R.drawable.game_level_2
        )
        GameLevel.MASTER -> GameLevelUi(
            gameLevel = GameLevel.MASTER,
            title = UiText.StringResource(R.string.master),
            imageResourceId = R.drawable.game_level_3
        )
    }
}