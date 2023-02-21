package itb.jiafumarc.street.models

import kotlinx.datetime.LocalDateTime

data class Category(
    val id : Int? = null,
    var name : String,
    var image : String? = null,
    val createDate : LocalDateTime? = null
){
    var productsCount : Long = 0
}

//class Category(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<Category>(CategoryTable)
//
//    var name by CategoryTable.name
//
//
//}