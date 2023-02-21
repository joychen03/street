package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Address
import itb.jiafumarc.street.models.Payment
import itb.jiafumarc.street.models.ShoppingCart
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import itb.jiafumarc.street.utils.GlobalFuntions
import kotlinx.html.*

class ShoppingCartPageTemplate(val user : User) : Template<HTML> {

    var productsInCart : List<ShoppingCart> = emptyList()
    var payments : List<Payment> = emptyList()
    var addresses : List<Address> = emptyList()

    override fun HTML.apply() {
        head {
            title {
                "Mi cesta"
            }
            insert(HeadTemplate(), TemplatePlaceholder())
            link {
                href = "static/css/shopping_cart.css"
                rel = "stylesheet"
            }
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                form {
                    action = "/orders/add"
                    method = FormMethod.post
                    encType = FormEncType.multipartFormData
                    id = "payform"
                    autoComplete = false
                }

                div("container py-5 h-100") {
                    div("row d-flex justify-content-center align-items-center h-100") {
                        div("col") {
                            div("card") {
                                div("card-body p-4") {
                                    div("row") {
                                        div("col-lg-7") {
                                            h5("mb-3") { +"""Mi cesta""" }
                                            hr {
                                            }
                                            div("d-flex justify-content-between align-items-center mb-4") {
                                                div {
                                                    p("mb-0") {
                                                        +"""Tienes """
                                                        span(classes = "fw-bold" ) { +"${productsInCart.count()} " }
                                                        +"""productos en tu cesta"""
                                                    }
                                                }
                                            }
                                            div {
                                                id = "shopping-cart-products-container"
                                                productsInCart.forEach {
                                                    div("card mb-3") {
                                                        div("card-body") {
                                                            div("d-flex justify-content-between") {
                                                                div("d-flex flex-row align-items-center") {
                                                                    div {
                                                                        img(classes = "img-fluid rounded-3") {
                                                                            if(!it.productBody?.image.isNullOrEmpty()){
                                                                                src = it.productBody?.image!!
                                                                            }else{
                                                                                src = "/static/img/noimage.png"
                                                                            }
                                                                            alt = "Shopping item"
                                                                            style = "width: 65px"
                                                                        }
                                                                    }
                                                                    div("ms-3") {
                                                                        h5 { +(it.productBody?.name ?: "") }
                                                                    }
                                                                }
                                                                div("d-flex flex-row align-items-center") {
                                                                    div("mx-3 fw-normal mb-0") {

                                                                        span("fw-bold fs-5") {
                                                                            +"${it.quantity}"
                                                                        }
                                                                        span("fw-normal") {
                                                                            small { +" x " }
                                                                            +"${it.price}€"
                                                                        }

                                                                    }

                                                                    div("mx-3"){
                                                                        h5("mb-0") { +"${GlobalFuntions.round2Decimals(it.totalPrice)}€" }
                                                                    }
                                                                    a(classes = "text-danger") {
                                                                        href = "shopping-cart/delete/${it.id}"
                                                                        i("fas fa-trash-alt") {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        div("col-lg-5") {
                                            div("card bg-dark text-white rounded-3 position-sticky") {
                                                style = "top: 20px"
                                                div("card-body") {
                                                    div() {
                                                        div("text-center mb-2") {
                                                            h5("mb-0") { +"""Pagamiento""" }
                                                        }
                                                        p("small mb-2") { +"""Acceptamos:""" }
                                                        i("fab fa-cc-mastercard fa-2x me-2") {
                                                        }
                                                        i("fab fa-cc-visa fa-2x me-2") {
                                                        }
                                                        i("fab fa-cc-amex fa-2x me-2") {
                                                        }
                                                        ul("nav nav-tabs nav-justified mb-3") {
                                                            id = "ex1"
                                                            role = "tablist"
                                                            li("nav-item") {
                                                                role = "presentation"
                                                                a(classes = "nav-link bg-dark my-nav ${if (payments.isEmpty()) "active" else ""}") {
                                                                    id = "pago-tab-nueva"
                                                                    attributes["data-mdb-toggle"] = "tab"
                                                                    href = "#pago-tab-nueva-content"
                                                                    role = "tab"
                                                                    attributes["aria-controls"] =
                                                                        "pago-tab-nueva-content"
                                                                    attributes["aria-selected"] = "true"
                                                                    +"""Nueva tarjeta"""
                                                                }
                                                            }
                                                            li("nav-item") {
                                                                role = "presentation"
                                                                a(classes = "nav-link bg-dark my-nav ${if (payments.isEmpty()) "" else "active"}") {
                                                                    id = "pago-tab-guardados"
                                                                    attributes["data-mdb-toggle"] = "tab"
                                                                    href = "#pago-tab-guardados-content"
                                                                    role = "tab"
                                                                    attributes["aria-controls"] =
                                                                        "pago-tab-guardados-content"
                                                                    attributes["aria-selected"] = "false"
                                                                    +"""Mis tarjetas"""
                                                                }
                                                            }
                                                        }
                                                        div("tab-content") {
                                                            input(classes = "d-none") {
                                                                type = InputType.text
                                                                name = "payment_way"
                                                                value = ""
                                                                id = "payment_way"
                                                                form = "payform"
                                                            }
                                                            div("payment_tab tab-pane fade show ${if (payments.isEmpty()) "active show" else "" }") {
                                                                id = "pago-tab-nueva-content"
                                                                role = "tabpanel"
                                                                attributes["data-payment-way"] = "new"
                                                                div(classes = "mt-4") {
                                                                    div("form-outline form-white mb-4") {
                                                                        input(classes = "form-control form-control-lg") {
                                                                            type = InputType.text
                                                                            id = "payment_owner"
                                                                            placeholder = "Jiafu Chen"
                                                                            name = "payment_owner"
                                                                            form = "payform"
                                                                        }
                                                                        label("form-label") {
                                                                            htmlFor = "payment_owner"
                                                                            +"""Titular"""
                                                                        }
                                                                    }
                                                                    div("form-outline form-white mb-4") {
                                                                        input(classes = "form-control form-control-lg") {
                                                                            type = InputType.text
                                                                            id = "payment_card_num"
                                                                            size = "16"
                                                                            placeholder = "1234 5678 9012 3457"
                                                                            minLength = "16"
                                                                            maxLength = "16"
                                                                            name = "payment_card_num"
                                                                            form = "payform"
                                                                        }
                                                                        label("form-label") {
                                                                            htmlFor = "payment_card_num"
                                                                            +"""Numero de la tarjeta"""
                                                                        }
                                                                    }
                                                                    div("row mb-4") {
                                                                        div("col-md-6") {
                                                                            div("form-outline form-white") {
                                                                                input(classes = "form-control form-control-lg") {
                                                                                    type = InputType.text
                                                                                    id = "payment_exp"
                                                                                    placeholder = "MM/YY"
                                                                                    size = "5"
                                                                                    id = "payment_exp"
                                                                                    minLength = "5"
                                                                                    maxLength = "5"
                                                                                    name = "payment_exp"
                                                                                    form = "payform"
                                                                                }
                                                                                label("form-label") {
                                                                                    htmlFor = "payment_exp"
                                                                                    +"""Expiración"""
                                                                                }
                                                                            }
                                                                        }
                                                                        div("col-md-6") {
                                                                            div("form-outline form-white") {
                                                                                input(classes = "form-control form-control-lg") {
                                                                                    type = InputType.password
                                                                                    id = "payment_cvv"
                                                                                    placeholder ="●●●"
                                                                                    minLength = "3"
                                                                                    maxLength = "3"
                                                                                    name = "payment_cvv"
                                                                                    form = "payform"
                                                                                }
                                                                                label("form-label") {
                                                                                    htmlFor = "typeText"
                                                                                    +"""CVV"""
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            div("payment_tab tab-pane fade ${if (payments.isEmpty()) "" else "active show"}") {
                                                                id = "pago-tab-guardados-content"
                                                                role = "tabpanel"
                                                                attributes["data-payment-way"] = "saved"
                                                                div(classes = "pb-3") {
                                                                    payments.forEach {
                                                                        div("d-flex flex-row pb-3") {
                                                                            div("d-flex align-items-center pe-2") {
                                                                                input(classes = "form-check-input pago-guardado") {
                                                                                    type = InputType.radio
                                                                                    value = "${it.id}"
                                                                                    name = "payment_saved"
                                                                                    form = "payform"
                                                                                }
                                                                            }
                                                                            div("rounded border d-flex w-100 p-3 align-items-center") {
                                                                                p("mb-0") {
                                                                                    i("fab fa-cc-visa fa-lg text-light pe-2") {
                                                                                    }
                                                                                    +"${it.owner}"
                                                                                }
                                                                                div("ms-auto") { +"**** **** **** ${it.cardNumber.takeLast(4)}" }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        div {
                                                            div("mb-2 mt-5 text-center w-100") {
                                                                h5("mb-0") { +"""Envio""" }
                                                            }
                                                            ul("nav nav-tabs nav-justified mb-3") {
                                                                id = "tab-envio"
                                                                role = "tablist"

                                                                li("nav-item") {
                                                                    role = "presentation"
                                                                    a(classes = "nav-link bg-dark my-nav ${if (addresses.isEmpty()) "active" else ""}") {
                                                                        id = "envio-tab-nuevo"
                                                                        attributes["data-mdb-toggle"] = "tab"
                                                                        href = "#envio-tab-nuevo-content"
                                                                        role = "tab"
                                                                        attributes["aria-controls"] = "ex3-tabs-1"
                                                                        attributes["aria-selected"] = "true"
                                                                        +"""Nueva dirección"""
                                                                    }
                                                                }
                                                                li("nav-item") {
                                                                    role = "presentation"
                                                                    a(classes = "nav-link bg-dark my-nav ${if (addresses.isEmpty()) "" else "active"}") {
                                                                        id = "envio-tab-guardados"
                                                                        attributes["data-mdb-toggle"] = "tab"
                                                                        href = "#envio-tab-guardados-content"
                                                                        role = "tab"
                                                                        attributes["aria-controls"] = "ex3-tabs-2"
                                                                        attributes["aria-selected"] = "false"
                                                                        +"""Mis direcciones"""
                                                                    }
                                                                }
                                                            }
                                                            div("tab-content") {
                                                                input(classes = "d-none") {
                                                                    type = InputType.text
                                                                    name = "address_way"
                                                                    value = ""
                                                                    id = "address_way"
                                                                    form = "payform"
                                                                }
                                                                div("address_tab tab-pane fade ${if (addresses.isEmpty()) "active show" else "" }") {
                                                                    id = "envio-tab-nuevo-content"
                                                                    role = "tabpanel"
                                                                    attributes["data-address-way"] = "new"

                                                                    div(classes = "mt-4") {
                                                                        div("form-outline form-white mb-4") {
                                                                            input(classes = "form-control form-control-lg") {
                                                                                type = InputType.text
                                                                                id = "address_street"
                                                                                placeholder = "Calle de pepito, 14"
                                                                                name = "address_street"
                                                                                form = "payform"
                                                                            }
                                                                            label("form-label") {
                                                                                htmlFor = "address_street"
                                                                                +"""Dirección"""
                                                                            }
                                                                        }
                                                                        div("form-outline form-white mb-4") {
                                                                            input(classes = "form-control form-control-lg") {
                                                                                type = InputType.text
                                                                                id = "address_city"
                                                                                placeholder = "Barcelona"
                                                                                name = "address_city"
                                                                                form = "payform"
                                                                            }
                                                                            label("form-label") {
                                                                                htmlFor = "address_city"
                                                                                +"""Ciudad"""
                                                                            }
                                                                        }
                                                                        div("row mb-4") {
                                                                            div("col-md-6") {
                                                                                div("form-outline form-white") {
                                                                                    input(classes = "form-control form-control-lg") {
                                                                                        type = InputType.text
                                                                                        id = "address_country"
                                                                                        placeholder = "España"
                                                                                        name = "address_country"
                                                                                        form = "payform"
                                                                                    }
                                                                                    label("form-label") {
                                                                                        htmlFor = "address_country"
                                                                                        +"""Pais"""
                                                                                    }
                                                                                }
                                                                            }
                                                                            div("col-md-6") {
                                                                                div("form-outline form-white") {
                                                                                    input(classes = "form-control form-control-lg") {
                                                                                        type = InputType.text
                                                                                        id = "address_zip_code"
                                                                                        placeholder = "08500"
                                                                                        name = "address_zip_code"
                                                                                        form = "payform"
                                                                                    }
                                                                                    label("form-label") {
                                                                                        htmlFor = "address_zip_code"
                                                                                        +"""Codigo postal"""
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                div("address_tab tab-pane fade ${if (addresses.isEmpty()) "" else "active show" }") {
                                                                    id = "envio-tab-guardados-content"
                                                                    role = "tabpanel"
                                                                    attributes["data-address-way"] = "saved"

                                                                    div(classes = "pb-3") {
                                                                        addresses.forEach {
                                                                            div("d-flex flex-row pb-3") {
                                                                                div("d-flex align-items-center pe-2") {
                                                                                    input(classes = "form-check-input direccion-guardado") {
                                                                                        type = InputType.radio
                                                                                        value = "${it.id}"
                                                                                        name = "address_saved"
                                                                                        form = "payform"
                                                                                    }
                                                                                }
                                                                                div("rounded border d-flex flex-wrap w-100 p-3 align-items-center") {
                                                                                    div("col-12 text-start") {
                                                                                        p("mb-0 w-100 fw-bold") { +"${it.street}" }
                                                                                        p("mb-0 w-100 small") { +"${it.zipCode}" }
                                                                                        p("mb-0 w-100 small") { +"${it.city}" }
                                                                                        p("mb-0 w-100 small") { +"${it.country}" }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    hr("my-4") {
                                                    }
                                                    div("d-flex justify-content-between") {
                                                        p("mb-2") { +"""Subtotal""" }
                                                        p("mb-2") { +"${GlobalFuntions.round2Decimals(productsInCart.sumOf {it.totalPrice })}€" }
                                                    }
                                                    div("d-flex justify-content-between") {
                                                        p("mb-2") { +"""Shipping""" }
                                                        p("mb-2") { +"""Gratis""" }
                                                    }
                                                    div("d-flex justify-content-between mb-4") {
                                                        p("mb-2") { +"""Total (IVA incluido)""" }
                                                        p("mb-2") { +"${GlobalFuntions.round2Decimals(productsInCart.sumOf {it.totalPrice })}€" }
                                                    }
                                                    button(classes = "btn btn-block btn-lg") {
                                                        type = ButtonType.submit
                                                        style = "background-color: rgb(255 222 89)"
                                                        form = "payform"
                                                        if(productsInCart.isEmpty()){
                                                            disabled = true
                                                        }
                                                        div("d-flex justify-content-between") {
                                                            span { +"${GlobalFuntions.round2Decimals(productsInCart.sumOf {it.totalPrice })}€" }
                                                            span { +"""Comprar ahora""" }
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
            script {
                type = "text/javascript"
                src = "/static/js/shopping_cart.js"
            }
        }
    }
}