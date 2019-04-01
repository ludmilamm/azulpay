package br.com.azulpay.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun getLocale() = Locale("pt", "BR")

fun Long.formatToDateFormat(format: String): String {
    return try {
        val simpleDateFormat = SimpleDateFormat(format, getLocale())
        val date = Date(this)
        simpleDateFormat.format(date)
    } catch (e: Exception) {
        ""
    }
}

fun String.parseToDateMillis(format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): Long? {
    val simpleDateFormat = SimpleDateFormat(format, getLocale())
    return try {
        val date = simpleDateFormat.parse(this)
        date.time
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}