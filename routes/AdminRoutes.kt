package itb.jiafumarc.street.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOCategory
import itb.jiafumarc.street.dao.DAOProduct
import itb.jiafumarc.street.dao.DAOUser
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.templates.pages.AdminPageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.adminRoutes() {

    route("/admin") {
        authenticate("auth-session") {
            get("") {
                val session = call.sessions.get("user_session") as UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoProduct = DAOProduct()
                val daoCategory = DAOCategory()
                val daoUser = DAOUser()

                val products = daoProduct.getAllProductsWithCurrentStock()
                val categories = daoCategory.getAllCategoriesWithCount()
                val users = daoUser.getAllUsers()

                val template = AdminPageTemplate(session.user).apply {
                    this.products = products
                    this.categories = categories
                    this.users = users
                }

                call.respondHtmlTemplate(template){

                }
            }
        }

    }
}