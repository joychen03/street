package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Order
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class OrdersPageTemplate(val user : User) : Template<HTML> {

    var orders : List<Order> = emptyList()
    override fun HTML.apply() {
        head {
            title{
                +"Street - Mis pedidos"
            }

            insert(HeadTemplate(), TemplatePlaceholder())

            link {
                href = "/static/css/orders.css"
                rel = "stylesheet"
            }

        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container py-5 h-100") {
                    div("row d-flex justify-content-center align-items-center") {
                        orders.forEach {
                            div("col-12 mb-4") {
                                div("card card-stepper") {
                                    style = "border-radius: 10px"
                                    div("card-body p-4") {
                                        div("d-flex justify-content-between align-items-center") {
                                            div("d-flex flex-column") {
                                                span("small text-muted") { +"""Order ID:""" }
                                                span("fs-4 fw-bold") { +"${it.id}" }
                                            }
                                            div("d-flex flex-column") {
                                                span("small text-muted") { +"""Cantidad articulos""" }
                                                span("fs-4") { +"${it.lines.count()}" }
                                            }
                                            div("d-flex flex-column") {
                                                span("small text-muted") { +"""Fecha de creación""" }
                                                span("fs-4") { +"${it.createDate?.date}" }
                                            }
                                            div("d-flex flex-column") {
                                                span("small text-muted") { +"""Precio total""" }
                                                span("fs-4") { +"${it.totalPrice}€" }
                                            }
                                            div {
                                                a(classes = "btn btn-primary") {
                                                    href = "/orders/my-orders/id/${it.id}"
                                                    +"""Ver detalles"""
                                                }
                                            }
                                        }
                                        hr("my-4") {
                                        }
                                        div("d-flex flex-row justify-content-between align-items-center align-content-center") {
                                            span("dot") {
                                            }
                                            hr("flex-fill track-line") {
                                            }
                                            span("dot") {
                                            }
                                            hr("flex-fill track-line") {
                                            }
                                            span("d-flex justify-content-center align-items-center big-dot dot") {
                                                i("fa fa-check text-white") {
                                                }
                                            }
                                        }
                                        div("d-flex flex-row justify-content-between align-items-center") {
                                            div("col-4 d-flex flex-column align-items-start") {
                                                span("text-muted small") { +"${it.createDate?.date}" }
                                                span("fw-bold") { +"""Preparando""" }
                                            }
                                            div("col-4 d-flex flex-column align-items-center") {
                                                span("text-muted small") { +"${it.createDate?.date}" }
                                                span("fw-bold") { +"""Enviado""" }
                                            }
                                            div("col-4 d-flex flex-column align-items-end") {
                                                span("text-muted small") { +"${it.createDate?.date}" }
                                                span("fw-bold") { +"""Entregado""" }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            insert(FooterTemplate(), TemplatePlaceholder())
        }
    }
}