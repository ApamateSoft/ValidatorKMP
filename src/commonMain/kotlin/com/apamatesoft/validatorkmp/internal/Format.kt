package com.apamatesoft.validatorkmp.internal

/**
 * A minimal cross-platform string formatter supporting printf-style format specifiers.
 *
 * Supported specifiers: `%s`, `%d`, `%f`, `%.Nf`, `%1$...`, `%2$...`, `%%`.
 * This is used internally to format error messages with variable placeholders.
 */
internal fun format(template: String, vararg args: Any?): String {
    val sb = StringBuilder(template.length + 16)
    var i = 0
    var autoIndex = 0
    while (i < template.length) {
        val c = template[i]
        if (c != '%') { sb.append(c); i++; continue }
        if (i + 1 >= template.length) { sb.append('%'); i++; continue }
        val next = template[i + 1]
        if (next == '%') { sb.append('%'); i += 2; continue }

        var j = i + 1
        var index = -1
        val idxStart = j
        while (j < template.length && template[j].isDigit()) j++
        if (j < template.length && template[j] == '$' && j > idxStart) {
            index = template.substring(idxStart, j).toInt() - 1
            j++
        } else {
            j = i + 1
        }
        var precision = -1
        if (j < template.length && template[j] == '.') {
            val pStart = j + 1
            var k = pStart
            while (k < template.length && template[k].isDigit()) k++
            if (k > pStart) {
                precision = template.substring(pStart, k).toInt()
                j = k
            }
        }
        if (j >= template.length) { sb.append('%'); i++; continue }
        val spec = template[j]
        val argIndex = if (index >= 0) index else autoIndex.also { autoIndex++ }
        val arg: Any? = args.getOrNull(argIndex)
        when (spec) {
            's' -> sb.append(arg?.toString() ?: "null")
            'd' -> sb.append(
                when (arg) {
                    is Number -> arg.toLong().toString()
                    null -> "null"
                    else -> arg.toString()
                }
            )
            'f' -> {
                val d = (arg as? Number)?.toDouble() ?: 0.0
                val p = if (precision >= 0) precision else 6
                sb.append(formatDouble(d, p))
            }
            else -> { sb.append('%'); sb.append(spec) }
        }
        i = j + 1
    }
    return sb.toString()
}

private fun formatDouble(value: Double, precision: Int): String {
    if (precision <= 0) return value.toLong().toString()
    val factor = pow10(precision)
    val rounded = kotlin.math.round(value * factor) / factor
    val abs = if (rounded < 0) -rounded else rounded
    val intPart = abs.toLong()
    val frac = abs - intPart
    val fracScaled = kotlin.math.round(frac * factor).toLong()
    val fracStr = fracScaled.toString().padStart(precision, '0')
    val sign = if (rounded < 0) "-" else ""
    return "$sign$intPart.$fracStr"
}

private fun pow10(n: Int): Double {
    var r = 1.0
    repeat(n) { r *= 10.0 }
    return r
}
