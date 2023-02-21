package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.Category


interface IDAOCategory {

    suspend fun getAllCategories() : List<Category>
    suspend fun getAllCategoriesWithCount() : List<Category>
    suspend fun getCategory(id : Int) : Category?

    suspend fun addCategory(category: Category) : Category?

    suspend fun updateCategory(category: Category) : Boolean

    suspend fun deleteCategory(id : Int) : Boolean

}