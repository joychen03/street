package itb.jiafumarc.street.dao.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object OrderLineTable : Table("street_order_line") {

    val id = integer("id").autoIncrement()
    val orderID = integer("order_id").references(OrderTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val productID = integer("product_id").references(ProductTable.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val price = double("price")
    val quantity = integer("quantity")
    val totalPrice = double("total_price")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(orderID, productID)
    }

}