package itb.jiafumarc.street.models

import itb.jiafumarc.street.utils.GlobalFuntions

data class OrderLine(
    val id : Int? = null,
    val orderID : Int,
    val productID : Int,
    val price : Double,
    var quantity: Int,
    var productBody : Product? = null
){
    val totalPrice : Double
        get() = GlobalFuntions.round2Decimals( this.price * this.quantity )
}

//class OrderLine(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<OrderLine>(OrderLineTable)
//
//    var orderID by OrderLineTable.orderID
//    var productID by OrderLineTable.productID
//    var price by OrderLineTable.price
//    var units by OrderLineTable.units
//    var totalPrice by OrderLineTable.totalPrice
//
//}

