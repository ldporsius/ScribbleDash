package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import nl.codingwithlinda.scribbledash.core.domain.model.shop.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
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
        penSalesManager.productsBoughtByUser(userAccount).let {products ->
            _uiState.update {
                it.copy(
                    availablePenProducts = products
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
}