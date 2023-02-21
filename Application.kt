package itb.jiafumarc.street

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOUser
import itb.jiafumarc.street.dao.utils.DatabaseFactory
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.plugins.*
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.file.Paths
import java.util.*
import kotlin.time.Duration

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    DatabaseFactory()

    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAge = Duration.INFINITE
        }

    }

    install(Authentication) {
        form("auth-form") {
            runBlocking {
                userParamName = "username"
                passwordParamName = "password"

                val daoUser = DAOUser()

                validate { credentials ->
                    val validUser : User? = daoUser.checkUser(credentials.name, credentials.password);
                    if (validUser != null) {
                        UserSession(validUser)
                    } else {
                        null
                    }
                }

                challenge {
                    call.respondRedirect("/auth")
                }
            }
        }

        session<UserSession>("auth-session") {
            runBlocking {
                validate { session ->
                    val daoUser = DAOUser()
                    val users = daoUser.getAllUsers()
                    val validUser = users.find { it.id == session.user.id }

                    if(validUser != null){
                        session
                    }else{
                        null
                    }
                }

                challenge {
                    call.respondRedirect("/auth")
                }
            }
        }

    }

    configureSerialization()
    configureRouting()
}


fun PartData.FileItem.save(): String {
    val fileBytes = streamProvider().readBytes()
    val fileExtension = originalFileName?.takeLastWhile { it != '.' }
    val fileName = UUID.randomUUID().toString() + "." + fileExtension
    val uploadFolder = Paths.get("").toAbsolutePath().resolve("uploads")
    File(uploadFolder.toString()).mkdir()
    File(uploadFolder.resolve(fileName).toString()).writeBytes(fileBytes)
    return fileName
}
