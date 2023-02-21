package itb.jiafumarc.street.templates.share

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import kotlinx.html.*

class NavBarTemplate(val user : User?) : Template<FlowContent> {
    override fun FlowContent.apply() {
        header {
            nav("navbar navbar-dark bg-dark navbar-expand-lg") {
                div("container-fluid") {
                    button(classes = "navbar-toggler") {
                        type = ButtonType.button
                        attributes["data-mdb-toggle"] = "collapse"
                        attributes["data-mdb-target"] = "#navbarSupportedContent"
                        attributes["aria-controls"] = "navbarSupportedContent"
                        attributes["aria-expanded"] = "false"
                        attributes["aria-label"] = "Toggle navigation"
                        i("fas fa-bars") {
                        }
                    }
                    div("collapse navbar-collapse") {
                        id = "navbarSupportedContent"
                        a(classes = "navbar-brand mt-2 mt-lg-0") {
                            href = "/"
                            img {
                                src = "/static/img/logo.png"
                                height = "40"
                                alt = "Street logo"
                                attributes["loading"] = "lazy"
                            }
                        }
                        ul("navbar-nav me-auto mb-2 mb-lg-0") {
                            li("nav-item") {
                                a(classes = "nav-link") {
                                    href = "/"
                                    +"""Inicio"""
                                }
                            }
                            if(user?.isAdmin == true){
                                li("nav-item") {
                                    a(classes = "nav-link") {
                                        href = "/admin"
                                        +"""Admin"""
                                    }
                                }
                            }
                            li("nav-item") {
                                a(classes = "nav-link") {
                                    href = "/products/categories"
                                    +"""Categorias"""
                                }
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
                    div("d-flex align-items-center") {
                        a(classes = "text-reset me-3") {
                            href = "/shopping-cart"
                            style = "color: white !important"
                            rel = "nofollow"
                            i("fas fa-shopping-cart") {
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
                            a(classes = "btn btn-light text-dark") {
                                href = "/auth"
                                +"""Iniciar Session"""
                            }
                        }
                    }
                }
            }
        }
    }

}