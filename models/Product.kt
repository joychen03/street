package itb.jiafumarc.street.models

import kotlinx.datetime.LocalDateTime

data class Product(
    val id: Int? = null,
    var name: String,
    var categoryID: Int? = null,
    var description: String? = null,
    var price: Double,
    var stock: Int,
    var image : String? = null,
    val createDate: LocalDateTime? = null
){
    var currentStock : Int? = null
}

//class Product(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<Product>(ProductTable)
//
//    var name by ProductTable.name
//    var categoriesId by ProductTable.categoriesId
//    var description by ProductTable.description
//    var price by ProductTable.price
//    var stock by ProductTable.stock
//    var createDate by ProductTable.createDate
//
//}