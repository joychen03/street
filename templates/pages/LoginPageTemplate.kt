package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.templates.share.HeadTemplate
import kotlinx.html.*

class LoginPageTemplate : Template<HTML> {
    override fun HTML.apply() {
        head {
            title {
                "Street - Autenticación"
            }
            insert(HeadTemplate(), TemplatePlaceholder())
            link {
                href = "static/css/login.css"
                rel = "stylesheet"
            }
        }
        body {
            main {
                div("wrapper d-flex justify-content-center") {
                    style = "width: 100%; height: 100vh"
                    div("container authContainer card py-5 px-5") {
                        ul("nav nav-pills nav-justified m-0 mb-3") {
                            id = "ex1"
                            role = "tablist"
                            li("nav-item") {
                                role = "presentation"
                                a(classes = "nav-link active") {
                                    id = "tab-login"
                                    attributes["data-mdb-toggle"] = "pill"
                                    href = "#pills-login"
                                    role = "tab"
                                    attributes["aria-controls"] = "pills-login"
                                    attributes["aria-selected"] = "true"
                                    +"""Accedir"""
                                }
                            }
                            li("nav-item") {
                                role = "presentation"
                                a(classes = "nav-link") {
                                    id = "tab-register"
                                    attributes["data-mdb-toggle"] = "pill"
                                    href = "#pills-register"
                                    role = "tab"
                                    attributes["aria-controls"] = "pills-register"
                                    attributes["aria-selected"] = "false"
                                    +"""Registrar"""
                                }
                            }
                        }
                        div("tab-content") {
                            div("tab-pane fade show active") {
                                id = "pills-login"
                                role = "tabpanel"
                                attributes["aria-labelledby"] = "tab-login"
                                form {
                                    autoComplete = false
                                    encType = FormEncType.multipartFormData
                                    method = FormMethod.post
                                    action = "auth/login"
                                    div("form-outline mb-4") {
                                        input(classes = "form-control") {
                                            type = InputType.text
                                            id = "loginUsername"
                                            name = "username"
                                        }
                                        label("form-label") {
                                            htmlFor = "loginUsername"
                                            +"""Usuario"""
                                        }
                                    }
                                    div("form-outline mb-4") {
                                        input(classes = "form-control") {
                                            type = InputType.password
                                            id = "loginPassword"
                                            name = "password"
                                        }
                                        label("form-label") {
                                            htmlFor = "loginPassword"
                                            +"""Contraseña"""
                                        }
                                    }
                                    button(classes = "btn btn-primary btn-block mb-4") {
                                        type = ButtonType.submit
                                        +"""Acceder"""
                                    }
                                }
                            }
                            div("tab-pane fade") {
                                id = "pills-register"
                                role = "tabpanel"
                                attributes["aria-labelledby"] = "tab-register"
                                form {
                                    autoComplete = false
                                    encType = FormEncType.multipartFormData
                                    method = FormMethod.post
                                    action = "/auth/register"
                                    div("row mb-4") {
                                        div("col") {
                                            div("form-outline") {
                                                input(classes = "form-control") {
                                                    type = InputType.text
                                                    id = "reg_firstName"
                                                    name = "first_name"
                                                    required = true
                                                }
                                                label("form-label") {
                                                    htmlFor = "reg_firstName"
                                                    +"""Nombre"""
                                                }
                                            }
                                        }
                                        div("col") {
                                            div("form-outline") {
                                                input(classes = "form-control") {
                                                    type = InputType.text
                                                    id = "reg_lastName"
                                                    name = "last_name"
                                                    required = true
                                                }
                                                label("form-label") {
                                                    htmlFor = "reg_lastName"
                                                    +"""Apellido"""
                                                }
                                            }
                                        }
                                    }
                                    div("form-outline mb-4") {
                                        input(classes = "form-control") {
                                            type = InputType.text
                                            id = "registerUsername"
                                            name = "username"
                                            required = true
                                        }
                                        label("form-label") {
                                            htmlFor = "registerUsername"
                                            +"""Usuario"""
                                        }
                                    }
                                    div("form-outline mb-4") {
                                        input(classes = "form-control") {
                                            type = InputType.password
                                            id = "passwd"
                                            required = true
                                        }
                                        label("form-label") {
                                            htmlFor = "registerPassword"
                                            +"""Contraseña"""
                                        }
                                    }
                                    div("form-outline mb-4") {
                                        input(classes = "form-control") {
                                            type = InputType.password
                                            id = "confirm_passwd"
                                            name = "password"
                                            required = true
                                            attributes["onpaste"] = "return false;"
                                        }
                                        label("form-label") {
                                            htmlFor = "registerRepeatPassword"
                                            +"""Confirmar contraseña"""
                                        }
                                    }
                                    button(classes = "btn btn-primary btn-block mb-3") {
                                        type = ButtonType.submit
                                        onClick = "validatePassword()"
                                        +"""Registrar"""
                                    }
                                }
                            }
                        }
                    }
                }
            }
            script {
                type = "text/javascript"
                src = "https://mdbootstrap.com/api/snippets/static/download/MDB5-Pro-Advanced_6.1.0/js/mdb.min.js"
            }
            script {
                type = "text/javascript"
                src = "https://mdbootstrap.com/api/snippets/static/download/MDB5-Pro-Advanced_6.1.0/plugins/js/all.min.js"
            }
            script {
                type = "text/javascript"
                src = "static/js/login.js"
            }
        }
    }
}