package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Order
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class OrderDetailPageTemplate(val user : User) : Template<HTML> {

    var order : Order? = null
    override fun HTML.apply() {
        head {
            title{
                +"Street - Detalle de pedido"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container py-5 h-100") {
                    div("row d-flex justify-content-center align-items-center h-100") {
                        div("col-lg-10 col-xl-8") {
                            div("card") {
                                style = "border-radius: 5px"
                                div("card-header px-4 py-4 bg-dark") {
                                    div("d-flex justify-content-between align-items-center") {
                                        span("fs-4 fw-bold text-light mb-0") {
                                            +"""Gracias por tu pedido, """
                                            span {
                                                style = "color: rgb(255 222 89)"
                                                +"${user.firstName}"
                                            }
                                            +"""!"""
                                        }
                                        img {
                                            src = "/static/img/logo.png"
                                            alt = "logo"
                                            width = "80px"
                                        }
                                    }
                                }
                                div("card-body p-4") {
                                    div("d-flex justify-content-between align-items-center mb-4") {
                                        p("lead fw-normal mb-0 text-dark") { +"""Productos""" }
                                    }
                                    div {
                                        id = "orderLineContainer"
                                        order?.lines?.forEach {
                                            div("card mb-3") {
                                                div("card-body") {
                                                    div("d-flex justify-content-between") {
                                                        div("d-flex flex-row align-items-center") {
                                                            div {
                                                                img(classes = "img-fluid rounded-3") {
                                                                    src = if(!it.productBody?.image.isNullOrEmpty()){
                                                                        it.productBody?.image!!
                                                                    }else{
                                                                        "/static/img/noimage.png"
                                                                    }
                                                                    alt = "Shopping item"
                                                                    style = "width: 65px"
                                                                }
                                                            }
                                                            div("ms-3") {
                                                                h5 { +"${it.productBody?.name}" }
                                                            }
                                                        }
                                                        div("d-flex flex-row align-items-center") {
                                                            div() {
                                                                style = "width: 200px; text-align: right;"
                                                                span("fw-bold fs-5") {
                                                                    +"${it.quantity}"
                                                                }
                                                                span("fw-normal") {
                                                                    small { +" x " }
                                                                    +"${it.price}€"
                                                                }
                                                            }
                                                            div(){
                                                                style = "width: 200px; min-width: fit-contentpx; text-align: right;"
                                                                h5("mb-0") { +"${it.totalPrice }€" }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }


                                    }
                                    div("d-flex justify-content-between pt-2") {
                                        p("fw-bold mb-0") { +"""Detalles del pedido""" }
                                        p("text-muted mb-0") {
                                            span("fw-bold me-4") { +"""Total""" }
                                            +"${order?.totalPrice}€"
                                        }
                                    }
                                    div("d-flex justify-content-between pt-2") {
                                        p("text-muted mb-0") { +"""Numero del pedido : ${order?.id}""" }
                                        p("text-muted mb-0") {
                                            span("fw-bold me-4") { +"""Descuento""" }
                                            +"""0$"""
                                        }
                                    }
                                    div("d-flex justify-content-between") {
                                        p("text-muted mb-0") { +"""Fecha de creación : ${order?.createDate?.date}""" }
                                        p("text-muted mb-0") {
                                            span("fw-bold me-4") { +"""IVA 0%""" }
                                            +"""0$"""
                                        }
                                    }
                                    div("d-flex justify-content-end mb-5") {
                                        p("text-muted mb-0") {
                                            span("fw-bold me-4") { +"""Gastos de envio""" }
                                            +"""Gratis"""
                                        }
                                    }
                                }
                                div("card-footer border-0 px-4 py-5 bg-dark rounded-bottom") {
                                    style = "border-bottom-left-radius: 10px; border-bottom-right-radius: 10px"
                                    h5("d-flex align-items-center justify-content-end text-white text-uppercase mb-0") {
                                        +"""Total pagado:"""
                                        span("h2 mb-0 ms-2") {
                                            style = "color: rgb(255 222 89)"
                                            +"${order?.totalPrice}€"
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