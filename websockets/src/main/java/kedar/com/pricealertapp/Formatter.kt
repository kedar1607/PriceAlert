package kedar.com.pricealertapp

import java.text.NumberFormat
import java.util.*

object Formatter {
    fun currencyFormatter(locale: Locale = Locale.US, fractionDigits: Int  = 2): NumberFormat{
        return NumberFormat.getCurrencyInstance(locale).apply { maximumFractionDigits = fractionDigits }
    }
}