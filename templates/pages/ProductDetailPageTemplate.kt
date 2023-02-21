package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class ProductDetailPageTemplate(val user : User?) : Template<HTML> {

    var product : Product? = null;
    override fun HTML.apply() {
        head {
            title{
                +"Detalle de producto"
            }

            insert(HeadTemplate(), TemplatePlaceholder())

        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container px-4 px-lg-5 my-5") {
                    div("row gx-4 gx-lg-5 align-items-center") {
                        div("col-md-6 text-center") {
                            if(product?.image != null){
                                img(classes = "card-img-top mb-5 mb-md-0 shadow-3-strong rounded-5") {
                                    src = product!!.image!!
                                    alt = "product-image"
                                }
                            }else{
                                i("fas fa-image fs-1") {
                                }
                            }

                        }
                        div("col-md-6") {
                            form {
                                encType = FormEncType.multipartFormData
                                method = FormMethod.post
                                action = "/shopping-cart/add"
                                input(classes = "d-none") {
                                    type = InputType.text
                                    name = "product_id"
                                    id = "product_id"
                                    value = "${product?.id ?: -1}"
                                }
                                div("small mb-1") {
                                    +"""Product ID:"""
                                    span("fw-bold") {
                                        id = "product_id_text"
                                        +" ${product?.id ?: "Unknown"}"
                                    }
                                }
                                h1("display-5 fw-bolder") {
                                    id = "product_title"
                                    +"${product?.name ?: "Unknown"}"
                                }
                                div("fs-5 mb-5") {
                                    span {
                                        id = "product_price"
                                        +"${product?.price ?: "Unknown"}€"
                                    }
                                }
                                p("lead mb-5") {
                                    id = "product_desc"
                                    +"${product?.description ?: "Unknown"}"
                                }
                                div("d-flex") {
                                    div("form-outline me-3") {
                                        style = "max-width: 6rem"
                                        input(classes = "form-control") {
                                            type = InputType.number
                                            name = "product_qty"
                                            id = "product_qty"
                                            value = "1"
                                            min = "1"
                                        }
                                        label("form-label") {
                                            htmlFor = "typeNumber"
                                            +"""Quantity"""
                                        }
                                    }
                                    button(classes = "btn btn-dark btn-md") {
                                        type = ButtonType.submit
                                        style = "color: rgb(255 222 89)"
                                        onClick = "checkQty()"
                                        +"""Add to Cart"""
                                        i("fas fa-shopping-cart ms-2") {
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
                src = "/static/js/product.js"
            }
        }
    }
}