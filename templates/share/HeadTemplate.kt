package itb.jiafumarc.street.templates.share

import io.ktor.server.html.*
import kotlinx.html.HEAD
import kotlinx.html.link
import kotlinx.html.meta


class HeadTemplate : Template<HEAD> {

    override fun HEAD.apply() {
        meta {
            charset = "utf-8"
        }
        meta {
            name = "viewport"
            content = "width=device-width, initial-scale=1"
        }

        link {
            rel = "icon"
            type = "image/x-icon"
            href = "/static/img/favicon.ico"
        }
        link {
            href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel = "stylesheet"
        }
        link {
            href = "https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel = "stylesheet"
        }
        link {
            href = "https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.1.0/mdb.min.css"
            rel = "stylesheet"
        }

        link {
            href = "/static/css/global.css"
            rel = "stylesheet"
        }

    }
}