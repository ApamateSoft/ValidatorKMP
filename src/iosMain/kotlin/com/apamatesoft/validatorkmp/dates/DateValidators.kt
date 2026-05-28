/**
 * iOS implementation of date validation functions using `NSDateFormatter` and `NSCalendar`.
 */
package com.apamatesoft.validatorkmp.dates

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarOptions
import platform.Foundation.NSLocale
import platform.Foundation.date
import platform.Foundation.timeIntervalSince

actual fun validateDate(evaluate: String, format: DateFormats): Boolean {
    val formatter = NSDateFormatter()
    formatter.dateFormat = format.pattern()
    formatter.locale = NSLocale.currentLocale
    val pattern = format.pattern()
    if (evaluate.length != pattern.length) return false
    return formatter.dateFromString(evaluate) != null
}

actual fun validateMinAge(evaluate: String, format: DateFormats, age: Int): Boolean {
    val formatter = NSDateFormatter()
    formatter.dateFormat = format.pattern()
    formatter.locale = NSLocale.currentLocale
    val evaluateDate = formatter.dateFromString(evaluate) ?: return false
    val calendar = NSCalendar.currentCalendar
    val now = NSDate()
    val components = calendar.components(
        NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
        fromDate = evaluateDate,
        toDate = now,
        options = NSCalendarOptions(0)
    )
    val years = components.year.toInt()
    return years >= age
}

actual fun validateExpirationDate(evaluate: String, format: DateFormats): Boolean {
    val formatter = NSDateFormatter()
    formatter.dateFormat = format.pattern()
    formatter.locale = NSLocale.currentLocale
    val evaluateDate = formatter.dateFromString(evaluate) ?: return false
    return NSDate().timeIntervalSince(evaluateDate) > 0.0
}
