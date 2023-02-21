package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOCategory
import itb.jiafumarc.street.dao.tables.CategoryTable
import itb.jiafumarc.street.dao.tables.ProductTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOCategory : IDAOCategory {
    private fun resultRowToCategoryWithCount(row: ResultRow) = Category(
        id = row[CategoryTable.id],
        name = row[CategoryTable.name],
        image = row[CategoryTable.image],
        createDate = row[CategoryTable.createDate]
    ).apply {
        productsCount = row[ProductTable.id.count()]
    }

    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[CategoryTable.id],
        name = row[CategoryTable.name],
        image = row[CategoryTable.image],
        createDate = row[CategoryTable.createDate]
    )

    override suspend fun getAllCategories(): List<Category> = dbQuery {
        CategoryTable
            .selectAll()
            .map(::resultRowToCategory)
    }

    override suspend fun getAllCategoriesWithCount(): List<Category> = dbQuery {
        CategoryTable
            .leftJoin(ProductTable, {id}, {categoryID})
            .slice(CategoryTable.id, CategoryTable.name, CategoryTable.image, CategoryTable.createDate, ProductTable.id.count())
            .selectAll()
            .groupBy(CategoryTable.id, CategoryTable.name, CategoryTable.image, CategoryTable.createDate)
            .orderBy(CategoryTable.id, SortOrder.ASC)
            .map(::resultRowToCategoryWithCount)
    }

    override suspend fun getCategory(id: Int): Category? = dbQuery {
        CategoryTable
            .select { CategoryTable.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun addCategory(category: Category): Category? = dbQuery {
        val insertStatement = CategoryTable.insert {
            it[name] = category.name
            it[image] = category.image
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun updateCategory(category: Category): Boolean = dbQuery {
        CategoryTable.update({CategoryTable.id eq category.id!!}) {
            it[name] = category.name
            it[image] = category.image
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean = dbQuery {
        CategoryTable.deleteWhere { CategoryTable.id eq id } > 0
    }


}