package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.ShoppingCart
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopAction
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopEvent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopUiState

class ShopViewModel(
    private val penSalesManager: SalesManager<PenInTier>,
    private val canvasSalesManager: SalesManager<CanvasInTier>,
    private val userAccount: UserAccount,

): ViewModel() {
    private val shoppingCart = ShoppingCart()

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState = _uiState.asStateFlow()

    val eventChannel = Channel<ShopEvent>()

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
        updatePenUi()
        updateCanvasUi()
    }

    fun handleAction(action: ShopAction){
        when(action){
            is ShopAction.ItemClickCanvas -> {
                viewModelScope.launch {
                    if (canvasSalesManager.userOwnsProduct(productId = action.productId, userAccountId = userAccount.id)){
                        return@launch

                    }

                    if (canvasSalesManager.userCanAffordProduct(
                            userAccountId = userAccount.id,
                            price = action.price
                        )
                    ) {
                        canvasSalesManager.buyProduct(
                            productId = action.productId,
                            userAccountId = userAccount.id,
                            price = action.price
                        )
                        updateCanvasUi()
                    } else {
                        eventChannel.send(ShopEvent.BalanceInsufficient)
                    }
                }
            }
            is ShopAction.ItemClickPen -> {
                viewModelScope.launch {

                    if (penSalesManager.userOwnsProduct(productId = action.productId, userAccountId = userAccount.id)){
                        println("SHOP VIEWMODEL UPDATES SELECTED PEN ID: ${action.productId}")
                        _uiState.update {
                            it.copy(
                                selectedPenId = action.productId
                            )
                        }

                        return@launch

                    }
                    if (penSalesManager.userCanAffordProduct(
                            userAccountId = userAccount.id,
                            price = action.price
                        )
                    ) {
                        penSalesManager.buyProduct(
                            productId = action.productId,
                            userAccountId = userAccount.id,
                            price = action.price
                        )
                        updatePenUi()
                    } else {
                        eventChannel.send(ShopEvent.BalanceInsufficient)
                    }
                }
            }
        }
    }

    private fun putPenInBasket(penProduct: PenProduct): ShoppingCart {
        return shoppingCart.copy(
            pen = penProduct
        )
    }

    private fun updatePenUi(){
        penSalesManager.productsAvailableToUser(userAccount).let { products ->
            _uiState.update {
                it.copy(
                    availablePenProducts = products
                )
            }
        }
    }
    private fun updateCanvasUi(){
        canvasSalesManager.productsAvailableToUser(userAccount).let { products ->
            _uiState.update {
                it.copy(
                    availableCanvasProducts = products
                )
            }
        }
    }
}