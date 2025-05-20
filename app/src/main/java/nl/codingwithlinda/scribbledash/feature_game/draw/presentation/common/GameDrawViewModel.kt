package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.CanvasManager
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.PenManager
import nl.codingwithlinda.scribbledash.core.domain.memento.CareTaker
import nl.codingwithlinda.scribbledash.core.domain.model.SingleDrawPath
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.BasicPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.MultiColorPenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.tools.MyShoppingCart
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.memento.PathDataCareTaker
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.MultiColorPathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathCreator
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.game_engine.GameEngineTemplate
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawAction
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawExampleUiState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.DrawState
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.state.GameDrawUiState

class GameDrawViewModel(
    private val careTaker: CareTaker<PathData, List<PathData>> = PathDataCareTaker(),
    private val offsetParser: OffsetParser,
    private val pathDrawer: PathCreator<SingleDrawPath>,
    private val gameEngine: GameEngineTemplate,
    private val shoppingCart: MyShoppingCart
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

        val coloredPaths = offsets.map {data->
            splitPathIntoColors(penTool, data = data.path)
        }.flatten()

        state.copy(
            drawPaths = coloredPaths,
            canRedo = careTaker.canRedo()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _uiState.value)

    private var penTool: PenProduct = PenManager.pensFreeTier[0]


    init {
        viewModelScope.launch {
            println("GameDrawViewModel init IS CALLED")
            gameEngine.start()
        }
        viewModelScope.launch {
            val penId = shoppingCart.getMyShoppingCart().penProductId ?: return@launch
            penTool = PenManager.getPenById(penId)
        }

        viewModelScope.launch {
            val canvasId = shoppingCart.getMyShoppingCart().canvasProductId ?: return@launch
            val canvasProduct = CanvasManager.getCanvasById(canvasId)

            println("GAME DRAW VM HAS CANVAS: ${canvasProduct.id}")
            _uiState.update {
                it.copy(
                    canvasProduct = canvasProduct
                )
            }
        }

    }

    fun handleAction(action: DrawAction){
        when(action){
            is DrawAction.StartPath -> {

                val pathData = PathData(
                    id = System.currentTimeMillis().toString(),
                    color = 0,
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

                    val drawPath = splitPathIntoColors(penTool, currentPathCopy.path)

                    _uiState.update {
                        it.copy(
                            currentPath = drawPath
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
                        currentPath = emptyList(),
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
                        currentPath = emptyList(),
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

    private fun splitPathIntoColors(penProduct: PenProduct, data: List<Offset>): List<ColoredPath> {

        val colors = when(penProduct){
           is BasicPenProduct -> listOf( penProduct.color)
            is MultiColorPenProduct -> penProduct.colors

            else -> emptyList()

        }.map {
            Color(it)
        }
        val multiColorPathCreator = MultiColorPathCreator(colors)
        return multiColorPathCreator.drawPath(data).paths
    }

}