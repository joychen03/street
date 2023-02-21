package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.Category

class CategoryRepository {
    companion object{
        val categories = listOf(
            Category(
                name = "Zapatos",
                image = "/static/img/init/cate1.jpg"

            ),
            Category(
                name = "Camisetas",
                image = "/static/img/init/cate2.jpg"
            ),
            Category(
                name = "Pantalones",
                image = "/static/img/init/cate3.jpg"
            ),
        )
    }
}