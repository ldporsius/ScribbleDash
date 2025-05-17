package nl.codingwithlinda.scribbledash.core.domain.model.shop.sales

import androidx.compose.ui.util.fastSumBy
import nl.codingwithlinda.scribbledash.core.data.shop.product_manager.PenManager
import nl.codingwithlinda.scribbledash.core.data.shop.sales.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.domain.model.shop.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.PenInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

abstract class SalesManager<P: ProductInTier> {
    private val accountManager = AccountManager()

    abstract fun getProductsPerTier(): Map<Tier, List<P>>

    private fun freeProducts() = PenManager.pensFreeTier.map {
        PenInTier(
            tier = Tier.FREE,
            product = it
        )
    }

    fun productsBoughtByUser(userAccount: UserAccount): List<String> {
        return userAccount.transactions.map { it.id } + freeProducts().map { it.product.id }
    }
    fun productAvailableForUser(userAccount: UserAccount, product: ShopProduct): Boolean {
        val boughtProducts = userAccount.transactions.toList()
        return boughtProducts.contains(product)
    }

    fun userCanAffordProduct(userAccount: UserAccount,
                             product: ShopProduct
    ): Boolean {

        val price = product.price
        val expenses = userAccount.transactions.fastSumBy {
            it.price
        }
        val balance = userAccount.coins - expenses

        return price + expenses <= balance
    }

    fun buyProduct(product: ShopProduct, userAccount: UserAccount) {
        accountManager.updateUserAccount(userAccount, product)
    }

}
