package itb.jiafumarc.street.models

import itb.jiafumarc.street.utils.GlobalFuntions
import kotlinx.datetime.LocalDateTime

data class ShoppingCart(
    val id : Int? = null,
    var userID : Int,
    var productID : Int,
    var price : Double,
    var quantity : Int,
    val createDate : LocalDateTime? = null,
    val productBody : Product? = null
){
    val totalPrice : Double
        get() = GlobalFuntions.round2Decimals(this.quantity * this.price)
}

//class ShoppingCart(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<ShoppingCart>(ShoppingCartTable)
//
//    var userID by ShoppingCartTable.userID
//    var productID by ShoppingCartTable.productID
//    var quantity by ShoppingCartTable.quantity
//
//
//}