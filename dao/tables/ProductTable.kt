package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ProductTable : Table("street_product") {

    val id = integer("id").autoIncrement()
    val name = varchar("name",100)
    val categoryID = integer("category_id").references(CategoryTable.id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE).nullable()
    val description = varchar("description",500).nullable()
    val price = double("price")
    val stock = integer("stock")
    val image = varchar("image", 200).nullable();
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}