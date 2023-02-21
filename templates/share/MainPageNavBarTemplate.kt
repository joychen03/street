package itb.jiafumarc.street.templates.share

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import kotlinx.html.*

class MainPageNavBarTemplate(val user : User?) : Template<FlowContent> {
    override fun FlowContent.apply() {
        header {
            nav("navbar fixed-top navbar-scroll") {
                div("container-fluid") {
                    div("d-flex my-3 ms-3") {
                        div("me-3 fs-5") {
                            a(classes = "nav-link") {
                                attributes["aria-current"] = "page"
                                href = "#intro"
                                +"""Inicio"""
                            }
                        }
                        if(user?.isAdmin == true){
                            div("me-3 fs-5") {
                                a(classes = "nav-link") {
                                    attributes["aria-current"] = "page"
                                    href = "admin"
                                    +"""Admin"""
                                }
                            }
                        }
                        div("me-3 fs-5") {
                            a(classes = "nav-link") {
                                attributes["aria-current"] = "page"
                                href = "/products/categories"
                                +"""Categorias"""
                            }
                        }
                    }
                    if(user?.isAdmin == true){
                        a(classes = "btn btn-danger position-absolute translate-middle-x") {
                            style = "left: 50%"
                            href = "/reset"
                            +"""reset"""
                        }
                    }
                    div("d-flex align-items-center me-3") {
                        ul("navbar-nav flex-row") {
                            li("nav-item me-2") {
                                a(classes = "nav-link pe-2") {
                                    href = "/shopping-cart"
                                    rel = "nofollow"
                                    i("fas fa-shopping-cart") {
                                    }
                                }
                            }
                        }
                        if(user != null){
                            div("dropdown") {
                                a(classes = "dropdown-toggle d-flex align-items-center hidden-arrow") {
                                    href = "#"
                                    id = "navbarDropdownMenuAvatar"
                                    role = "button"
                                    attributes["data-mdb-toggle"] = "dropdown"
                                    attributes["aria-expanded"] = "false"

                                    img(classes = "rounded-circle nav-avatar") {
                                        src = if(user.avatar.isNullOrEmpty()){
                                            "/static/img/anonymous.png"
                                        }else{
                                            user.avatar!!
                                        }
                                        attributes["loading"] = "lazy"
                                    }

                                }
                                ul("dropdown-menu dropdown-menu-end") {
                                    attributes["aria-labelledby"] = "navbarDropdownMenuAvatar"
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "/myspace"
                                            +"""Mi perfil"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "/orders/my-orders"
                                            +"""Mis pedidos"""
                                        }
                                    }
                                    li {
                                        a(classes = "dropdown-item") {
                                            href = "/auth/logout"
                                            +"""Cerrar session"""
                                        }
                                    }

                                }
                            }
                        }else{
                            a(classes = "btn btn-dark text-street") {
                                href = "/auth"
                                +"""Iniciar Session"""
                            }
                        }

                    }
                }
            }
            div("bg-image") {
                id = "intro"
                style =
                    "background-image: url(https://assets.vogue.com/photos/601482223216336c3f4ba8d0/master/w_1920,c_limit/Paris%20Mens%20Fall%2021%20Day%203%20by%20STYLEDUMONDE%20Street%20Style%20Fashion%20Photography_95A8613FullRes.jpg); height: 100vh"
                div("mask text-white") {
                    style = "background-color: rgba(0, 0, 0, 0.8)"
                    div("container d-flex align-items-center text-center h-100") {
                        div("w-100") {
                            img(classes = "mb-5") {
                                src = "/static/img/logo.png"
                                alt = "logo"
                                width = "300px"
                            }
                            h1("mb-5") { +"""S.T.R.R.E.T la moda urbana que te hace DESTACAR""" }
                        }
                    }
                }
            }
        }
    }
}