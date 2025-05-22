package nl.codingwithlinda.scribbledash.core.domain.model.tools

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductType
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.sales.ShoppingCart

class MyShoppingCart(
    private val dataStore: DataStore<Preferences>
) {

    companion object{
         val KEY_SHOPPING_CART = stringPreferencesKey("shopping_cart")
    }
    private var cart = ShoppingCart()
    suspend fun putProductInCart(product: ShopProduct){
        when(product.type){
            ProductType.PEN -> {
                cart = cart.copy(penProductId = product.id)
                dataStore.edit {
                    it[KEY_SHOPPING_CART] = cart.toString()
                }
            }
            ProductType.CANVAS -> {
                cart = cart.copy(canvasProductId = product.id)
                dataStore.edit {
                    it[KEY_SHOPPING_CART] = cart.toString()
                }
            }
        }
    }

    suspend fun getMyShoppingCart(): ShoppingCart {
        dataStore.data.firstOrNull()?.let { preferences ->
            preferences.get(KEY_SHOPPING_CART)?.let {
                val (pen, canvas) = it.split(";")

                return ShoppingCart(
                    penProductId = pen,
                    canvasProductId = canvas
                )
            }
        }
        return cart
    }

}