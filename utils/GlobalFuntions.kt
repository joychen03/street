package itb.jiafumarc.street.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class GlobalFuntions {
    companion object{
        fun getDateTimeNow() : String{
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = Date()
            return formatter.format(date)
        }

        fun formatCreditCardString(input: String): String {
            val stringBuilder = StringBuilder()
            for (i in input.indices) {
                stringBuilder.append(input[i])
                if (i % 4 == 3 && i != input.lastIndex) {
                    stringBuilder.append(" ")
                }
            }
            return stringBuilder.toString()
        }

        fun round2Decimals(value: Double): Double {
            return (value * 100.0).roundToInt() / 100.0
        }


    }


}
