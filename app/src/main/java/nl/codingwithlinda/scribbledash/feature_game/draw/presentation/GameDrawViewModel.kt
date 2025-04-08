package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.GamePathCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.GamePathsCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.GameDrawUiState

class GameDrawViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val careTaker = GamePathsCareTaker()

    private val drawPaths = mutableMapOf<Int,ColoredDrawPath>()


    val uiState = _uiState.map{
            state ->
        state.copy(
            undoStack = drawPaths.size,
            redoStack = careTaker.redoStack.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.StartPath -> {
                val id = drawPaths.size + 1
                val newPath = ColoredDrawPath(
                    id = id,
                    color = Color.Black,
                    path = Path().apply {
                        this.moveTo(action.offset.x, action.offset.y)
                    }
                )
                drawPaths[id] = newPath

                _uiState.update {
                    it.copy(
                        currentPath = newPath,
                    )
                }
            }
            is DrawAction.Draw -> {
                val currentPath = _uiState.value.currentPath ?: ColoredDrawPath(
                    id = drawPaths.size + 1,
                    color = Color.Black,
                    path = Path()
                )
                val newPath = currentPath.path.apply {
                    this.relativeLineTo(action.offset.x, action.offset.y)
                }
                val id = currentPath.id
                drawPaths.put(id,
                    currentPath.copy(path = newPath)
                )

                _uiState.value = _uiState.value.copy(
                    drawPaths = drawPaths.values.toList(),
                    currentPath = currentPath.copy(path = newPath),
                )
            }
            DrawAction.Clear -> {
                println("VIEWMODEL CLEARS MEMENTOS")
                careTaker.clear()
                drawPaths.clear()
                _uiState.value = GameDrawUiState()
            }

            DrawAction.Save -> {
                println("VIEWMODEL SAVES MEMENTO")
                careTaker.save(drawPaths.values.toList())
                _uiState.update {
                    it.copy(
                        currentPath = null,
                    )
                }
            }

            DrawAction.Redo -> {
                val redoMemento = careTaker.redo()
                println("VIEWMODEL HAS REDO MEMENTO $redoMemento")
                redoMemento?.let { paths ->

                    val mapPaths = paths.map {
                        it.id to it
                    }.toMap()
                    drawPaths.putAll(mapPaths)

                    _uiState.update {
                        it.copy(
                            drawPaths = drawPaths.values.toList()
                        )
                    }
                }
            }

            DrawAction.Undo -> {
                val undoMemento = careTaker.undo()
                println("VIEWMODEL HAS UNDO MEMENTO $undoMemento")
                undoMemento.let { paths ->
                    val mapPaths = paths.map {
                        it.id to it
                    }.toMap()
                    drawPaths.clear()
                    drawPaths.putAll(mapPaths)
                    _uiState.update {
                        it.copy(
                            drawPaths = drawPaths.values.toList()
                        )
                    }
                }
            }

        }
    }
}