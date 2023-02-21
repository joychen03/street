package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.User

class UserRepository {
    companion object{

        val users = listOf(
            User(
                username = "admin",
                password = "admin",
                firstName = "Jiafu",
                lastName = "Chen",
                avatar = "/static/img/init/avatar.jpg",
                isAdmin = true
            ),
            User(
                username = "guest",
                password = "guest",
                firstName = "Guest",
                lastName = "User",
                avatar = "",
                isAdmin = false
            )
        )
    }
}