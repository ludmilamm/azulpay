package br.com.azulpay.presentation.common.custom

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.NumberFormat
import java.util.*


class CurrencyValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value)
    }
}