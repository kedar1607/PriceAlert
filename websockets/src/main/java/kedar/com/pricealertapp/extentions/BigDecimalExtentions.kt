package kedar.com.pricealertapp.extentions

import kedar.com.pricealertapp.Formatter
import java.math.BigDecimal
import java.util.*

fun BigDecimal.getCurrencyFormat(locale: Locale = Locale.US, fractionDigits: Int = 2): String =
    Formatter.currencyFormatter(locale, fractionDigits).format(this)