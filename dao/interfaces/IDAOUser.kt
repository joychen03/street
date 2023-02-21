package itb.jiafumarc.street.dao.interfaces

import itb.jiafumarc.street.models.User

interface IDAOUser {

    suspend fun getAllUsers() : List<User>
    suspend fun getUser(id: Int) : User?
    suspend fun addUser(user : User) : User?
    suspend fun updateUser(user : User) : Boolean
    suspend fun deleteUser(id : Int) : Boolean
    suspend fun checkUser(name: String, password: String): User?
}