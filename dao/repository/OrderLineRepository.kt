package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.OrderLine

class OrderLineRepository {
    companion object{
        val orderLines = mutableListOf(
            OrderLine(
                orderID = 1,
                productID = 1,
                price = 89.9,
                quantity = 2,
            ),
            OrderLine(
                orderID = 1,
                productID = 3,
                price = 78.9,
                quantity = 1,
            ),
            OrderLine(
                orderID = 1,
                productID = 5,
                price = 26.9,
                quantity = 3,
            ),
            OrderLine(
                orderID = 1,
                productID = 8,
                price = 49.9,
                quantity = 1,
            ),
            OrderLine(
                orderID = 2,
                productID = 4,
                price = 8.9,
                quantity = 300,
            ),
            OrderLine(
                orderID = 2,
                productID = 5,
                price = 8.9,
                quantity = 300,
            ),
            OrderLine(
                orderID = 2,
                productID = 1,
                price = 29.9,
                quantity = 50,
            ),
            OrderLine(
                orderID = 3,
                productID = 6,
                price = 55.0,
                quantity = 1,
            ),
            OrderLine(
                orderID = 3,
                productID = 7,
                price = 45.9,
                quantity = 1,
            ),
            OrderLine(
                orderID = 3,
                productID = 2,
                price = 69.9,
                quantity = 1,
            )
        )
    }
}