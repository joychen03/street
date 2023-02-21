package itb.jiafumarc.street.models

import kotlinx.datetime.LocalDateTime

data class Address(
    val id : Int? = null,
    var userID : Int,
    var street : String,
    var city : String,
    var country : String,
    var zipCode : String,
    val createDate : LocalDateTime? = null
)

//class Address(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<Address>(AddressTable)
//
//    var street by AddressTable.street
//    var city by AddressTable.city
//    var country by AddressTable.country
//    var zipcode by AddressTable.zipCode
//}