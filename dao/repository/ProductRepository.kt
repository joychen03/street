package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.Product

class ProductRepository {
    companion object{
        val products = listOf(
            Product(
                name = "Air Force ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 1,
                price = 89.9,
                stock = 1000,
                image = "/static/img/init/zap1.jpg"
            ),
            Product(
                name = "Yeezy 666 ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 1,
                price = 69.9,
                stock = 2000,
                image = "/static/img/init/zap2.jpg"
            ),
            Product(
                name = "Tacon sexy ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 1,
                price = 78.9,
                stock = 600,
                image = "/static/img/init/zap3.jpg"
            ),
            Product(
                name = "Camiseta Original ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 2,
                price = 28.9,
                stock = 3000,
                image = "/static/img/init/cam1.jpg"
            ),
            Product(
                name = "Camiseta Black 705 ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 2,
                price = 26.9,
                stock = 2000,
                image = "/static/img/init/cam2.jpg"
            ),
            Product(
                name = "Camiseta Yo soy mucho ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 2,
                price = 55.0,
                stock = 1000,
                image = "/static/img/init/cam3.jpg"
            ),
            Product(
                name = "Pantalon Fresh ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 3,
                price = 49.9,
                stock = 2500,
                image = "/static/img/init/pant1.jpg"
            ),
            Product(
                name = "Pantalon Fresh ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 3,
                price = 49.9,
                stock = 2500,
                image = "/static/img/init/pant2.jpg"
            ),
            Product(
                name = "Pantalon Panter ITB",
                description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias desde el año 1500",
                categoryID = 3,
                price = 45.9,
                stock = 1600,
                image = "/static/img/init/pant3.jpg"
            )


        )
    }
}