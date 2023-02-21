package itb.jiafumarc.street.dao.repository

import itb.jiafumarc.street.models.Address

class AddressRepository {
    companion object{
        val addresses = listOf(
            Address(
                userID = 1,
                street = "Calle de fenals 14, 3-4",
                city = "Barcelona",
                country = "España",
                zipCode = "08600",
            ),
            Address(
                userID = 1,
                street = "Via julia 101, 1-2",
                city = "Vic",
                country = "España",
                zipCode = "08223",
            )

        )
    }
}