package itb.jiafumarc.street.dao

import itb.jiafumarc.street.dao.interfaces.IDAOUser
import itb.jiafumarc.street.dao.tables.UserTable
import itb.jiafumarc.street.dao.utils.DatabaseFactory.dbQuery
import itb.jiafumarc.street.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOUser : IDAOUser{
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[UserTable.id],
        username = row[UserTable.username],
        password = row[UserTable.password],
        firstName = row[UserTable.firstName],
        lastName = row[UserTable.lastName],
        isAdmin = row[UserTable.isAdmin],
        avatar = row[UserTable.avatar],
        createDate = row[UserTable.createDate]
    )

    override suspend fun getAllUsers(): List<User> = dbQuery {
        UserTable.selectAll().map(::resultRowToUser)
    }

    override suspend fun getUser(id: Int): User? = dbQuery {
        UserTable
            .select { UserTable.id eq id}
            .map(::resultRowToUser)
            .singleOrNull()
    }
    override suspend fun addUser(user: User): User? = dbQuery {

        val insertStatement = UserTable.insert {
            it[username] = user.username
            it[password] = user.password
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[avatar] = user.avatar
            it[isAdmin] = user.isAdmin
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        UserTable.update({ UserTable.id eq user.id!! }) {
            it[password] = user.password
            it[firstName] = user.firstName
            it[lastName] = user.lastName
            it[avatar] = user.avatar
            it[isAdmin] = user.isAdmin
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        UserTable.deleteWhere { UserTable.id eq id } > 0
    }

    override suspend fun checkUser(name: String, password: String): User? = dbQuery{
        UserTable
            .select { (UserTable.username eq name) and (UserTable.password eq password)}
            .map(::resultRowToUser)
            .singleOrNull()
    }

}