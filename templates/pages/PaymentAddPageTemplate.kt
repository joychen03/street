package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class PaymentAddPageTemplate(val user : User) : Template<HTML> {
    override fun HTML.apply() {
        head {
            title{
                +"Street - Añadir nueva tarjeta"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5") {
                    div("row justify-content-center") {
                        div("col-lg-6") {
                            h3("mb-3") { +"""Añadir nuevo pago""" }
                            div("card") {
                                div("card-body") {
                                    form(classes = "mt-4") {
                                        autoComplete = false
                                        action = "/myspace/payments/add"
                                        encType = FormEncType.multipartFormData
                                        method = FormMethod.post
                                        div("form-outline mb-4") {
                                            input(classes = "form-control form-control-lg") {
                                                name = "payment_owner"
                                                type = InputType.text
                                                id = "payment_owner"
                                                placeholder = "Jiafu Chen"
                                                required = true
                                            }
                                            label("form-label") {
                                                htmlFor = "payment_owner"
                                                +"""Titular"""
                                            }
                                        }
                                        div("form-outline mb-4") {
                                            input(classes = "form-control form-control-lg") {
                                                name = "payment_card_num"
                                                type = InputType.text
                                                id = "payment_card_num"
                                                size = "16"
                                                placeholder = "1234 5678 9012 3457"
                                                minLength = "16"
                                                maxLength = "16"
                                                required = true
                                            }
                                            label("form-label") {
                                                htmlFor = "payment_card_num"
                                                +"""Numero de la tarjeta"""
                                            }
                                        }
                                        div("row mb-4") {
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        name = "payment_exp"
                                                        type = InputType.text
                                                        id = "payment_exp"
                                                        placeholder = "MM/YYYY"
                                                        size = "5"
                                                        id = "exp"
                                                        minLength = "5"
                                                        maxLength = "5"
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "payment_exp"
                                                        +"""Expiración"""
                                                    }
                                                }
                                            }
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        name = "payment_cvv"
                                                        type = InputType.password
                                                        id = "payment_cvv"
                                                        placeholder = "●●●"
                                                        size = "1"
                                                        minLength = "3"
                                                        maxLength = "3"
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "payment_cvv"
                                                        +"""CVV"""
                                                    }
                                                }
                                            }
                                        }
                                        button(classes = "btn btn-primary btn-block mb-4") {
                                            type = ButtonType.submit
                                            +"""AÑADIR"""
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