package itb.jiafumarc.street.dao.tables
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object AddressTable : Table("street_address") {

    val id = integer("id").autoIncrement()
    val userID = integer("user_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val street = varchar("street", 100)
    val city = varchar("city", 50)
    val country = varchar("country", 50)
    val zipCode = varchar("zip_code", 10)
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)

}