package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.Order
import itb.jiafumarc.street.models.ShoppingCart

interface IDAOOrder {

    suspend fun getAllOrders() : List<Order>

    suspend fun getOrder(id: Int) : Order?

    suspend fun getUserOrders(userID : Int) : List<Order>

    suspend fun addOrder(order: Order, cartItems : List<ShoppingCart>) : Order?


}