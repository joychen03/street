package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object OrderTable : Table("street_order") {

    val id = integer("id").autoIncrement()
    val userID = integer("user_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val paymentID = integer("payment_id").references(PaymentTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val addressID = integer("address_id").references(AddressTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}