package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class ProfileEditPageTemplate(val user : User) : Template<HTML> {
    override fun HTML.apply() {
        head {
            title {
                +"Street - Actualizar perfil"
            }
            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5") {
                    div("row justify-content-center") {
                        div("col-lg-8") {
                            h3("mb-3") { +"""Mi perfil""" }
                            div("card") {
                                div("card-body") {
                                    form {
                                        encType = FormEncType.multipartFormData
                                        method = FormMethod.post
                                        action = "/myspace/edit"
                                        div("form-outline mb-4") {
                                            input(classes = "form-control") {
                                                type = InputType.text
                                                id = "user_username"
                                                name = "user_username"
                                                disabled = true
                                                value = user.username
                                            }
                                            label("form-label") {
                                                htmlFor = "user_username"
                                                +"""Nombre de usuario"""
                                            }
                                        }
                                        div("row mb-4") {
                                            div("col") {
                                                div("form-outline") {
                                                    input(classes = "form-control") {
                                                        type = InputType.text
                                                        id = "user_firstname"
                                                        name = "user_firstname"
                                                        required = true
                                                        value = user.firstName
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "user_firstname"
                                                        +"""Nombre"""
                                                    }
                                                }
                                            }
                                            div("col") {
                                                div("form-outline") {
                                                    input(classes = "form-control") {
                                                        type = InputType.text
                                                        id = "user_lastname"
                                                        name = "user_lastname"
                                                        required = true
                                                        value = user.lastName
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "user_lastname"
                                                        +"""Apellido"""
                                                    }
                                                }
                                            }
                                        }
                                        div("form-check form-switch mb-3") {
                                            input(classes = "form-check-input") {
                                                type = InputType.checkBox
                                                role = "switch"
                                                id = "change_passwd"
                                            }
                                            label("form-check-label") {
                                                htmlFor = "change_passwd"
                                                +"""Cambiar contraseña"""
                                            }
                                        }

                                        div("row mb-2") {
                                            div("col") {
                                                div("form-outline mb-4") {
                                                    input(classes = "form-control") {
                                                        type = InputType.password
                                                        id = "passwd"
                                                        disabled = true
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "passwd"
                                                        +"""Contraseña"""
                                                    }
                                                }
                                            }
                                            div("col") {
                                                div("form-outline mb-4") {
                                                    input(classes = "form-control") {
                                                        type = InputType.password
                                                        id = "confirm_passwd"
                                                        name = "user_passwd"
                                                        attributes["onpaste"] = "return false;"
                                                        disabled = true
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "confirm_passwd"
                                                        +"""Confirma contraseña"""
                                                    }
                                                }
                                            }
                                        }
                                        div("row mb-4") {
                                            div("col") {
                                                div {
                                                    label("form-label") {
                                                        htmlFor = "user_avatar"
                                                        +"""Avatar"""
                                                    }
                                                    input(classes = "form-control") {
                                                        type = InputType.file
                                                        id = "user_avatar"
                                                        name = "user_avatar"
                                                        accept = "image/png, image/jpeg"
                                                    }
                                                }
                                            }
                                            if(!user.avatar.isNullOrEmpty()){
                                                div("d-flex col-auto align-items-end") {
                                                    div("form-check form-switch") {
                                                        input(classes = "form-check-input") {
                                                            type = InputType.checkBox
                                                            role = "switch"
                                                            id = "user_no_avatar"
                                                            name = "user_no_avatar"
                                                        }
                                                        label("form-check-label") {
                                                            htmlFor = "user_no_avatar"
                                                            +"""Quitar avatar"""
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        div("text-center mb-3") {
                                            img(classes = "mt-3 mb-3") {
                                                src = if(!user.avatar.isNullOrEmpty()){
                                                    user.avatar!!
                                                }else{
                                                    "/static/img/anonymous.png"
                                                }
                                                alt = "avatar"
                                                style = "height: 200px"
                                            }
                                        }
                                        button(classes = "btn btn-primary btn-block mb-3") {
                                            type = ButtonType.submit
                                            onClick = "validatePassword()"
                                            +"""ACTUALIZAR"""
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
                src = "/static/js/profile_edit.js"
            }
        }
    }
}