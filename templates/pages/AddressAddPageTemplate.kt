package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class AddressAddPageTemplate(val user : User) : Template<HTML> {
    override fun HTML.apply() {
        head {
            title{
                +"Street - Añadir nueva dirección"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5") {
                    div("row justify-content-center") {
                        div("col-lg-6") {
                            h3("mb-3") { +"""Añadir nueva direccion""" }
                            div("card") {
                                div("card-body") {
                                    form(classes = "mt-4") {
                                        autoComplete = false
                                        action = "/myspace/addresses/add"
                                        method = FormMethod.post
                                        encType = FormEncType.multipartFormData
                                        div("form-outline mb-4") {
                                            input(classes = "form-control form-control-lg") {
                                                name = "address_street"
                                                type = InputType.text
                                                id = "address_street"
                                                placeholder = "Calle de pepito, 14"
                                                required = true
                                            }
                                            label("form-label") {
                                                htmlFor = "address_street"
                                                +"""Dirección"""
                                            }
                                        }
                                        div("form-outline mb-4") {
                                            input(classes = "form-control form-control-lg") {
                                                name = "address_city"
                                                type = InputType.text
                                                id = "address_city"
                                                placeholder = "Barcelona"
                                                required = true
                                            }
                                            label("form-label") {
                                                htmlFor = "address_city"
                                                +"""Ciudad"""
                                            }
                                        }
                                        div("row mb-4") {
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        name = "address_country"
                                                        type = InputType.text
                                                        id = "address_country"
                                                        placeholder = "España"
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "address_country"
                                                        +"""Pais"""
                                                    }
                                                }
                                            }
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        name = "address_zip_code"
                                                        type = InputType.password
                                                        id = "address_zip_code"
                                                        placeholder = "08500"
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "address_zip_code"
                                                        +"""Codigo postal"""
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