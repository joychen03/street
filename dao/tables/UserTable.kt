package itb.jiafumarc.street.dao.tables

import itb.jiafumarc.street.utils.GlobalFuntions
import org.jetbrains.exposed.sql.Table

object UserTable : Table("street_user") {

    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).index(isUnique = true)
    val password = varchar("password", 50)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val isAdmin = bool("is_admin")
    val avatar = varchar("avatar", 200).nullable()
    val createDate = varchar("create_date", 100).default(GlobalFuntions.getDateTimeNow())

    override val primaryKey = PrimaryKey(id)
}