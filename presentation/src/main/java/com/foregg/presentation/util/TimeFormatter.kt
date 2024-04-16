package com.foregg.presentation.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object TimeFormatter {

    fun parseToLocalDate(date: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")
        return LocalDate.parse(date, formatter)
    }
}