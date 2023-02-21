package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOShoppingCart
import itb.jiafumarc.street.dao.tables.ProductTable
import itb.jiafumarc.street.dao.tables.ShoppingCartTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.ShoppingCart
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOShoppingCart : IDAOShoppingCart {

    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[ProductTable.id],
        name = row[ProductTable.name],
        categoryID = row[ProductTable.categoryID],
        description = row[ProductTable.description],
        price = row[ProductTable.price],
        stock = row[ProductTable.stock],
        image = row[ProductTable.image],
        createDate = row[ProductTable.createDate]
    )
    private fun resultRowToShoppingCartWithProduct(row: ResultRow) = ShoppingCart(
        id = row[ShoppingCartTable.id],
        userID = row[ShoppingCartTable.userID],
        productID = row[ShoppingCartTable.productID],
        quantity = row[ShoppingCartTable.quantity],
        price = row[ShoppingCartTable.price],
        createDate = row[ShoppingCartTable.createDate],
        productBody = resultRowToProduct(row)
    )

    private fun resultRowToShoppingCart(row: ResultRow) = ShoppingCart(
        id = row[ShoppingCartTable.id],
        userID = row[ShoppingCartTable.userID],
        productID = row[ShoppingCartTable.productID],
        quantity = row[ShoppingCartTable.quantity],
        price = row[ShoppingCartTable.price],
        createDate = row[ShoppingCartTable.createDate]
    )


    override suspend fun getUserShoppingCart(userID: Int): List<ShoppingCart> = dbQuery{
        ShoppingCartTable
            .innerJoin(ProductTable, {productID}, {id})
            .select{ShoppingCartTable.userID eq userID}
            .orderBy(ShoppingCartTable.id, SortOrder.ASC)
            .map(::resultRowToShoppingCartWithProduct)
    }

    override suspend fun getShoppingCart(shoppingCartID: Int): ShoppingCart? = dbQuery{
        ShoppingCartTable
            .innerJoin(ProductTable, {productID}, {id})
            .select{ShoppingCartTable.id eq shoppingCartID}
            .map(::resultRowToShoppingCartWithProduct)
            .singleOrNull()
    }

    override suspend fun addProductInCart(shoppingCart: ShoppingCart): ShoppingCart? = dbQuery {

        var result : ShoppingCart? = null

        val insertStatement = ShoppingCartTable.insert {
            it[userID] = shoppingCart.userID
            it[productID] = shoppingCart.productID
            it[quantity] = shoppingCart.quantity
            it[price] = shoppingCart.price
        }

        val inserted  =  insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToShoppingCart)

        if(inserted != null){
            result = getShoppingCart(inserted.id!!)
        }
        result
    }

    override suspend fun removeProduct(id: Int): Boolean = dbQuery {
        ShoppingCartTable.deleteWhere { ShoppingCartTable.id eq id } > 0
    }

    override suspend fun removeAll(userID: Int): Boolean = dbQuery {
        ShoppingCartTable.deleteWhere { ShoppingCartTable.userID eq userID } > 0
    }

    override suspend fun updateProduct(shoppingCart: ShoppingCart): Boolean = dbQuery {
        ShoppingCartTable.update({ShoppingCartTable.userID eq shoppingCart.userID and (ShoppingCartTable.productID eq shoppingCart.productID)} ) {
            with(SqlExpressionBuilder) {
                it[quantity] = quantity + shoppingCart.quantity
            }
        } > 0
    }

    override suspend fun checkProductExists(userID: Int, productID: Int): Boolean = dbQuery {
        ShoppingCartTable
            .select { (ShoppingCartTable.productID eq productID) and (ShoppingCartTable.userID eq userID) }
            .map { ::resultRowToShoppingCartWithProduct }
            .isNotEmpty()
    }


}