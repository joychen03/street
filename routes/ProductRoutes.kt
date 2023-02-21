package itb.jiafumarc.street.routes

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import itb.jiafumarc.street.dao.DAOCategory
import itb.jiafumarc.street.dao.DAOProduct
import itb.jiafumarc.street.models.Category
import itb.jiafumarc.street.models.Product
import itb.jiafumarc.street.models.UserSession
import itb.jiafumarc.street.save
import itb.jiafumarc.street.templates.pages.*
import itb.jiafumarc.street.utils.HttpCodeEnum

fun Route.productRoutes() {
    route("/products") {
        get("id/{id}") {
            try {
                val session = call.sessions.get("user_session") as? UserSession
                val daoProduct = DAOProduct()

                val id = call.parameters["id"]?.toInt() ?: throw Exception()
                val product = daoProduct.getProduct(id)?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                val template = ProductDetailPageTemplate(session?.user).apply {
                    this.product = product
                }
                call.respondHtmlTemplate(template){

                }
            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        get("categories"){
            try{
                val session = call.sessions.get("user_session") as? UserSession
                val daoProduct = DAOCategory()

                val categoryProducts = daoProduct.getAllCategoriesWithCount()
                val template = CategoryPageTemplate(session?.user).apply {
                    this.categories = categoryProducts
                }

                call.respondHtmlTemplate(template){

                }
            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        get("categories/{id}"){
            try {
                val session = call.sessions.get("user_session") as? UserSession
                val daoCategory = DAOCategory()
                val daoProduct = DAOProduct()

                val id = call.parameters["id"]?.toInt() ?: throw Exception()
                val category = daoCategory.getCategory(id)?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
                val products = daoProduct.getProductByCategory(id)

                val template = CategoryProductsPageTemplate(session?.user).apply {
                    this.products = products
                    this.category = category
                }

                call.respondHtmlTemplate(template){

                }
            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        get("add") {
            try {
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoCategory = DAOCategory()

                val categories = daoCategory.getAllCategories()

                val template = ProductAddEditPageTemplate(session!!.user, true ).apply {
                    this.categoryList = categories
                }

                call.respondHtmlTemplate(template){

                }
            }catch (e: Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        post("add") {
            try {
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoProduct = DAOProduct()
                val uploadsFolderURL = "${call.request.origin.scheme}://${call.request.host()}:${call.request.port()}/uploads"

                val multipart = call.receiveMultipart()

                var name: String? = null
                var desc: String? = null
                var price: Double? = null
                var stock: Int? = null
                var category: Int? = null
                var fileName: String? = null
                var noImage : String? = null

                multipart.forEachPart { part ->
                    when(part){
                        is PartData.FormItem ->{
                            if(part.value.isNotEmpty()){
                                when(part.name) {
                                    "product_name" -> name = part.value
                                    "product_desc" -> desc = part.value
                                    "product_price" -> price = part.value.toDoubleOrNull()
                                    "product_stock" -> stock = part.value.toIntOrNull()
                                    "product_category" -> category = part.value.toIntOrNull()
                                    "product_no_image" -> noImage = part.value
                                }
                            }
                        }
                        is PartData.FileItem ->{
                            val file = part.originalFileName as String
                            fileName = if(!file.isEmpty()){
                                part.save()
                            }else{
                                null
                            }
                        }

                        else -> {}
                    }
                }

                val newProduct = Product(
                    name = name ?: throw Exception(),
                    price = price ?: throw Exception(),
                    stock = stock ?: throw Exception(),
                    description = desc,
                    categoryID = category,
                    image = if(!fileName.isNullOrEmpty() && noImage.isNullOrEmpty()){
                        "${uploadsFolderURL}/${fileName}"
                    }else{
                        null
                    }
                )

                if(daoProduct.addProduct(newProduct) == null) throw Exception();

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

        get("edit/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw Exception()
            val session = call.sessions.get("user_session") as? UserSession
            if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

            val daoProduct = DAOProduct()
            val daoCategory = DAOCategory()

            val product = daoProduct.getProduct(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
            val categories = daoCategory.getAllCategories()

            val template = ProductAddEditPageTemplate(session!!.user, false ).apply {
                this.product = product
                this.categoryList = categories
            }

            call.respondHtmlTemplate(template){

            }
        }

        post("edit/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw Exception();
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val uploadsFolderURL = "${call.request.origin.scheme}://${call.request.host()}:${call.request.port()}/uploads"
                val daoProduct = DAOProduct()
                val oldProduct = daoProduct.getProduct(id) ?: return@post call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                val multipart = call.receiveMultipart()

                var noImage : String? = null
                var fileName: String? = null

                multipart.forEachPart { part ->
                    when(part){
                        is PartData.FormItem ->{
                            if(part.value.isNotEmpty()){
                                when(part.name) {
                                    "product_name" -> oldProduct.name = part.value
                                    "product_desc" -> oldProduct.description = part.value
                                    "product_price" -> oldProduct.price = part.value.toDoubleOrNull() ?: throw Exception()
                                    "product_stock" -> oldProduct.stock = part.value.toIntOrNull() ?: throw Exception()
                                    "product_category" -> oldProduct.categoryID = part.value.toIntOrNull()
                                    "product_no_image" -> noImage = part.value
                                }
                            }
                        }
                        is PartData.FileItem ->{
                            val file = part.originalFileName as String
                            if(!file.isEmpty()){
                                fileName =  part.save()
                            }else{
                                fileName = null
                            }
                        }

                        else -> {}
                    }
                }

                oldProduct.image = if(noImage.isNullOrEmpty()){
                    if(fileName.isNullOrEmpty()){
                        oldProduct.image
                    }else{
                        "${uploadsFolderURL}/${fileName}"
                    }
                }else{
                    null
                }

                if(!daoProduct.updateProduct(oldProduct)) throw Exception()

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

        get("delete/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw Exception();
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoProduct = DAOProduct()
                daoProduct.getProduct(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                daoProduct.deleteProduct(id)

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

        get("categories/add") {
            try {
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val template = CategoryAddEditPageTemplate(session!!.user, true)

                call.respondHtmlTemplate(template){

                }

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        post("categories/add") {
            try {
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoCategory = DAOCategory()
                val uploadsFolderURL = "${call.request.origin.scheme}://${call.request.host()}:${call.request.port()}/uploads"
                val multipart = call.receiveMultipart()

                var name: String? = null
                var fileName: String? = null
                var noImage : String? = null

                multipart.forEachPart { part ->
                    when(part){
                        is PartData.FormItem ->{
                            if(part.value.isNotEmpty()){
                                when(part.name) {
                                    "category_name" -> name = part.value
                                    "category_no_image" -> noImage = part.value
                                }
                            }
                        }
                        is PartData.FileItem ->{
                            val file = part.originalFileName as String
                            fileName = if(!file.isEmpty()){
                                part.save()
                            }else{
                                null
                            }
                        }

                        else -> {}
                    }
                }

                val newCategory = Category(
                    name = name ?: throw Exception(),
                    image = if(!fileName.isNullOrEmpty() && noImage.isNullOrEmpty()){
                        "${uploadsFolderURL}/${fileName}"
                    }else{
                        null
                    }
                )

                daoCategory.addCategory(newCategory) ?: throw Exception()

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

        get("categories/edit/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw  Exception()
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoCategory = DAOCategory()
                val category = daoCategory.getCategory(id) ?: return@get call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                val template = CategoryAddEditPageTemplate(session!!.user, false).apply {
                    this.category = category
                }

                call.respondHtmlTemplate(template){

                }

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }

        }

        post("categories/edit/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw  Exception()
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoCategory = DAOCategory()
                val oldCategory = daoCategory.getCategory(id) ?: return@post call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")
                val uploadsFolderURL = "${call.request.origin.scheme}://${call.request.host()}:${call.request.port()}/uploads"

                val multipart = call.receiveMultipart()

                var noImage : String? = null
                var fileName: String? = null

                multipart.forEachPart { part ->
                    when(part){
                        is PartData.FormItem ->{
                            if(part.value.isNotEmpty()){
                                when(part.name) {
                                    "category_name" -> oldCategory.name = part.value
                                    "category_no_image" -> noImage = part.value
                                }
                            }
                        }
                        is PartData.FileItem ->{
                            val file = part.originalFileName as String
                            fileName = if(!file.isEmpty()){
                                part.save()
                            }else{
                                null
                            }
                        }

                        else -> {}
                    }
                }

                oldCategory.image = if(noImage.isNullOrEmpty()){
                    if(fileName.isNullOrEmpty()){
                        oldCategory.image
                    }else{
                        "${uploadsFolderURL}/${fileName}"
                    }
                }else{
                    null
                }

                if(!daoCategory.updateCategory(oldCategory)) throw Exception()

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

        get("categories/delete/{id}") {
            try {
                val id = call.parameters["id"]?.toInt() ?: throw  Exception()
                val session = call.sessions.get("user_session") as? UserSession
                if(!session!!.user.isAdmin) call.respondRedirect("/error/${HttpCodeEnum.UNAUTHORIZED}")

                val daoCategory = DAOCategory()
                daoCategory.getCategory(id) ?: call.respondRedirect("/error/${HttpCodeEnum.NOT_FOUND}")

                if(!daoCategory.deleteCategory(id)) throw Exception()

                call.respondRedirect("/admin")

            }catch (e : Exception){
                call.respondRedirect("/error/${HttpCodeEnum.SERVER_ERROR}")
                throw e
            }
        }

    }

}

