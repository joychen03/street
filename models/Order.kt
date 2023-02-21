package itb.jiafumarc.street.models


import itb.jiafumarc.street.utils.GlobalFuntions
import kotlinx.datetime.LocalDateTime

data class Order(
    val id : Int? = null,
    var userID : Int,
    var paymentID : Int,
    var addressID : Int,
    var lines : MutableList<OrderLine> = mutableListOf(),
    val createDate : LocalDateTime? = null
){
    val totalPrice : Double
        get() = GlobalFuntions.round2Decimals (this.lines.sumOf { it.totalPrice })

}

//class Order(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<Order>(OrderTable)
//
//    var userID by OrderTable.userID
//    var paymentID by OrderTable.paymentID
//    var addressID by OrderTable.addressID
//    var totalPrice by OrderTable.totalPrice
//    var createDate by OrderTable.createDate
//
//}