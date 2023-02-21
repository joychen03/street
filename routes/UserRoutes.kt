package itb.jiafumarc.street.routes

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOAddress
import itb.jiafumarc.street.dao.DAOPayment
import itb.jiafumarc.street.dao.DAOUser
import itb.jiafumarc.street.models.Address
import itb.jiafumarc.street.models.Payment
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.save
import itb.jiafumarc.street.templates.pages.AddressAddPageTemplate
import itb.jiafumarc.street.templates.pages.PaymentAddPageTemplate
import itb.jiafumarc.street.templates.pages.ProfileEditPageTemplate
import itb.jiafumarc.street.templates.pages.ProfilePageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.userRoutes() {

    route("/myspace") {

        authenticate("auth-session") {
            get("") {
                val session = call.sessions.get("user_session") as UserSession
                val daoPayment = DAOPayment()
                val daoAddress = DAOAddress()

                val myPayments = daoPayment.getUserPayments(session.user.id ?: -1)
                val myAddresses = daoAddress.getUserAddresses(session.user.id ?: -1)

                val template = ProfilePageTemplate(session.user).apply {
                    this.payments = myPayments
                    this.addresses = myAddresses
                }

                call.respondHtmlTemplate(template){

                }
            }

            get("edit") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession

                    val template = ProfileEditPageTemplate(session!!.user)

                    call.respondHtmlTemplate(template){

                    }

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            post("edit") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession

                    val daoUser = DAOUser()
                    val oldUser = daoUser.getUser(session!!.user.id!!) ?: return@post call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    val uploadsFolderURL = "${call.request.origin.scheme}://${call.request.host()}:${call.request.port()}/uploads"
                    var noAvatar : String? = null
                    var fileName: String? = null
                    var newPasswd: String? = null

                    val multipart = call.receiveMultipart()
                    multipart.forEachPart { part ->
                        when(part){
                            is PartData.FormItem ->{
                                if(part.value.isNotEmpty()){
                                    when(part.name) {
                                        "user_username" -> oldUser.username = part.value
                                        "user_firstname" -> oldUser.firstName = part.value
                                        "user_lastname" -> oldUser.lastName = part.value
                                        "user_passwd" -> newPasswd = part.value
                                        "user_no_avatar" -> noAvatar = part.value
                                    }
                                }
                            }
                            is PartData.FileItem ->{
                                val file = part.originalFileName as String
                                fileName = if(file.isNotEmpty()){
                                    part.save()
                                }else{
                                    null
                                }
                            }

                            else -> {}
                        }
                    }

                    oldUser.avatar = if(noAvatar.isNullOrEmpty()){
                        if(fileName.isNullOrEmpty()){
                            oldUser.avatar
                        }else{
                            "${uploadsFolderURL}/${fileName}"
                        }
                    }else{
                        null
                    }

                    oldUser.password = if(!newPasswd.isNullOrEmpty()){
                        newPasswd!!
                    }else{
                        oldUser.password
                    }

                    if(daoUser.updateUser(oldUser)) {
                        val updadedUser = daoUser.getUser(session.user.id!!) ?: throw Exception()
                        call.sessions.set(UserSession(updadedUser))
                    }else{
                        throw Exception()
                    }

                    call.respondRedirect("/myspace")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            get("payments/add") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession

                    val template = PaymentAddPageTemplate(session!!.user)

                    call.respondHtmlTemplate(template){

                    }

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            post("payments/add") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession
                    val daoPayment = DAOPayment()
                    val fd = call.receiveParameters()

                    val newPayment = Payment(
                        userID = session!!.user.id!!,
                        owner = fd["payment_owner"] ?: throw Exception(),
                        cardNumber = fd["payment_card_num"] ?: throw Exception(),
                        expireDate = fd["payment_cvv"] ?: throw Exception(),
                        cvc = fd["payment_cvv"] ?: throw Exception()
                    )

                    daoPayment.addPayment(newPayment) ?: throw Exception()

                    call.respondRedirect("/myspace")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            get("payments/delete/{id}") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession
                    val id = call.parameters["id"]?.toInt() ?: throw Exception();
                    val daoPayment = DAOPayment()

                    daoPayment.getPayment(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    if(!daoPayment.deletePayment(id)) throw Exception()

                    call.respondRedirect("/myspace")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            get("addresses/add") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession

                    val template = AddressAddPageTemplate(session!!.user)

                    call.respondHtmlTemplate(template){

                    }

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            post("addresses/add") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession
                    val daoAddress = DAOAddress()
                    val fd = call.receiveParameters()

                    val newAddress = Address(
                        userID = session!!.user.id!!,
                        street = fd["address_street"] ?: throw Exception(),
                        city = fd["address_city"] ?: throw Exception(),
                        country = fd["address_country"] ?: throw Exception(),
                        zipCode = fd["address_zip_code"] ?: throw Exception()
                    )

                    daoAddress.addAddress(newAddress) ?: throw Exception()

                    call.respondRedirect("/myspace")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

            get("addresses/delete/{id}") {
                try {
                    val session = call.sessions.get("user_session") as? UserSession
                    val id = call.parameters["id"]?.toInt() ?: throw Exception();
                    val daoAddress = DAOAddress()

                    daoAddress.getAddress(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    if(!daoAddress.deleteAddress(id)) throw Exception()

                    call.respondRedirect("/myspace")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }
            }

        }

    }

    route("/users"){
        authenticate("auth-session") {
            get("delete/{id}"){
                try {
                    val session = call.sessions.get("user_session") as? UserSession

                    if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                    val id = call.parameters["id"]?.toInt() ?: throw Exception()
                    val daoUser = DAOUser()

                    daoUser.getUser(id) ?: call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    if(!daoUser.deleteUser(id)) throw Exception()

                    call.respondRedirect("/admin")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e
                }

            }
        }
    }

}

