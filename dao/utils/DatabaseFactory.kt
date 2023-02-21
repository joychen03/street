package itb.jiafumarc.street.dao.utils


import itb.jiafumarc.street.dao.repository.*
import itb.jiafumarc.street.dao.tables.*
import itb.jiafumarc.street.utils.Constants
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    val driverClassName = "org.postgresql.Driver"
    val jdbcURL = Constants.JDBC_URL
    lateinit var database : Database

    operator fun invoke(){
        database = Database.connect(jdbcURL, driverClassName, Constants.DB_USER, Constants.DB_PASSWORD)
        createTables()
    }

    fun reset(){

        transaction(database) {
            SchemaUtils.drop(
                CategoryTable,
                UserTable,
                ProductTable,
                PaymentTable,
                AddressTable,
                OrderTable,
                OrderLineTable,
                ShoppingCartTable
            )

        }

        createTables()
        loadInitialData()
    }


    private fun createTables(){

        transaction(database) {
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(UserTable)
            SchemaUtils.create(ProductTable)
            SchemaUtils.create(PaymentTable)
            SchemaUtils.create(AddressTable)
            SchemaUtils.create(OrderTable)
            SchemaUtils.create(OrderLineTable)
            SchemaUtils.create(ShoppingCartTable)
        }

    }

    private fun loadInitialData() {

        transaction(database) {

            UserTable.batchInsert(UserRepository.users){
                this[UserTable.username] = it.username
                this[UserTable.password] = it.password
                this[UserTable.firstName] = it.firstName
                this[UserTable.lastName] = it.lastName
                this[UserTable.avatar] = it.avatar
                this[UserTable.isAdmin] = it.isAdmin
            }

            CategoryTable.batchInsert(CategoryRepository.categories){
                this[CategoryTable.name] = it.name
                this[CategoryTable.image] = it.image
            }

            ProductTable.batchInsert(ProductRepository.products){
                this[ProductTable.name] = it.name
                this[ProductTable.description] = it.description
                this[ProductTable.categoryID] = it.categoryID
                this[ProductTable.price] = it.price
                this[ProductTable.stock] = it.stock
                this[ProductTable.image] = it.image
            }

            ShoppingCartTable.batchInsert(ShoppingCartRepository.shoppingCarts){
                this[ShoppingCartTable.userID] = it.userID
                this[ShoppingCartTable.productID] = it.productID
                this[ShoppingCartTable.quantity] = it.quantity
                this[ShoppingCartTable.price] = it.price
            }

            AddressTable.batchInsert(AddressRepository.addresses){
                this[AddressTable.userID] = it.userID
                this[AddressTable.street] = it.street
                this[AddressTable.city] = it.city
                this[AddressTable.country] = it.country
                this[AddressTable.zipCode] = it.zipCode
            }

            PaymentTable.batchInsert(PaymentRepository.payments){
                this[PaymentTable.userID] = it.userID
                this[PaymentTable.owner] = it.owner
                this[PaymentTable.cardNumber] = it.cardNumber
                this[PaymentTable.cvc] = it.cvc
                this[PaymentTable.expireDate] = it.expireDate
            }

            OrderTable.batchInsert(OrderRepository.orders){
                this[OrderTable.userID] = it.userID
                this[OrderTable.paymentID] = it.paymentID
                this[OrderTable.addressID] = it.addressID
            }

            OrderLineTable.batchInsert(OrderLineRepository.orderLines){
                this[OrderLineTable.orderID] = it.orderID
                this[OrderLineTable.quantity] = it.quantity
                this[OrderLineTable.price] = it.price
                this[OrderLineTable.productID] = it.productID
                this[OrderLineTable.totalPrice] = it.totalPrice
            }
        }
   }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }

}