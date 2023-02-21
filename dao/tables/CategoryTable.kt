package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object CategoryTable : Table("street_category") {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val image = varchar("image", 200).nullable()
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}