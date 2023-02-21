package itb.jiafumarc.street.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOProduct
import itb.jiafumarc.street.dao.DAOUser
import itb.jiafumarc.street.dao.utils.DatabaseFactory
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.templates.pages.ErrorPageTemplate
import itb.jiafumarc.street.templates.pages.MainPageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum
import kotlinx.html.*
import java.io.File
import java.nio.file.Paths


fun Route.mainRouting() {

    val userDao = DAOUser()
    route("/") {

        get("") {
            val daoProduct = DAOProduct()
            var products = daoProduct.getAllProducts()
            val userSession : UserSession? = call.sessions.get("user_session") as? UserSession

            val page = MainPageTemplate().apply {
                this.products = products
                this.currentUser = userSession?.user
            }
            call.respondHtmlTemplate(page){

            }

        }

        get("uploads/{filename}"){
            val filename = call.parameters["filename"] ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
            val filePath = Paths.get("").toAbsolutePath().resolve("uploads").resolve(filename).toString()
            val file = File(filePath)
            if(file.exists()) {
                call.respondFile(file)
            }
            else call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
        }

        get("reset") {
            DatabaseFactory.reset()
            val uploadFolder = Paths.get("").toAbsolutePath().resolve("uploads").toString()
            File(uploadFolder).deleteRecursively()
            call.sessions.clear<UserSession>()
            call.respondHtml{
                head {
                    title{
                        + "RESET"
                    }
                }
                body {
                    style = "font-family: system-ui;"
                    h1 { +"Base de datos reseteado" }
                    br {  }
                    h2 { +"Usuario demo (Administrador)" }
                    h3 { +"Nombre usuario : admin" }
                    h3 { +"Contrasenya : admin" }
                    br {  }
                    h2 { +"Usuario demo vacia (Cliente)" }
                    h3 { +"Nombre usuario : guest" }
                    h3 { +"Contrasenya : guest" }
                    br {  }
                    a {
                        href = "/"
                        h1 { +"INICIO" }
                    }
                }
            }
        }

        get("error/{status}") {
            try {
                val id = call.parameters["status"]

                var status : HttpStatusCode
                var template : Template<HTML>

                when(id){
                    HttpCodeEnum.NOT_FOUND.toString() ->{
                        template = ErrorPageTemplate( HttpStatusCode.InternalServerError)
                        status = HttpStatusCode.NotFound
                    }
                    HttpCodeEnum.UNAUTHORIZED.toString() ->{
                        template = ErrorPageTemplate(HttpStatusCode.Unauthorized)
                        status = HttpStatusCode.Unauthorized
                    }
                    else -> {
                        template = ErrorPageTemplate(HttpStatusCode.InternalServerError)
                        status = HttpStatusCode.InternalServerError
                    }
                }

                call.respondHtmlTemplate(template, status = status){}

            }catch (e :Exception){
                call.respondHtmlTemplate(ErrorPageTemplate(HttpStatusCode.InternalServerError), status = HttpStatusCode.InternalServerError){}
            }

        }
    }

}

