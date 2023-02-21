package itb.jiafumarc.street.templates.pages

import io.ktor.http.*
import io.ktor.server.html.*
import itb.jiafumarc.street.templates.share.HeadTemplate
import kotlinx.html.*

class ErrorPageTemplate(val errorType : HttpStatusCode) : Template<HTML> {
    override fun HTML.apply() {
        head {
            title{
                +"ERROR"
            }
            insert(HeadTemplate(), TemplatePlaceholder())
        }
        body {
            main("h-100") {
                div("d-flex justify-content-center align-items-center h-100") {
                    div("row justify-content-center align-items-center") {
                        div("col-auto text-center") {
                            div("w-100") {
                                span("fs-1 fw-bold") { +
                                    when(errorType){
                                        HttpStatusCode.NotFound ->{
                                            """404 NOT FOUND"""
                                        }
                                        HttpStatusCode.Unauthorized ->{
                                            """YOU ARE NOT ADMIN!!"""
                                        }
                                        else -> {"${errorType.value} - ${errorType.description}"}
                                    }
                                }
                            }
                            a(classes = "text-center btn btn-info btn-rounded mt-3") {
                                href = "/"
                                h5("m-0") { +"""Go back""" }
                            }
                        }
                    }
                }
            }

        }
    }

}