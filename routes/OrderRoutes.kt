package itb.jiafumarc.street.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOAddress
import itb.jiafumarc.street.dao.DAOOrder
import itb.jiafumarc.street.dao.DAOPayment
import itb.jiafumarc.street.dao.DAOShoppingCart
import itb.jiafumarc.street.models.Address
import itb.jiafumarc.street.models.Order
import itb.jiafumarc.street.models.Payment
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.templates.pages.OrderDetailPageTemplate
import itb.jiafumarc.street.templates.pages.OrdersPageTemplate
import itb.jiafumarc.street.templates.pages.ThanksPageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.orderRoutes() {

    route("/orders") {

        authenticate("auth-session") {

            get("my-orders") {
                try {
                    val session = call.sessions.get("user_session") as UserSession
                    val daoOrder = DAOOrder()

                    val orders = daoOrder.getUserOrders(session.user.id ?: -1)

                    val template = OrdersPageTemplate(session.user).apply {
                        this.orders = orders
                    }
                    call.respondHtmlTemplate(template){

                    }
                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                }
            }

            get("my-orders/id/{id}") {

                try {
                    val session = call.sessions.get("user_session") as UserSession
                    val daoOrder = DAOOrder()

                    val id = call.parameters["id"]?.toInt() ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
                    val order = daoOrder.getOrder(id)?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    val template = OrderDetailPageTemplate(session.user).apply {
                        this.order = order
                    }
                    call.respondHtmlTemplate(template){

                    }
                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                }

            }

            post("add") {
                try {
                    val session = call.sessions.get("user_session") as UserSession

                    val fd = call.receiveParameters()
                    val paymentWay = fd["payment_way"]
                    val addressWay = fd["address_way"]

                    val daoOrder = DAOOrder()
                    val daoPayment = DAOPayment()
                    val daoAddress = DAOAddress()
                    val daoShoppingCart = DAOShoppingCart()

                    var orderPaymentID : Int?
                    var orderAddressID : Int?
                    val productsInCart = daoShoppingCart.getUserShoppingCart(session.user.id!!)

                    if(productsInCart.isEmpty()) throw Exception()

                    when (paymentWay) {
                        "new" -> {
                            val owner = fd["payment_owner"] ?: throw Exception()
                            val cardNum = fd["payment_card_num"] ?: throw Exception()
                            val exp = fd["payment_exp"] ?: throw Exception()
                            val cvv = fd["payment_cvv"] ?: throw Exception()

                            val newPayment = daoPayment.addPayment(
                                Payment(
                                    userID = session.user.id!!,
                                    owner = owner,
                                    cardNumber = cardNum,
                                    expireDate = exp,
                                    cvc = cvv
                                )
                            )

                            if(newPayment != null){
                                orderPaymentID = newPayment.id
                            }else{
                                throw Exception()
                            }

                        }
                        "saved" -> {
                            val paymentID = fd["payment_saved"]?.toIntOrNull() ?: throw Exception()
                            orderPaymentID = paymentID
                        }
                        else -> {
                            throw Exception()
                        }
                    }

                    when (addressWay) {
                        "new" -> {
                            val street = fd["address_street"] ?: throw Exception()
                            val city = fd["address_city"] ?: throw Exception()
                            val country = fd["address_country"] ?: throw Exception()
                            val zipCode = fd["address_zip_code"] ?: throw Exception()

                            val newAddress = daoAddress.addAddress(
                                Address(
                                    userID = session.user.id,
                                    street = street,
                                    city = city,
                                    country = country,
                                    zipCode = zipCode
                                )
                            )

                            if(newAddress != null){
                                orderAddressID = newAddress.id
                            }else{
                                throw Exception()
                            }
                        }
                        "saved" -> {
                            val addressID = fd["address_saved"]?.toIntOrNull() ?: throw Exception()
                            orderAddressID = addressID
                        }
                        else -> {
                            throw Exception()
                        }
                    }

                    daoOrder.addOrder(
                        Order(
                            userID = session.user.id,
                            paymentID = orderPaymentID!!,
                            addressID = orderAddressID!!
                        ),
                        productsInCart
                    ) ?: throw Exception()

                    if(!daoShoppingCart.removeAll(session.user.id)) throw Exception()

                    val template = ThanksPageTemplate(session.user)
                    call.respondHtmlTemplate(template){

                    }

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e;
                }

            }

            get("delete/{id}") {

            }

            post("edit") {
                //EXTRA
            }
        }

    }
}