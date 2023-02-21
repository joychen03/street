package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOAddress
import itb.jiafumarc.street.dao.tables.AddressTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.Address
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOAddress : IDAOAddress {
    private fun resultRowToAddress(row: ResultRow) = Address(
        id = row[AddressTable.id],
        userID = row[AddressTable.userID],
        street = row[AddressTable.street],
        city = row[AddressTable.city],
        country = row[AddressTable.country],
        zipCode = row[AddressTable.zipCode],
        createDate = row[AddressTable.createDate]
    )

    override suspend fun getAllAddresses(): List<Address> = dbQuery {
        AddressTable.selectAll().map(::resultRowToAddress)
    }

    override suspend fun getAddress(id: Int): Address? = dbQuery {
        AddressTable
            .select{AddressTable.id eq id}
            .map(::resultRowToAddress)
            .singleOrNull()
    }

    override suspend fun getUserAddresses(userID: Int): List<Address> = dbQuery {
        AddressTable
            .select { AddressTable.userID eq userID }
            .map(::resultRowToAddress)
    }

    override suspend fun addAddress(address: Address): Address? = dbQuery {
        val insertStatement = AddressTable.insert {
            it[userID] = address.userID
            it[street] = address.street
            it[city] = address.city
            it[country] = address.country
            it[zipCode] = address.zipCode
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAddress)
    }

    override suspend fun updateAddress(address: Address): Boolean = dbQuery {
        AddressTable.update({AddressTable.id eq address.id!!}) {
            it[userID] = address.userID
            it[street] = address.street
            it[city] = address.city
            it[country] = address.country
            it[zipCode] = address.zipCode
        } > 0
    }

    override suspend fun deleteAddress(id: Int): Boolean = dbQuery {
        AddressTable.deleteWhere { AddressTable.id eq id } > 0
    }


}