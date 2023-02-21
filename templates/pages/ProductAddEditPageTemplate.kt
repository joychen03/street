package itb.jiafumarc.street.templates.pages

import io.ktor.server.html.*
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.User
import itb.jiafumarc.street.templates.share.FooterTemplate
import itb.jiafumarc.street.templates.share.HeadTemplate
import itb.jiafumarc.street.templates.share.NavBarTemplate
import kotlinx.html.*

class ProductAddEditPageTemplate(val user : User, val newProduct : Boolean) : Template<HTML> {

    var categoryList = listOf<Category>()
    var product : Product? = null;
    override fun HTML.apply() {
        head {

            title{
                if(newProduct){
                    +"Street - Añadir nuevo producto"
                }else{
                    +"Street - Editar producto"
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
                                if(newProduct){
                                    +"Añadir nuevo producto"
                                }else{
                                    +"Editar producto - ID: ${product?.id}"
                                }
                            }
                            div("card") {
                                div("card-body") {
                                    form(classes = "mt-4") {
                                        id = "product-form"
                                        autoComplete = false
                                        encType = FormEncType.multipartFormData
                                        action = if(newProduct){
                                            "/products/add"
                                        }else{
                                            "/products/edit/${product?.id}"
                                        }
                                        method = FormMethod.post
                                        div("row") {
                                            div("col-md-8") {
                                                div("form-outline mb-4") {
                                                    input(classes = "form-control form-control-lg") {
                                                        type = InputType.text
                                                        id = "product_name"
                                                        name="product_name"
                                                        placeholder = "Nike Ariforce"
                                                        if(!newProduct){
                                                            value = "${product?.name}"
                                                        }
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "product_name"
                                                        +"""Nombre"""
                                                    }
                                                }
                                            }
                                            div("col-md-4") {
                                                select("form-select") {
                                                    attributes["aria-label"] = "select"
                                                    id = "product_category"
                                                    name="product_category"
                                                    option {
                                                        if(newProduct){
                                                            selected = true
                                                        }
                                                        +"""No tiene Categoria"""
                                                    }
                                                    categoryList.forEach {
                                                        option {
                                                            value = "${it.id}"
                                                            if(!newProduct && product?.categoryID == it.id){
                                                                selected = true
                                                            }
                                                            +"${it.name}"

                                                        }
                                                    }


                                                }
                                            }
                                        }
                                        div("form-outline mb-4") {
                                            textArea(classes = "form-control") {
                                                id = "product_desc"
                                                name="product_desc"
                                                rows = "4"
                                                placeholder = "Este producto es..."
                                                if(!newProduct){
                                                    +"${product?.description}"
                                                }
                                            }


                                            label("form-label") {
                                                htmlFor = "product_desc"
                                                +"""Descripción"""
                                            }
                                        }
                                        div("row pb-4") {
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        type = InputType.number
                                                        id = "product_price"
                                                        name = "product_price"
                                                        placeholder = "99,8"
                                                        if(!newProduct){
                                                            value = product?.price.toString();
                                                        }
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "product_price"
                                                        +"""Precio"""
                                                    }
                                                    div("form-helper") { +"""Unidad = euro €""" }
                                                }
                                            }
                                            div("col-md-6") {
                                                div("form-outline") {
                                                    input(classes = "form-control form-control-lg") {
                                                        type = InputType.number
                                                        id = "product_stock"
                                                        name = "product_stock"
                                                        placeholder = "200"
                                                        if(!newProduct){
                                                            value = product?.stock.toString()
                                                        }
                                                        required = true
                                                    }
                                                    label("form-label") {
                                                        htmlFor = "product_stock"
                                                        +"""Stock"""
                                                    }
                                                }
                                            }
                                        }

                                        div("row mt-3") {
                                            div("col") {
                                                div {
                                                    label("form-label") {
                                                        htmlFor = "product_image"
                                                        +"""Imagen del producto"""
                                                    }
                                                    input(classes = "form-control") {
                                                        type = InputType.file
                                                        id = "product_image"
                                                        name="product_image"
                                                        accept = "image/png, image/jpeg"
                                                    }
                                                }
                                            }
                                            if(!product?.image.isNullOrEmpty()){
                                                div("d-flex col-auto align-items-end") {
                                                    div("form-check form-switch") {
                                                        input(classes = "form-check-input") {
                                                            type = InputType.checkBox
                                                            role = "switch"
                                                            id = "product_no_image"
                                                            name = "product_no_image"
                                                        }
                                                        label("form-check-label") {
                                                            htmlFor = "product_no_image"
                                                            +"""No image"""
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if(!newProduct){
                                            div("text-center") {
                                                img(classes = "mt-3 mb-3") {
                                                    src = if(product?.image != null){
                                                        product?.image!!
                                                    }else{
                                                        "/static/img/noimage.png"
                                                    }
                                                    alt = "product-image"
                                                    style = "height: 200px"
                                                }
                                            }
                                        }

                                        button(classes = "btn btn-primary mb-4 mt-4 w-100") {
                                            type = ButtonType.submit

                                            +if(newProduct) "AÑADIR" else "ACTUALIZAR"
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
                src = "/static/js/product_add_edit.js"
            }
        }
    }
}