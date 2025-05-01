package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state

import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import org.junit.Assert.*
import org.junit.Test

class GameDrawUiStateTest{
    private val uiState = GameDrawUiState()

    @Test
    fun `test boolean`(){
        val state = uiState.copy(
            drawPaths = emptyList(),
            canRedo = false,
            currentPath = null
        )

        assertFalse(state.isUndoAvailable())
        assertFalse(state.isRedoAvailable())
        assertFalse(state.isClearAvailable())

    }
}