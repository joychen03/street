package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.ShoppingCart

interface IDAOShoppingCart {

    suspend fun getShoppingCart(shoppingCartID : Int) : ShoppingCart?
    suspend fun getUserShoppingCart(userID : Int) : List<ShoppingCart>
    suspend fun addProductInCart(shoppingCart: ShoppingCart) : ShoppingCart?
    suspend fun removeProduct(id : Int) : Boolean
    suspend fun removeAll(userID : Int) : Boolean
    suspend fun updateProduct(shoppingCart : ShoppingCart) : Boolean
    suspend fun checkProductExists(userID: Int, productID: Int): Boolean


}