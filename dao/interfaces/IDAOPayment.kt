package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.Payment

interface IDAOPayment {

    suspend fun getAllPayments() : List<Payment>

    suspend fun getPayment(id: Int) : Payment?

    suspend fun getUserPayments(userID : Int) : List<Payment>

    suspend fun addPayment(payment: Payment) : Payment?

    suspend fun updatePayment(payment: Payment) : Boolean

    suspend fun deletePayment(id : Int) : Boolean

}