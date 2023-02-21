package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOProduct
import itb.jiafumarc.street.dao.tables.OrderLineTable
import itb.jiafumarc.street.dao.tables.ProductTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Product
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOProduct : IDAOProduct{
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

    private fun resultRowToProductWithCurrentStock(row: ResultRow) = Product(
        id = row[ProductTable.id],
        name = row[ProductTable.name],
        categoryID = row[ProductTable.categoryID],
        description = row[ProductTable.description],
        price = row[ProductTable.price],
        stock = row[ProductTable.stock],
        image = row[ProductTable.image],
        createDate = row[ProductTable.createDate]
    ).apply {
        if(row[OrderLineTable.quantity.sum()] == null){
            currentStock = stock
        }else{
            currentStock = stock - row[OrderLineTable.quantity.sum()]!!
        }

    }

    override suspend fun getAllProducts(): List<Product> = dbQuery{
        ProductTable
            .selectAll()
            .orderBy(ProductTable.id, SortOrder.ASC)
            .map(::resultRowToProduct)
    }

    override suspend fun getProduct(id: Int): Product? = dbQuery {
        ProductTable
            .select { ProductTable.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun getProductByCategory(categoryID: Int): List<Product> = dbQuery {
        ProductTable
            .select { ProductTable.categoryID eq categoryID }
            .orderBy(ProductTable.id, SortOrder.DESC)
            .map(::resultRowToProduct)
    }

    override suspend fun getAllProductsWithCurrentStock(): List<Product> = dbQuery{
        ProductTable
            .leftJoin(OrderLineTable, { id }, { productID })
            .slice(ProductTable.id,ProductTable.name, ProductTable.description, ProductTable.stock, ProductTable.price, ProductTable.image, ProductTable.categoryID, ProductTable.createDate, OrderLineTable.quantity.sum())
            .selectAll()
            .groupBy(ProductTable.id,ProductTable.name, ProductTable.description, ProductTable.stock, ProductTable.price, ProductTable.image, ProductTable.categoryID, ProductTable.createDate)
            .orderBy(ProductTable.id, SortOrder.ASC)
            .map(::resultRowToProductWithCurrentStock)
    }

    override suspend fun addProduct(product: Product): Product? = dbQuery {
        val insertStatement = ProductTable.insert {
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[stock] = product.stock
            it[image] = product.image
            it[categoryID] = product.categoryID

        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun updateProduct(product: Product): Boolean = dbQuery {
        ProductTable.update({ProductTable.id eq product.id!!}) {
            it[name] = product.name
            it[description] = product.description
            it[price] = product.price
            it[stock] = product.stock
            it[image] = product.image
            it[categoryID] = product.categoryID
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        ProductTable.deleteWhere { ProductTable.id eq id } > 0
    }


}