package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.domain.model.shop.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopAction
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopUiState

class ShopViewModel(
    private val penSalesManager: SalesManager<PenInTier>,
    private val canvasSalesManager: SalesManager<CanvasInTier>,
    private val userAccount: UserAccount
): ViewModel() {


    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState = _uiState.asStateFlow()

    init {
        penSalesManager.getProductsPerTier().let { pens ->
            _uiState.update {
                it.copy(
                    penProducts = pens.values.flatten()
                )
            }
        }


        canvasSalesManager.getProductsPerTier().let {canvasses ->
            _uiState.update {
                it.copy(
                    canvasProducts = canvasses.values.flatten()
                )
            }
        }
    }

    fun handleAction(action: ShopAction){
        when(action){
            is ShopAction.ItemClickCanvas -> {
                val product = _uiState.value.canvasProducts.find { it.product.id == action.productId } ?: return
                penSalesManager.buyProduct(product.product, userAccount)
            }
            is ShopAction.ItemClickPen -> {
                val product = _uiState.value.penProducts.find { it.product.id == action.productId } ?: return
                penSalesManager.buyProduct(product.product, userAccount)
            }
        }
    }

    private fun updateUi(){
        penSalesManager.productsBoughtByUser(userAccount).let {products ->
            _uiState.update {
                it.copy(
                    availablePenProducts = products
                )
            }
        }
    }
}