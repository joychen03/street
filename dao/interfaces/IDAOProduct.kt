package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.Product

interface IDAOProduct {

    suspend fun getAllProducts() : List<Product>

    suspend fun getProduct(id: Int) : Product?

    suspend fun getProductByCategory(categoryID : Int) : List<Product>

    suspend fun getAllProductsWithCurrentStock() : List<Product>

    suspend fun addProduct(product : Product) : Product?

    suspend fun updateProduct(product: Product) : Boolean

    suspend fun deleteProduct(id : Int) : Boolean

}