package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Address
import itb.jiafumarc.street.models.Payment
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import itb.jiafumarc.street.utils.GlobalFuntions
import kotlinx.html.*

class ProfilePageTemplate(val user : User) : Template<HTML> {

    var payments : List<Payment> = emptyList()
    var addresses : List<Address> = emptyList()
    override fun HTML.apply() {
        head {
            title{
                +"Street - Mi perfil"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container py-5 h-100") {
                    div("row d-flex justify-content-center h-100") {
                        div("col-lg-12 mb-4 mb-lg-0") {
                            div("card mb-3") {
                                style = "border-radius: 0.5rem"
                                div("row g-0") {
                                    div("col-md-4 bg-dark text-center d-flex align-items-center justify-content-center") {
                                        style = "border-top-left-radius: 0.5rem; border-bottom-left-radius: 0.5rem"
                                        div {
                                            img(classes = "img-fluid my-5 rounded-circle big-avatar") {
                                                src = if(!user.avatar.isNullOrEmpty()){
                                                    user.avatar!!
                                                }else{
                                                    "/static/img/anonymous.png"
                                                }
                                                attributes["loading"] = "lazy"
                                                alt = "Avatar"
                                            }

                                            h2 {
                                                style = "color: rgb(255 222 89)"
                                                +"${user.firstName} ${user.lastName}"
                                            }
                                            p("text-light") { +"${user.username}" }
                                            a(classes = "text-light") {
                                                href = "/myspace/edit"
                                                i("far fa-edit fw-bold fs-4 mb-5") {
                                                }
                                            }
                                        }
                                    }
                                    div("col-md-8") {
                                        div("card-body p-4") {
                                            div {
                                                span("fw-bold fs-5") { +"""Información""" }
                                                hr("mt-1 mb-4") {
                                                }
                                                div("row pt-1") {
                                                    div("col-auto mb-3 pe-4") {
                                                        h6 { +"""Nombre""" }
                                                        p("text-muted") { +"${user.firstName}" }
                                                    }
                                                    div("col-auto mb-3 pe-4") {
                                                        h6 { +"""Apellido""" }
                                                        p("text-muted") { +"${user.lastName}" }
                                                    }
                                                    div("col-auto mb-3 pe-4") {
                                                        h6 { +"""Nombre de usuario""" }
                                                        p("text-muted") { +"${user.username}" }
                                                    }
                                                }
                                            }
                                            div("mt-3") {
                                                div("d-flex justify-content-between") {
                                                    span("fw-bold fs-5") { +"""Mis pagos""" }
                                                    a(classes = "text-primary fs-3") {
                                                        href = "/myspace/payments/add"
                                                        i("fas fa-plus-square") {
                                                        }
                                                    }
                                                }
                                                hr("mt-1 mb-4") {
                                                }
                                                div("row pt-1") {
                                                    payments.forEach {
                                                        div("col-lg-6 mb-4") {
                                                            div("card") {
                                                                div("card-body") {
                                                                    i("fab fa-cc-visa fs-3 mb-2") {
                                                                    }
                                                                    h5("card-title") { +"${GlobalFuntions.formatCreditCardString(it.cardNumber)}" }
                                                                    div("d-flex w-100 justify-content-between") {
                                                                        span("card-text") { +"${it.owner}" }
                                                                        span("card-text") { +"${it.expireDate}" }
                                                                    }
                                                                    a(classes = "text-danger fs-4 float-end mt-2") {
                                                                        href = "/myspace/payments/delete/${it.id}"
                                                                        i("fa fa-trash-alt") {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            div("mt-3") {
                                                div("d-flex justify-content-between") {
                                                    span("fw-bold fs-5") { +"""Mis direcciones""" }
                                                    a(classes = "text-primary fs-3") {
                                                        href = "/myspace/addresses/add"
                                                        i("fas fa-plus-square") {
                                                        }
                                                    }
                                                }
                                                hr("mt-1 mb-4") {
                                                }
                                                div("row pt-1") {
                                                    addresses.forEach {
                                                        div("col-lg-6 mb-3") {
                                                            div("card") {
                                                                div("card-body") {
                                                                    div("col-12 text-start") {
                                                                        p("mb-0 w-100 fw-bold") { +"${it.street}" }
                                                                        p("mb-0 w-100") { +"${it.zipCode}"}
                                                                        p("mb-0 w-100") { +"${it.city}" }
                                                                        p("mb-0 w-100") { +"${it.country}" }
                                                                    }
                                                                    a(classes = "text-danger fs-4 float-end mt-2") {
                                                                        href = "/myspace/addresses/delete/${it.id}"
                                                                        i("fa fa-trash-alt") {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }

                                                }
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