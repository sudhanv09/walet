package io.github.sudhanv09.presentation.common

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object CurrencyFormatter {
    private val formatter: DecimalFormat = NumberFormat.getCurrencyInstance(Locale.getDefault()) as DecimalFormat
    
    init {
        formatter.applyPattern("¤#,##0.00")
    }

    fun format(amount: Double): String {
        return formatter.format(amount)
    }

    fun formatWithoutSymbol(amount: Double): String {
        val cleanFormatter = DecimalFormat("#,##0.00")
        return cleanFormatter.format(amount)
    }

    fun getCurrencySymbol(): String {
        return Currency.getInstance(Locale.getDefault()).symbol
    }
}
