package itb.jiafumarc.street.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOAddress
import itb.jiafumarc.street.dao.DAOPayment
import itb.jiafumarc.street.dao.DAOProduct
import itb.jiafumarc.street.dao.DAOShoppingCart
import itb.jiafumarc.street.models.ShoppingCart
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.templates.pages.ShoppingCartPageTemplate
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.shoppingCartRoute() {

    route("/shopping-cart") {

        authenticate("auth-session") {

            get("") {
                try {
                    val session = call.sessions.get("user_session") as UserSession

                    val daoShoppingCart = DAOShoppingCart()
                    val daoPayment = DAOPayment()
                    val daoAddress = DAOAddress()

                    val userShoppingCartProducts = daoShoppingCart.getUserShoppingCart(session.user.id!!)
                    val userPayments = daoPayment.getUserPayments(session.user.id)
                    val userAddresses = daoAddress.getUserAddresses(session.user.id)

                    val template = ShoppingCartPageTemplate(session.user).apply {
                        productsInCart = userShoppingCartProducts
                        payments = userPayments
                        addresses = userAddresses
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

                    val daoShoppingCart = DAOShoppingCart()
                    val daoProduct = DAOProduct()

                    val fd = call.receiveParameters()
                    val productID = fd["product_id"]?.toIntOrNull() ?: return@post call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
                    val quantity = fd["product_qty"]?.toIntOrNull()

                    //Precio de cesta sera igual que el precio original de articulo, podria haver casos con descuentos. Se controlaria por aqui
                    val product = daoProduct.getProduct(productID)
                    val existsProductInCart = daoShoppingCart.checkProductExists(session.user.id!!, productID)

                    val shoppingCartItem = ShoppingCart(
                        userID = session.user.id,
                        productID = productID,
                        quantity = quantity ?: 1,
                        price = product?.price ?: 0.0
                    )

                    if(existsProductInCart){
                        daoShoppingCart.updateProduct(shoppingCartItem)
                    }else{
                        daoShoppingCart.addProductInCart(shoppingCartItem)
                    }

                    call.respondRedirect("/shopping-cart")
                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e;
                }

            }

            get("delete/{id}") {
                try {
                    val session = call.sessions.get("user_session") as UserSession
                    val id = call.parameters["id"]?.toInt() ?: throw Exception();

                    val daoShoppingCart = DAOShoppingCart()
                    daoShoppingCart.getShoppingCart(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                    if(!daoShoppingCart.removeProduct(id)) throw Exception()

                    call.respondRedirect("/shopping-cart")

                }catch (e : Exception){
                    call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                    throw e;
                }
            }

        }

    }
}