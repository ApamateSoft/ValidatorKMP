/**
 * iOS implementation of [DateFormats.pattern].
 * Returns Unicode date format pattern strings compatible with `NSDateFormatter`.
 */
package com.apamatesoft.validatorkmp.dates

actual fun DateFormats.pattern(): String = when (this) {
    DateFormats.YYYY_MM_DD       -> "yyyy-MM-dd"
    DateFormats.DD_MM_YYYY       -> "dd/MM/yyyy"
    DateFormats.MM_DD_YYYY       -> "MM/dd/yyyy"
    DateFormats.YYYY_MM_DD_HH_MM -> "yyyy-MM-dd HH:mm"
    DateFormats.DD_MM_YYYY_HH_MM -> "dd/MM/yyyy HH:mm"
    DateFormats.HH_MM             -> "HH:mm"
    DateFormats.HH_MM_SS          -> "HH:mm:ss"
}
