package itb.jiafumarc.street.models

import io.ktor.server.auth.*

data class UserSession(val user : User) : Principal