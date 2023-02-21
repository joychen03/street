package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object ShoppingCartTable : Table("street_shopping_cart") {

    val id = integer("id").autoIncrement()
    val userID = integer("user_id").references(UserTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val productID = integer("product_id").references(ProductTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val quantity = integer("quantity")
    val price = double("price")
    val createDate = datetime("create_date").defaultExpression(CurrentDateTime)

    init {
        uniqueIndex(userID, productID)
    }

    override val primaryKey = PrimaryKey(id)
}