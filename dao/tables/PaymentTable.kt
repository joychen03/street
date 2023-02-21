package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PaymentTable : Table("street_payment") {

    val id = integer("id").autoIncrement()
    val userID = integer("user_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val owner = varchar("owner", 50);
    val cardNumber = varchar("card_num", 50);
    val cvc = varchar("cvc", 3);
    val expireDate = varchar("expire_date", 20);
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}