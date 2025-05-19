package nl.codingwithlinda.scribbledash.core.domain.model.shop.sales

import nl.codingwithlinda.scribbledash.core.data.accounts.AccountManager
import nl.codingwithlinda.scribbledash.core.domain.model.accounts.UserAccount
import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ProductInTier
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier

abstract class SalesManager<P: ProductInTier>(
    private val accountManager:AccountManager
) {

    abstract fun getProductsPerTier(): Map<Tier, List<P>>
    abstract fun freeProducts(): List<P>

    fun productsAvailableToUser(userAccount: UserAccount): List<String> {
        return userAccount.transactions.map { it.productId } + freeProducts().map { it.product.id }
    }

    fun userOwnsProduct(userAccountId: String, productId: String): Boolean {
        return accountManager.userOwnsProduct(userAccountId, productId) || freeProducts().any { it.product.id == productId }
    }

    fun userCanAffordProduct(
        userAccountId: String,
        price: Int,
    ): Boolean {

        val balance = accountManager.balance(userAccountId)

        return price <= balance
    }

    suspend fun buyProduct(productId: String, price: Int, userAccountId: String) {
        accountManager.processPurchase(userAccountId, productId, price)
    }



}
