package itb.jiafumarc.street.models

import kotlinx.datetime.LocalDateTime

data class Payment(
    val id : Int? = null,
    var userID : Int,
    var owner : String,
    var cardNumber : String,
    var cvc : String,
    var expireDate : String,
    val createDate : LocalDateTime? = null
)

//class Payment(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<Payment>(PaymentTable)
//
//    var userID by PaymentTable.userID
//    var owner by PaymentTable.owner
//    var cardNumber by PaymentTable.cardNumber
//    var cvc by PaymentTable.cvc
//    var expireDate by PaymentTable.expireDate
//    var createDate by PaymentTable.createDate
//}