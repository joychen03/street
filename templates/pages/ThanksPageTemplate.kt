package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class ThanksPageTemplate(val user: User) : Template<HTML> {
    override fun HTML.apply() {
        head {
            title {
                + "¡Gracias por tu compra!"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {

            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container") {
                    style = "padding-top: 180px"
                    div("row") {
                        div("d-flex flex-column col-lg-12 align-items-center") {
                            img(classes = "mb-4") {
                                src = "/static/img/logo.png"
                                alt = "logo"
                                width = "300px"
                            }
                            h1("mb-4") { +"""¡Gracias por tu compra!""" }
                            a(classes = "btn btn-dark") {
                                href = "/orders/my-orders"
                                style = "color: rgb(255 222 89)"
                                +"""Mis pedidos"""
                            }
                        }
                    }
                }
            }

            insert(FooterTemplate(), TemplatePlaceholder())
        }
    }
}