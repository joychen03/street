package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.Address

interface IDAOAddress {

    suspend fun getAllAddresses() : List<Address>

    suspend fun getAddress(id: Int) : Address?

    suspend fun getUserAddresses(userID : Int) : List<Address>

    suspend fun addAddress(address: Address) : Address?

    suspend fun updateAddress(address: Address) : Boolean

    suspend fun deleteAddress(id : Int) : Boolean

}