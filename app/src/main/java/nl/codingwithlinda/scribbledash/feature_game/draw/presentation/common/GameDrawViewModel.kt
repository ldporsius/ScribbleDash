package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultManager
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.ColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState

class GameDrawViewModel(
    private val careTaker: CareTaker<PathData, List<PathData>> = PathDataCareTaker(),
    private val offsetParser: OffsetParser<AndroidDrawPath>,
    private val pathDrawer: PathDrawer<AndroidDrawPath>,
    private val navToResult: () -> Unit
): ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val offsets = MutableStateFlow<List<PathData>>(emptyList())
    private var currentPath: PathData? = null
    private var countUndoes: Int = 0

    private fun canUndo() = countUndoes < 5 && offsets.value.isNotEmpty()

    val coloredPaths: Flow<List<AndroidDrawPath>> = offsets.map { list ->
       list.map {
           offsetParser.parseOffset(
               pathDrawer = pathDrawer,
               pathData = it
           )
       }
    }
    val uiState = combine(_uiState, coloredPaths){ state, offsets ->
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

                    val androidPath = offsetParser.parseOffset(
                        pathDrawer = pathDrawer,
                        pathData = currentPathCopy
                    )
                    _uiState.update {
                        it.copy(
                            currentPath = androidPath
                        )
                    }
                }
            }
            DrawAction.Clear -> {

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

                //println("VIEWMODEL CAN REDO : ${careTaker.canRedo()}")
            }

            DrawAction.Save -> {
                //println("VIEWMODEL SAVES MEMENTO")
                //println("VIEWMODEL SAVES MEMENTO. canUndo: ${canUndo()}")

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
                        redoMemento
                    }
                }
            }

            DrawAction.Done -> {
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
                    ResultManager.INSTANCE.updateResult(it)
                }

                navToResult()
            }

        }
    }
}