package itb.jiafumarc.street.models

data class User(
    val id: Int? = null,
    var username: String,
    var password: String,
    var firstName: String,
    var lastName: String,
    var avatar : String? = null,
    var isAdmin: Boolean,
    val createDate: String? = null
)

//class User(id: EntityID<Int>): IntEntity(id) {
//    companion object : IntEntityClass<User>(UserTable)
//
//    var username by UserTable.userame
//    var password by UserTable.password
//    var firstName by UserTable.firstName
//    var lastName by UserTable.lastName
//    var isAdmin by UserTable.isAdmin
//
//}