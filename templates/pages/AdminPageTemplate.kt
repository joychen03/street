package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class AdminPageTemplate(val user : User) : Template<HTML> {

    var products : List<Product> = emptyList()
    var categories : List<Category> = emptyList()
    var users : List<User> = emptyList()
    override fun HTML.apply() {
        head {
            title{
                +"Street - Panel de Admin"
            }

            insert(HeadTemplate(), TemplatePlaceholder())

            link {
                href = "/static/css/admin.css"
                rel = "stylesheet"
            }
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("mx-5 my-5") {
                    div("row") {
                        div("col-lg-2") {
                            div("nav flex-column nav-pills text-center") {
                                id = "v-pills-tab"
                                role = "tablist"
                                attributes["aria-orientation"] = "vertical"
                                a(classes = "nav-link shadow-6-strong active") {
                                    id = "productos-tab"
                                    attributes["data-mdb-toggle"] = "pill"
                                    href = "#productos-tab-content"
                                    role = "tab"
                                    attributes["aria-controls"] = "productos"
                                    attributes["aria-selected"] = "true"
                                    +"""Productos"""
                                }
                                a(classes = "nav-link shadow-6-strong") {
                                    id = "categorias-tab"
                                    attributes["data-mdb-toggle"] = "pill"
                                    href = "#categorias-tab-content"
                                    role = "tab"
                                    attributes["aria-controls"] = "categorias-tab-content"
                                    attributes["aria-selected"] = "false"
                                    +"""Categorias"""
                                }
                                a(classes = "nav-link shadow-6-strong") {
                                    id = "usuario-tab"
                                    attributes["data-mdb-toggle"] = "pill"
                                    href = "#usuario-tab-content"
                                    role = "tab"
                                    attributes["aria-controls"] = "usuario-tab-content"
                                    attributes["aria-selected"] = "false"
                                    +"""Usuario"""
                                }
                            }
                        }
                        div("col-lg-10 mt-2") {
                            div("card") {
                                div("card-body pb-5") {
                                    div("tab-content") {
                                        id = "v-pills-tabContent"
                                        div("tab-pane fade show active") {
                                            id = "productos-tab-content"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "productos-tab"
                                            div("text-end") {
                                                a(classes = "fs-2 text-primary") {
                                                    href = "/products/add"
                                                    i("far fa-plus-square") {
                                                    }
                                                }
                                            }
                                            div("table-responsive") {
                                                table("table align-middle mb-0 bg-white") {
                                                    thead("bg-white") {
                                                        tr {
                                                            th { +"""ID""" }
                                                            th { +"""Imagen""" }
                                                            th { +"""Nombre""" }
                                                            th { +"""Descripción""" }
                                                            th { +"""Categoria""" }
                                                            th { +"""Precio""" }
                                                            th { +"""Stock Inical""" }
                                                            th { +"""Stock Actual""" }
                                                            th { +"""Fecha de creación""" }
                                                            th(classes = "text-center") { +"""Accion""" }
                                                        }
                                                    }
                                                    tbody {
                                                        products.forEach {
                                                            tr {
                                                                td {
                                                                    h4("mb-0") { +"${it.id}" }
                                                                }
                                                                td {
                                                                    div("d-flex align-items-center") {
                                                                        img {
                                                                            if(!it.image.isNullOrEmpty()){
                                                                                src = it.image!!
                                                                            }else{
                                                                                src = "/static/img/noimage.png"
                                                                            }
                                                                            alt = ""
                                                                            style = "height: 45px"
                                                                        }
                                                                    }
                                                                }
                                                                td { +"${it.name}" }
                                                                td { +"${it.description}" }
                                                                td { +"${it.categoryID ?: "Sin categoria"}" }
                                                                td { +"${it.price}€" }
                                                                td { +"${it.stock}" }
                                                                td { +"${it.currentStock ?: "Unknown"}" }
                                                                td { +"${it.createDate?.date}" }
                                                                td {
                                                                    div("d-flex flex-column") {
                                                                        div("text-center") {
                                                                            a(classes = "text-warning fs-4 mx-2") {
                                                                                href = "/products/edit/${it.id}"
                                                                                type = "button"
                                                                                i("fas fa-edit") {
                                                                                }
                                                                            }
                                                                            a(classes = "text-danger fs-4 mx-2") {
                                                                                href = "/products/delete/${it.id}"
                                                                                type = "button"
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
                                            }
                                        }
                                        div("tab-pane fade") {
                                            id = "categorias-tab-content"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "categorias-tab"
                                            div("text-end") {
                                                a(classes = "fs-2 text-primary") {
                                                    href = "/products/categories/add"
                                                    i("far fa-plus-square") {
                                                    }
                                                }
                                            }
                                            div("table-responsive") {
                                                table("table align-middle mb-0 bg-white") {
                                                    thead("bg-white") {
                                                        tr {
                                                            th { +"""ID""" }
                                                            th { +"""Imagen""" }
                                                            th { +"""Nombre""" }
                                                            th { +"""Cantidad de articulos""" }
                                                            th { +"""Fecha de creación""" }
                                                            th(classes = "text-center") { +"""Accion""" }
                                                        }
                                                    }
                                                    tbody {
                                                        categories.forEach {
                                                            tr {
                                                                td {
                                                                    h4("mb-0") { +"${it.id}" }
                                                                }
                                                                td {
                                                                    div("d-flex align-items-center") {
                                                                        img {
                                                                            if(!it.image.isNullOrEmpty()){
                                                                                src = it.image!!
                                                                            }else{
                                                                                src = "/static/img/noimage.png"
                                                                            }
                                                                            alt = ""
                                                                            style = "height: 45px"
                                                                        }
                                                                    }
                                                                }
                                                                td { +"${it.name}" }
                                                                td { +"${it.productsCount}" }
                                                                td { +"${it.createDate?.date}" }
                                                                td {
                                                                    div("d-flex flex-column") {
                                                                        div("text-center") {
                                                                            a(classes = "text-warning fs-4 mx-2") {
                                                                                href = "/products/categories/edit/${it.id}"
                                                                                type = "button"
                                                                                i("fas fa-edit") {
                                                                                }
                                                                            }
                                                                            a(classes = "text-danger fs-4 mx-2") {
                                                                                href = "/products/categories/delete/${it.id}"
                                                                                type = "button"
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
                                            }
                                        }
                                        div("tab-pane fade") {
                                            id = "usuario-tab-content"
                                            role = "tabpanel"
                                            attributes["aria-labelledby"] = "usuario-tab"
                                            div("table-responsive") {
                                                table("table align-middle mb-0 bg-white") {
                                                    thead("bg-white") {
                                                        tr {
                                                            th { +"""ID""" }
                                                            th { +"""Avatar""" }
                                                            th { +"""Nombre de usuario""" }
                                                            th { +"""Nombre""" }
                                                            th { +"""Apellido""" }
                                                            th { +"""Tipo de usuario""" }
                                                            th(classes = "text-center") { +"""Accion""" }
                                                        }
                                                    }
                                                    tbody {
                                                        users.forEach {
                                                            tr {
                                                                td {
                                                                    h4("mb-0") { +"${it.id}" }
                                                                }
                                                                td {
                                                                    div("d-flex align-items-center") {
                                                                        img(classes = "rounded-circle ") {
                                                                            src = if(!it.avatar.isNullOrEmpty()){
                                                                                it.avatar!!
                                                                            }else{
                                                                                "/static/img/anonymous.png"
                                                                            }
                                                                            alt = ""
                                                                            style = "height:45px; width:45px; object-fit:cover; object-position: center;"
                                                                        }
                                                                    }
                                                                }
                                                                td { +"${it.username}" }
                                                                td { +"${it.firstName}" }
                                                                td { +"${it.lastName}" }
                                                                if(it.isAdmin){
                                                                    td { +"Administrador" }
                                                                }else{
                                                                    td { +"Cliente" }
                                                                }
                                                                td {
                                                                    div("d-flex flex-column") {
                                                                        div("text-center") {
                                                                            a(classes = "text-danger fs-4 mx-2") {
                                                                                href = "/users/delete/${it.id}"
                                                                                type = "button"
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