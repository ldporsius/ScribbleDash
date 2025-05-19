package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.CanvasProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.PenProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductType
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.SalesManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.ShoppingCart
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.CanvasInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.core.domain.model.tools.MyShoppingCart
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopAction
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopEvent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopUiState

class ShopViewModel(
    private val penSalesManager: SalesManager<PenInTier>,
    private val canvasSalesManager: SalesManager<CanvasInTier>,
    private val userAccount: UserAccount,
    private val shoppingCart: MyShoppingCart

): ViewModel() {

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
            is ShopAction.ItemClick -> {
                viewModelScope.launch {
                    when(action.product.type){
                        ProductType.PEN -> penHandleAction(action.product, action.price)
                        ProductType.CANVAS -> canvasHandleAction(action.product, action.price)
                    }
                }
            }
        }
    }

    private fun penHandleAction(product: ShopProduct, price: Int){
        val productId = product.id
        viewModelScope.launch {

            if (penSalesManager.userOwnsProduct(productId = productId, userAccountId = userAccount.id)){

                updateSelectedPen(productId)
                putProductInBasket(product)
                return@launch

            }
            if (penSalesManager.userCanAffordProduct(
                    userAccountId = userAccount.id,
                    price = price
                )
            ) {
                penSalesManager.buyProduct(
                    productId = productId,
                    userAccountId = userAccount.id,
                    price = price
                )
                updatePenUi()
                updateSelectedPen(productId)
                putProductInBasket(product)
            } else {
                eventChannel.send(ShopEvent.BalanceInsufficient)
            }
        }
    }

    private fun canvasHandleAction(product: ShopProduct, price: Int){
        val productId = product.id
        viewModelScope.launch {
            if (canvasSalesManager.userOwnsProduct(productId = productId, userAccountId = userAccount.id)){
                updateSelectedCanvas(productId)
                putProductInBasket(product)
                return@launch

            }

            if (canvasSalesManager.userCanAffordProduct(
                    userAccountId = userAccount.id,
                    price = price
                )
            ) {
                canvasSalesManager.buyProduct(
                    productId = productId,
                    userAccountId = userAccount.id,
                    price = price
                )
                updateCanvasUi()
                updateSelectedCanvas(productId)
                putProductInBasket(product)
            } else {
                eventChannel.send(ShopEvent.BalanceInsufficient)
            }
        }
    }

    private fun putProductInBasket(product: ShopProduct) = viewModelScope.launch {
      shoppingCart.putProductInCart(product)
    }

    private fun updateSelectedPen(penProductId: String){
        _uiState.update {
            it.copy(
                selectedPenId = penProductId
            )
        }
    }

    private fun updateSelectedCanvas(productId: String){
        _uiState.update {
            it.copy(
                selectedCanvasId = productId
            )
        }
    }
    private fun updatePenUi(){
        penSalesManager.productsAvailableToUser(userAccount).let { products ->
            println("PEN PRODUCTS AVAILABLE TO USER: ${products}")
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