package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngine
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState

class GameDrawViewModel(
    private val careTaker: CareTaker<PathData, List<PathData>> = PathDataCareTaker(),
    private val offsetParser: OffsetParser,
    private val gameEngine: GameEngine
): ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val offsets = MutableStateFlow<List<PathData>>(emptyList())
    private var currentPath: PathData? = null
    private var countUndoes: Int = 0

    private fun canUndo() = countUndoes < 5 && offsets.value.isNotEmpty()


    val uiState = combine(_uiState, offsets){ state, offsets ->
        val paths = offsets.map {
            offsetParser.parseOffset(it)
        }.let {
            coordinatesToPath(it)
        }

        state.copy(
            drawPaths = listOf(paths),
            canRedo = careTaker.canRedo()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)


    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.StartPath -> {

                val pathData = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = Color.BLACK,
                    path = listOf(action.offset)
                )

                currentPath = pathData

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

                currentPath = null
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
            }

            DrawAction.Save -> {
                //println("VIEWMODEL SAVES MEMENTO")

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

                viewModelScope.launch {
                    gameEngine.processUserInput(offsets.value)

                }

            }
            DrawAction.Undo -> {
                if (canUndo()) {
                    countUndoes++
                    val undoMemento = careTaker.undo()
                    //println("VIEWMODEL HAS UNDO MEMENTO $undoMemento")
                    //println("VIEWMODEL HAS UNDO MEMENTOS SIZE: ${undoMemento?.size}")
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
                //println("VIEWMODEL HAS REDO MEMENTO $redoMemento")
                //println("VIEWMODEL HAS REDO MEMENTOS SIZE: ${redoMemento?.size}")
                _uiState.update {
                    it.copy(
                        canUndo = canUndo(),
                        canRedo = careTaker.canRedo(),
                    )
                }

                redoMemento?.let { paths ->
                    offsets.update {
                        paths
                    }
                }
            }
        }
    }

    fun onDone(){
        currentPath = null

        gameEngine.endUserInput {

        }
    }

   /* private fun updateResultManager(){
        val result = offsets.value.map {pd->
            offsetParser.parseOffset(
                pathDrawer = pathDrawer,
                pathData = pd
            )
        }

        val newUserResult = ResultManager.INSTANCE.getLastResult()?.copy(
            userPath = result
        )
        newUserResult?.let {
            println("GAME DRAW VIEWMODEL SAVES USER PATH")

            ResultManager.INSTANCE.updateResult(it)
        }
    }*/
}