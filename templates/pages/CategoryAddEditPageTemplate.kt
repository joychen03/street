package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class CategoryAddEditPageTemplate(val user : User, val newCategory : Boolean) : Template<HTML>{

    var category : Category? = null

    override fun HTML.apply() {
        head {

            title{
                if(newCategory){
                    +"Street - Añadir nueva categoria"
                }else{
                    +"Street - Editar categoria"
                }
            }

            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            insert(NavBarTemplate(user), TemplatePlaceholder())

            main {
                div("container mt-5 mb-5") {
                    div("row justify-content-center") {
                        div("col-lg-8") {
                            h3("mb-3") {
                                + if(newCategory){
                                    "Añadir nueva categoria"
                                }else{
                                    "Editar categoria - ID: ${category?.id}"
                                }
                            }
                            div("card") {
                                div("card-body") {
                                    form(classes = "mt-4") {
                                        id = "category-form"
                                        autoComplete = false
                                        encType = FormEncType.multipartFormData
                                        action = if(newCategory){
                                            "/products/categories/add"
                                        }else{
                                            "/products/categories/edit/${category?.id}"
                                        }
                                        method = FormMethod.post
                                        div("form-outline mb-4") {
                                            input(classes = "form-control form-control-lg") {
                                                type = InputType.text
                                                name = "category_name"
                                                id = "category_name"
                                                placeholder = "Zapatos"
                                                required = true
                                                if(!newCategory){
                                                    value = category!!.name
                                                }
                                            }
                                            label("form-label") {
                                                htmlFor = "category_name"
                                                +"""Nombre"""
                                            }
                                        }
                                        div("row") {
                                            div("col") {
                                                div {
                                                    label("form-label") {
                                                        htmlFor = "category_image"
                                                        +"""Imagen del producto"""
                                                    }
                                                    input(classes = "form-control") {
                                                        type = InputType.file
                                                        id = "category_image"
                                                        name = "category_image"
                                                        accept = "image/png, image/jpeg"
                                                    }
                                                }
                                            }
                                            if(!category?.image.isNullOrEmpty()){
                                                div("d-flex col-auto align-items-end") {
                                                    div("form-check form-switch") {
                                                        input(classes = "form-check-input") {
                                                            type = InputType.checkBox
                                                            role = "switch"
                                                            id = "category_no_image"
                                                            name = "category_no_image"
                                                        }
                                                        label("form-check-label") {
                                                            htmlFor = "category_no_image"
                                                            +"""No image"""
                                                        }
                                                    }
                                                }
                                            }

                                        }

                                        div("text-center") {
                                            img(classes = "mt-4 mb-3") {
                                                src = if(!category?.image.isNullOrEmpty()){
                                                    category!!.image!!
                                                }else{
                                                    "/static/img/noimage"
                                                }
                                                alt = "product-image"
                                                style = "height: 200px"
                                            }
                                        }

                                        button(classes = "btn btn-primary mb-4 mt-4 w-100") {
                                            type = ButtonType.submit
                                            +if(newCategory){
                                                """AÑADIR"""
                                            }else{
                                                """ACTUALIZAR"""
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
                src = "/static/js/category_add_edit.js"
            }
        }
    }
}