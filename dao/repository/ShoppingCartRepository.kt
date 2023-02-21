package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.ShoppingCart

class ShoppingCartRepository {
    companion object{
        val shoppingCarts = listOf(
            ShoppingCart(
                userID = 1,
                productID = 1,
                price = 89.9,
                quantity = 2
            ),
            ShoppingCart(
                userID = 1,
                productID = 2,
                price = 69.9,
                quantity = 1
            ),
            ShoppingCart(
                userID = 1,
                productID = 4,
                price = 28.9,
                quantity = 1
            ),
            ShoppingCart(
                userID = 1,
                productID = 7,
                price = 49.9,
                quantity = 3
            )
        )
    }
}