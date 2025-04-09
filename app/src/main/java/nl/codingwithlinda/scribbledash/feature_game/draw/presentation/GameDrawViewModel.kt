package nl.codingwithlinda.scribbledash.feature_game.draw.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.feature_game.draw.data.ColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker2
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.StraightPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.state.GameDrawUiState

class GameDrawViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val careTaker = PathDataCareTaker()
    private val pathDrawer = StraightPathDrawer()

    private val offsets = MutableStateFlow<List<PathData>>(emptyList())
    private var currentPath: PathData? = null

   val uiState = combine(_uiState, offsets){ state, offsets ->
        state.copy(
            drawPaths = parseOffsets(offsets),
            canRedo = careTaker.canRedo()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    private fun parseOffsets(offsets: List<PathData>): List<ColoredDrawPath>{
        return offsets.map {
          parseOffset(it)
        }
    }

    private fun parseOffset(offset: PathData): ColoredDrawPath{
        val path = pathDrawer.drawPath(offset.path, offset.color)
        return ColoredDrawPath(
            color = Color(offset.color),
            path = path.path
        )
    }

    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.StartPath -> {

                val pathData = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = android.graphics.Color.BLACK,
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
                    val coloredPath = parseOffset(
                        currentPathCopy
                    )
                    _uiState.update {
                       it.copy(
                           currentPath = coloredPath
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
               currentPath?.let { pathData ->
                    offsets.update {
                        it.plus(pathData)
                    }
                   careTaker.save(pathData)
                }

                _uiState.update {
                    it.copy(
                        currentPath = null,
                    )
                }
            }
            DrawAction.Undo -> {
                val undoMemento = careTaker.undo()
                println("VIEWMODEL HAS UNDO MEMENTO $undoMemento")
                println("VIEWMODEL HAS UNDO MEMENTOS SIZE: ${undoMemento.size}")
                undoMemento.let { paths ->
                    offsets.update {
                        undoMemento
                    }
                }

                _uiState.update {
                    it.copy(
                        canRedo = careTaker.canRedo(),
                    )
                }
            }
            DrawAction.Redo -> {
                val redoMemento = careTaker.redo()
                println("VIEWMODEL HAS REDO MEMENTO $redoMemento")
                println("VIEWMODEL HAS REDO MEMENTOS SIZE: ${redoMemento.size}")
                redoMemento.let { paths ->
                   offsets.update {
                       redoMemento
                   }
                }
                _uiState.update {
                    it.copy(
                        canRedo = careTaker.canRedo(),
                    )
                }
            }

        }
    }
}