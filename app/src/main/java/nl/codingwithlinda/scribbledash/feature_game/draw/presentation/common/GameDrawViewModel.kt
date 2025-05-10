package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import android.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.core.domain.model.SingleDrawPath
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.mapping.coordinatesToPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawExampleUiState

class GameDrawViewModel(
    private val careTaker: CareTaker<PathData, List<PathData>> = PathDataCareTaker(),
    private val offsetParser: OffsetParser,
    private val pathDrawer: PathCreator<SingleDrawPath>,
    private val gameEngine: GameEngineTemplate
): ViewModel() {
    private val _uiState = MutableStateFlow(GameDrawUiState())
    private val offsets = MutableStateFlow<List<PathData>>(emptyList())
    private var currentPath: PathData? = null
    private var countUndoes: Int = 0

    private fun canUndo() = countUndoes < 5 && offsets.value.isNotEmpty()

    private val exampleFlow = gameEngine.exampleFlow.receiveAsFlow()
    private val _exampleUiState = MutableStateFlow(DrawExampleUiState())
    val exampleUiState = combine(_exampleUiState, exampleFlow, gameEngine.countDown){state, example, count ->
        state.copy(
            drawPath = example,
            counter = count
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _exampleUiState.value)

    val drawState = gameEngine.shouldShowExample.transform {
        if (it){
            emit(DrawState.EXAMPLE)
        }
        else{
            emit(DrawState.USER_INPUT)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DrawState.EXAMPLE)

    val uiState = combine(_uiState, offsets){ state, offsets ->
        val paths = offsets.map {
            offsetParser.parseOffset(it)
        }.let {
            coordinatesToPath(it)
        }.asComposePath()

        state.copy(
            drawPaths = listOf(paths),
            canRedo = careTaker.canRedo()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    init {
        viewModelScope.launch {
            gameEngine.start()
        }
    }

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

                    val drawPath = pathDrawer.drawPath(currentPathCopy.path).path
                    _uiState.update {
                        it.copy(
                            currentPath = drawPath.asComposePath()
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
        viewModelScope.launch(NonCancellable) {
            gameEngine.processUserInput(offsets.value)
            gameEngine.onUserInputDone()
        }
        currentPath = null

    }

}