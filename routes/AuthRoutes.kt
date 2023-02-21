package itb.jiafumarc.street.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOUser
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.templates.pages.LoginPageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.authRoutes() {

    route("/auth"){

        get(""){
            call.respondHtmlTemplate(LoginPageTemplate()){

            }
        }

        authenticate("auth-form") {
            post("login") {
                try {
                    val currentUser = call.principal<UserSession>()?.user
                    if(currentUser != null){
                        println("register")
                        call.sessions.set(UserSession(currentUser))
                    }

                    call.respondRedirect("/")
                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e;
                }

            }

        }

        authenticate("auth-session"){
            get("logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/")
            }
        }

        post("register"){
            try {
                val dao = DAOUser()

                val fd = call.receiveParameters()
                val firstName = fd["first_name"] ?: throw Exception()
                val lastName = fd["last_name"] ?: throw Exception()
                val username = fd["username"] ?: throw Exception()
                val password = fd["password"] ?: throw Exception()

                val newUser = User(
                    username = username,
                    firstName = firstName,
                    lastName = lastName,
                    password = password,
                    isAdmin = false
                )

                dao.addUser(newUser)

                call.respondRedirect("/auth")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e;
            }
        }

    }

}