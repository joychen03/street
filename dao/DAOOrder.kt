package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOOrder
import itb.jiafumarc.street.dao.tables.OrderLineTable
import itb.jiafumarc.street.dao.tables.OrderTable
import itb.jiafumarc.street.dao.tables.ProductTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Order
import itb.jiafumarc.street.models.OrderLine
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.ShoppingCart
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOOrder : IDAOOrder {
    private fun resultRowToOrder(row: ResultRow) : Order = Order(
        id = row[OrderTable.id],
        userID = row[OrderTable.userID],
        paymentID = row[OrderTable.userID],
        addressID = row[OrderTable.userID],
        createDate = row[OrderTable.createDate]
    )

    private fun resultRowToOrderLine(row: ResultRow) : OrderLine = OrderLine(
        id = row[OrderLineTable.id],
        orderID = row[OrderLineTable.orderID],
        productID = row[OrderLineTable.productID],
        price = row[OrderLineTable.price],
        quantity = row[OrderLineTable.quantity],
    )

    private fun resultRowToOrderLineWithProduct(row: ResultRow) : OrderLine = OrderLine(
        id = row[OrderLineTable.id],
        orderID = row[OrderLineTable.orderID],
        productID = row[OrderLineTable.productID],
        price = row[OrderLineTable.price],
        quantity = row[OrderLineTable.quantity],
        productBody = resultRowToProduct(row)
    )

    private fun resultRowToProduct(row: ResultRow) : Product = Product(
        id = row[ProductTable.id],
        name = row[ProductTable.name],
        description = row[ProductTable.description],
        categoryID = row[ProductTable.categoryID],
        price = row[ProductTable.price],
        stock = row[ProductTable.stock],
        image = row[ProductTable.image],
        createDate = row[ProductTable.createDate]
    )

    override suspend fun getAllOrders(): List<Order> = dbQuery {
        val orders = mutableListOf<Order>()
        var currentOrderID : Int? = null;
        var order : Order? = null;

        OrderTable
            .innerJoin(OrderLineTable,{id},{ productID})
            .innerJoin(ProductTable, {OrderLineTable.productID}, {id})
            .selectAll()
            .orderBy((OrderTable.id to SortOrder.DESC),(OrderLineTable.id to SortOrder.ASC))
            .forEach { row ->
                if(row[OrderTable.id] != currentOrderID){
                    order = resultRowToOrder(row)
                    orders.add(order!!)
                    order?.lines?.add(resultRowToOrderLineWithProduct(row))
                    currentOrderID = row[OrderTable.id]
                }else{
                    order?.lines?.add(resultRowToOrderLineWithProduct(row))
                }

            }


        orders
    }

    override suspend fun getOrder(id: Int): Order? = dbQuery {
        var order : Order? = null;
        OrderLineTable
            .innerJoin(OrderTable,{orderID},{ OrderTable.id })
            .innerJoin(ProductTable, {OrderLineTable.productID}, { ProductTable.id })
            .select { OrderTable.id eq id }
            .orderBy(OrderLineTable.id to SortOrder.ASC)
            .forEach { row ->
                if(order == null){
                    order = resultRowToOrder(row)
                }
                order?.lines?.add(resultRowToOrderLineWithProduct(row))
            }
        order
    }

    override suspend fun getUserOrders(userID: Int): List<Order> = dbQuery {
        val orders = mutableListOf<Order>()
        var currentOrderID : Int? = null;
        var order : Order? = null;

        OrderLineTable
            .innerJoin(OrderTable,{orderID},{id})
            .innerJoin(ProductTable, {OrderLineTable.productID}, {id})
            .select { OrderTable.userID eq userID }
            .orderBy((OrderTable.id to SortOrder.DESC),(OrderLineTable.id to SortOrder.ASC))
            .forEach { row ->
                if(row[OrderTable.id] != currentOrderID){
                    order = resultRowToOrder(row)
                    orders.add(order!!)
                    order?.lines?.add(resultRowToOrderLineWithProduct(row))
                    currentOrderID = row[OrderTable.id]
                }else{
                    order?.lines?.add(resultRowToOrderLineWithProduct(row))
                }
            }
        orders
    }

    override suspend fun addOrder(order: Order, cartItems: List<ShoppingCart>): Order? = dbQuery {

        transaction(DatabaseFactory.database) {
            val orderIS = OrderTable.insert {
                it[userID] = order.userID
                it[addressID] = order.addressID
                it[paymentID] = order.paymentID
            }

            val insertedOrder = orderIS.resultedValues?.singleOrNull()?.let(::resultRowToOrder)

            if(insertedOrder == null){
                rollback()
                return@transaction null
            }

            val orderLines = mutableListOf<OrderLine>()

            for(cartItem in cartItems){
                orderLines.add(
                    OrderLine(
                        orderID = insertedOrder.id!!,
                        productID = cartItem.productID,
                        quantity = cartItem.quantity,
                        price = cartItem.price
                    )
                )
            }

            val orderLineIS = OrderLineTable.batchInsert(orderLines){
                this[OrderLineTable.orderID] = it.orderID
                this[OrderLineTable.productID] = it.productID
                this[OrderLineTable.price] = it.price
                this[OrderLineTable.quantity] = it.quantity
                this[OrderLineTable.totalPrice] = it.quantity * it.price
            }.map(::resultRowToOrderLine)


            insertedOrder.lines.addAll(orderLineIS)
            insertedOrder
        }

    }


}