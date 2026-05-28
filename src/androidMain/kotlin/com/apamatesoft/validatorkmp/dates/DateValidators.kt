/**
 * Android implementation of date validation functions using `java.text.SimpleDateFormat`.
 */
package com.apamatesoft.validatorkmp.dates

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

actual fun validateDate(evaluate: String, format: DateFormats): Boolean {
    val pattern = format.pattern()
    if (evaluate.length != pattern.length) return false
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.isLenient = false
    return try {
        sdf.parse(evaluate) != null
    } catch (e: Exception) {
        false
    }
}

actual fun validateMinAge(evaluate: String, format: DateFormats, age: Int): Boolean {
    val pattern = format.pattern()
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.isLenient = false
    val evaluateDate = try {
        sdf.parse(evaluate) ?: return false
    } catch (e: Exception) {
        return false
    }
    val evaluateCalendar = Calendar.getInstance().apply { time = evaluateDate }
    val now = Calendar.getInstance()
    val yearsDiff = now.get(Calendar.YEAR) - evaluateCalendar.get(Calendar.YEAR)
    val monthDiff = now.get(Calendar.MONTH) - evaluateCalendar.get(Calendar.MONTH)
    val dayDiff = now.get(Calendar.DAY_OF_MONTH) - evaluateCalendar.get(Calendar.DAY_OF_MONTH)
    return yearsDiff > age || (yearsDiff == age && (monthDiff > 0 || (monthDiff == 0 && dayDiff >= 0)))
}

actual fun validateExpirationDate(evaluate: String, format: DateFormats): Boolean {
    val pattern = format.pattern()
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    sdf.isLenient = false
    val evaluateDate = try {
        sdf.parse(evaluate) ?: return false
    } catch (e: Exception) {
        return false
    }
    return Date().time > evaluateDate.time
}
