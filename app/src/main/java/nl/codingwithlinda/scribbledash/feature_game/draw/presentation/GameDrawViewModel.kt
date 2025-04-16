package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.GameDrawUiState

class GameDrawViewModel(
    private val careTaker: CareTaker<PathData, List<PathData>> = PathDataCareTaker(),
): ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val offsets = MutableStateFlow<List<PathData>>(emptyList())
    private var currentPath: PathData? = null
    private var countUndoes: Int = 0

    private fun canUndo() = countUndoes < 5 && offsets.value.isNotEmpty()

    val uiState = combine(_uiState, offsets){ state, offsets ->
        state.copy(
            drawPaths = offsets,
            canRedo = careTaker.canRedo()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.StartPath -> {

                val pathData = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = android.graphics.Color.BLACK,
                    path = listOf(action.offset)
                )

                currentPath = pathData
                println("VIEWMODEL STARTED PATH")
            }
            is DrawAction.Draw -> {
                viewModelScope.launch {

                    val _currentPath = currentPath ?: return@launch

                    val currentPathCopy = _currentPath.copy(
                        path = _currentPath.path.plusElement(action.offset)
                    )
                    currentPath = currentPathCopy

                    _uiState.update {
                        it.copy(
                            currentPath = currentPathCopy
                        )
                    }
                }
            }
            DrawAction.Clear -> {
                println("VIEWMODEL CLEARS MEMENTOS")
                careTaker.clear()
                offsets.update {
                    emptyList()
                }
                _uiState.update {
                    it.copy(
                        currentPath = null,
                        canRedo = careTaker.canRedo(),
                    )
                }

                println("VIEWMODEL CAN REDO : ${careTaker.canRedo()}")
            }

            DrawAction.Save -> {
                println("VIEWMODEL SAVES MEMENTO")
                println("VIEWMODEL SAVES MEMENTO. canUndo: ${canUndo()}")

                countUndoes = 0
                currentPath?.let { pathData ->
                    offsets.update {
                        it.plus(pathData)
                    }
                    careTaker.save(pathData)
                }

                _uiState.update {
                    it.copy(
                        canUndo = canUndo(),
                        currentPath = null,
                    )
                }
            }
            DrawAction.Undo -> {
                if (canUndo()) {
                    countUndoes++
                    val undoMemento = careTaker.undo()
                    println("VIEWMODEL HAS UNDO MEMENTO $undoMemento")
                    println("VIEWMODEL HAS UNDO MEMENTOS SIZE: ${undoMemento?.size}")
                    undoMemento?.let { paths ->
                        offsets.update {
                            undoMemento
                        }
                    }

                    _uiState.update {
                        it.copy(
                            canUndo = canUndo(),
                            canRedo = careTaker.canRedo(),
                        )
                    }
                }
            }
            DrawAction.Redo -> {
                countUndoes = countUndoes.minus(1).coerceAtLeast(0)
                val redoMemento = careTaker.redo()
                println("VIEWMODEL HAS REDO MEMENTO $redoMemento")
                println("VIEWMODEL HAS REDO MEMENTOS SIZE: ${redoMemento?.size}")
                _uiState.update {
                    it.copy(
                        canUndo = canUndo(),
                        canRedo = careTaker.canRedo(),
                    )
                }

                redoMemento?.let { paths ->
                    offsets.update {
                        redoMemento
                    }
                }

            }

        }
    }
}