package nl.codingwithlinda.scribbledash.core.data.shop.sales.accounts

import nl.codingwithlinda.scribbledash.core.domain.model.shop.products.ShopProduct
import nl.codingwithlinda.scribbledash.core.domain.model.shop.UserAccount

class AccountManager {

    val userAccount1 = UserAccount(
        id = "1",
    )
    private val userAccounts = listOf(
        userAccount1
    )

    fun updateUserAccount(userAccount: UserAccount, product: ShopProduct) {
        val userAccount = userAccounts.find { it.id == userAccount.id } ?: return
        userAccount.transactions.add(product)
    }
}