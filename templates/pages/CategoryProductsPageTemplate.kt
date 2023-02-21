package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class CategoryProductsPageTemplate(val user : User?) : Template<HTML> {

    var category : Category? = null
    var products : List<Product> = emptyList()
    override fun HTML.apply() {
        head {
            title{
                +"Street - Productos de la categoria"
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5") {
                    p { +"""Categoria:""" }
                    h1("mb-5") { +"${category?.name ?: "Unknown"}" }
                    div("row") {
                        products.forEach{
                            div("col-lg-4 col-md-6 mb-4") {
                                form {
                                    action = "/shopping-cart/add"
                                    method = FormMethod.post
                                    encType = FormEncType.multipartFormData
                                    div("card") {
                                        style = "height: 550px"
                                        input(classes = "d-none") {
                                            type = InputType.text
                                            name = "product_id"
                                            id = "product_id"
                                            value = "${it.id}"
                                        }
                                        div("bg-image hover-zoom ripple ripple-surface ripple-surface-light") {
                                            attributes["data-mdb-ripple-color"] = "light"
                                            img(classes = "w-100 product-card-img") {
                                                if(!it.image.isNullOrEmpty()){
                                                    src = it.image!!
                                                }else{
                                                    src = "/static/img/noimage.png"
                                                }
                                            }
                                            a {
                                                href = "/products/id/${it.id}"
                                                div("mask") {
                                                    div("d-flex justify-content-start align-items-end h-100") {
                                                    }
                                                }
                                                div("hover-overlay") {
                                                    div("mask") {
                                                        style = "background-color: rgba(251, 251, 251, 0.15)"
                                                    }
                                                }
                                            }
                                        }
                                        div("card-body d-flex justify-content-center align-items-center") {
                                            div("text-center") {
                                                p("card-title fs-5 mb-3") { +"${it.name}" }
                                                h5("price") { +"${it.price}€" }
                                                div("text-center") {
                                                    button(classes = "btn btn-primary btn-md rounded-pill btn-dark") {
                                                        type = ButtonType.submit
                                                        style = "color: rgb(255 222 89)"
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
                        }

                    }
                }
            }

            insert(FooterTemplate(), TemplatePlaceholder())
        }
    }
}