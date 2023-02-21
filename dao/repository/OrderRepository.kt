package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.Order

class OrderRepository {
    companion object{
        val orders = listOf(
            Order(
                userID = 1,
                paymentID = 1,
                addressID = 1
            ),
            Order(
                userID = 1,
                paymentID = 2,
                addressID = 2
            ),
            Order(
                userID = 1,
                paymentID = 1,
                addressID = 2
            )

        )
    }
}