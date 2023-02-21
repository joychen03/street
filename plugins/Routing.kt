package itb.jiafumarc.street.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import itb.jiafumarc.street.routes.*

fun Application.configureRouting() {

    routing {

        mainRouting()
        productRoutes()
        adminRoutes()
        shoppingCartRoute()
        orderRoutes()
        userRoutes()
        authRoutes()

        static("/static") {
            resources("files")
        }

    }
}
