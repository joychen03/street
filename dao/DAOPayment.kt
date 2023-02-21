package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOPayment
import itb.jiafumarc.street.dao.tables.PaymentTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Payment
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOPayment : IDAOPayment{
    private fun resultRowToPayment(row: ResultRow) = Payment(
        id = row[PaymentTable.id],
        userID = row[PaymentTable.userID],
        owner = row[PaymentTable.owner],
        cardNumber = row[PaymentTable.cardNumber],
        cvc = row[PaymentTable.cvc],
        expireDate = row[PaymentTable.expireDate],
        createDate = row[PaymentTable.createDate],
    )

    override suspend fun getAllPayments(): List<Payment> = dbQuery {
        PaymentTable.selectAll().map(::resultRowToPayment)
    }

    override suspend fun getPayment(id: Int): Payment? = dbQuery {
        PaymentTable
            .select { PaymentTable.id eq id }
            .map(::resultRowToPayment)
            .singleOrNull()
    }

    override suspend fun getUserPayments(userID: Int): List<Payment> = dbQuery {
        PaymentTable
            .select { PaymentTable.userID eq userID }
            .map(::resultRowToPayment)
    }

    override suspend fun addPayment(payment: Payment): Payment? = dbQuery {
        val insertStatement = PaymentTable.insert {
            it[userID] = payment.userID
            it[owner] = payment.owner
            it[cardNumber] = payment.cardNumber
            it[cvc] = payment.cvc
            it[expireDate] = payment.expireDate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPayment)
    }

    override suspend fun updatePayment(payment: Payment): Boolean = dbQuery {
        PaymentTable.update({PaymentTable.id eq payment.id!!}) {
            it[userID] = payment.userID
            it[owner] = payment.owner
            it[cardNumber] = payment.cardNumber
            it[cvc] = payment.cvc
            it[expireDate] = payment.expireDate
        } > 0
    }

    override suspend fun deletePayment(id: Int): Boolean = dbQuery {
        PaymentTable.deleteWhere { PaymentTable.id eq id } > 0
    }
}