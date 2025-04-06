package nl.codingwithlinda.scribbledash.core.presentation.util

import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.model.GameMode
import nl.codingwithlinda.scribbledash.core.presentation.model.GameModeUi
import nl.codingwithlinda.scribbledash.ui.theme.success

fun GameMode.toUi(): GameModeUi {
    return when (this) {
        GameMode.ONE_ROUND_WONDER -> GameModeUi(
            gameMode = this,
            title = UiText.StringResource(R.string.one_round_wonder),
            imageResourceId = R.drawable.one_round_wonder,
            color = success
        )
    }
}
