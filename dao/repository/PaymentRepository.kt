package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.Payment

class PaymentRepository {
    companion object{
        val payments = listOf(
            Payment(
                userID = 1,
                owner = "Jiafu Chen",
                cardNumber = "1234512345123555",
                cvc = "056",
                expireDate = "12/28",
            ),
            Payment(
                userID = 1,
                owner = "Marc Areny",
                cardNumber = "4665773688379288",
                cvc = "233",
                expireDate = "03/28",
            )

        )
    }
}