package itb.jiafumarc.street.templates.share

import io.ktor.server.html.*
import kotlinx.html.*

class FooterTemplate : Template<FlowContent> {
    override fun FlowContent.apply() {
        footer("text-center text-lg-start bg-white text-muted position-sticky top-100") {
            div("text-center p-4") {
                style = "background-color: rgba(0, 0, 0, 0.025)"
                +"""© 2023 Copyright: """
                span(classes = "text-reset fw-bold") {
                    +"""Jiafu Chen & Marc Areny"""
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
    }
}