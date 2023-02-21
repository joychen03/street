package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class CategoryPageTemplate(val user : User?) : Template<HTML> {

    var categories : List<Category> = emptyList()
    override fun HTML.apply() {
        head {
            title{
                +"Street - Categorias"
            }

            insert(HeadTemplate(), TemplatePlaceholder())

            link {
                href = "/static/css/category.css"
                rel = "stylesheet"
            }
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5") {
                    div("row justify-content-center") {
                        categories.forEach{
                            div("col-md-3 mb-4") {
                                div("bg-image ripple rounded-5 shadow-6-strong category-card") {
                                    img(classes = "h-100 w-100 object-cover") {
                                        if(!it.image.isNullOrEmpty()){
                                            src = it.image!!
                                        }else{
                                            src = "/static/img/noimage.png"
                                        }
                                    }
                                    a(classes = "text-light") {
                                        href = "/products/categories/${it.id}"
                                        div("mask") {
                                            style = "background-color: hsla(0, 0%, 0%, 0.4)"
                                            div("d-flex flex-column justify-content-center align-items-center h-100") {
                                                div("text-center w-100") {
                                                    span("fs-2 fw-bold") { +"${it.name}" }
                                                }
                                                span { +"${it.productsCount} ${ if(it.productsCount > 1) "productos" else "producto"}" }
                                            }
                                        }
                                        div("hover-overlay") {
                                            div("mask") {
                                                style = "background-color: hsla(0, 0%, 98%, 0.2)"
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