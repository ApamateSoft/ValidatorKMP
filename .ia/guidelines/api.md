# API Pública

## Validator

Clase principal. Valida Strings encadenando reglas.

### Constructores

| Firma | Descripción |
|-------|-------------|
| `Validator()` | Crea un validador vacío sin reglas. |
| `Validator.Builder().build()` | Crea un validador con reglas predefinidas via Builder. |

### Companion Object

| Método | Descripción |
|--------|-------------|
| `setMessages(messages: Messages?)` | Establece la implementación de mensajes por defecto. Si es `null`, no cambia. |
| `getMessages(): Messages` | Retorna la implementación de mensajes actual. |

### Métodos de validación

| Método | Lanza | Descripción |
|--------|-------|-------------|
| `validOrFail(key: String, evaluate: String?)` | `InvalidEvaluationException` | Valida que `evaluate` pase todas las reglas. |
| `compareOrFail(key: String, evaluate: String?, compare: String?)` | `InvalidEvaluationException` | Valida que `evaluate == compare` y luego pasa todas las reglas. |

### Métodos de reglas (por categoría)

Cada regla tiene dos sobrecargas: una con `message` personalizado y otra con el mensaje por defecto.

**Presencia:**
- `required(message)` / `required()`

**Longitud:**
- `length(length, message)` / `length(length)`
- `minLength(min, message)` / `minLength(min)`
- `maxLength(max, message)` / `maxLength(max)`
- `rangeLength(min, max, message)` / `rangeLength(min, max)`

**Formato:**
- `regExp(regExp, message)` / `regExp(regExp)`
- `email(message)` / `email()`
- `number(message)` / `number()`
- `link(message)` / `link()`
- `wwwLink(message)` / `wwwLink()`
- `httpLink(message)` / `httpLink()`
- `httpsLink(message)` / `httpsLink()`
- `ip(message)` / `ip()`
- `ipv4(message)` / `ipv4()`
- `ipv6(message)` / `ipv6()`
- `time(message)` / `time()`
- `time12(message)` / `time12()`
- `time24(message)` / `time24()`
- `numberPattern(pattern, message)` / `numberPattern(pattern)`
- `name(message)` / `name()`

**Contenido:**
- `onlyLetters(message)` / `onlyLetters()`
- `onlyAlphanumeric(message)` / `onlyAlphanumeric()`
- `shouldOnlyContain(alphabet, message)` / `shouldOnlyContain(alphabet)`
- `onlyNumbers(message)` / `onlyNumbers()`
- `notContain(alphabet, message)` / `notContain(alphabet)`
- `mustContainOne(alphabet, message)` / `mustContainOne(alphabet)`
- `mustContainMin(min, alphabet, message)` / `mustContainMin(min, alphabet)`

**Valor numérico:**
- `maxValue(max, message)` / `maxValue(max)`
- `minValue(condition, message)` / `minValue(condition)`
- `rangeValue(min, max, message)` / `rangeValue(min, max)`

**Fecha:**
- `date(format, message)` / `date(format)`
- `minAge(format, age, message)` / `minAge(format, age)`
- `expirationDate(format, message)` / `expirationDate(format)`

**Otros:**
- `rule(message, validate)` — Regla personalizada
- `setNotMatchMessage(message)` — Mensaje cuando compareOrFail falla por no coincidencia
- `copy(): Validator` — Copia profunda del validador

### Validator.Builder

Builder con los mismos métodos de reglas (retornan `Builder`). Termina con `build(): Validator`.

---

## Validate

```kotlin
typealias Validate = (String?) -> Boolean
```

Función que recibe un String nullable y retorna `true` si es válido.

---

## InvalidEvaluationException

```kotlin
class InvalidEvaluationException(
    val key: String,
    val value: String?,
    message: String
) : Exception(message)
```

Lanzada cuando una regla falla. Contiene:
- `key`: identificador del campo validado.
- `value`: el valor evaluado (puede ser `null`).
- `message`: el mensaje de error de la regla que falló.

---

## Validators (object)

Funciones booleanas puras. Todas retornan `Boolean`, todas aceptan `String?` como primer parámetro.

| Función | Firma | Nota |
|---------|-------|------|
| `required` | `(evaluate: String?)` | Retorna `false` si null o vacío |
| `length` | `(evaluate, length: Int)` | |
| `minLength` | `(evaluate, min: Int)` | |
| `maxLength` | `(evaluate, max: Int)` | |
| `rangeLength` | `(evaluate, min, max)` | |
| `regExp` | `(evaluate, regExp: String)` | Usa Kotlin Regex |
| `email` | `(evaluate)` | Usa RegularExpression.EMAIL |
| `number` | `(evaluate)` | Incluye enteros, decimales y negativos |
| `link` | `(evaluate)` | |
| `wwwLink` | `(evaluate)` | |
| `httpLink` | `(evaluate)` | |
| `httpsLink` | `(evaluate)` | |
| `ip` | `(evaluate)` | IPv4 o IPv6 |
| `ipv4` | `(evaluate)` | |
| `ipv6` | `(evaluate)` | |
| `time` | `(evaluate)` | 12h o 24h |
| `time12` | `(evaluate)` | |
| `time24` | `(evaluate)` | |
| `numberPattern` | `(evaluate, pattern)` | `x`/`X` = dígito |
| `name` | `(evaluate)` | Solo inglés |
| `onlyLetters` | `(evaluate)` | Solo inglés |
| `onlyAlphanumeric` | `(evaluate)` | Solo inglés |
| `shouldOnlyContain` | `(evaluate, alphabet)` | |
| `onlyNumbers` | `(evaluate)` | |
| `notContain` | `(evaluate, alphabet)` | |
| `mustContainOne` | `(evaluate, alphabet)` | |
| `mustContainMin` | `(evaluate, min, alphabet)` | |
| `maxValue` | `(evaluate, condition: Double)` | Requiere number() primero |
| `minValue` | `(evaluate, condition: Double)` | Requiere number() primero |
| `rangeValue` | `(evaluate, min, max)` | Requiere number() primero |
| `date` | `(evaluate, format: DateFormats)` | |
| `minAge` | `(evaluate, format, age)` | Usa fecha del dispositivo |
| `expirationDate` | `(evaluate, format)` | Usa fecha del dispositivo |

---

## DateFormats (enum)

| Valor | Formato |
|-------|---------|
| `YYYY_MM_DD` | `yyyy-MM-dd` |
| `DD_MM_YYYY` | `dd/MM/yyyy` |
| `MM_DD_YYYY` | `MM/dd/yyyy` |
| `YYYY_MM_DD_HH_MM` | `yyyy-MM-dd HH:mm` |
| `DD_MM_YYYY_HH_MM` | `dd/MM/yyyy HH:mm` |
| `HH_MM` | `HH:mm` |
| `HH_MM_SS` | `HH:mm:ss` |

Función `DateFormats.pattern(): String` — Retorna el patrón de formato de fecha específico de plataforma.

---

## Messages (interface)

34 propiedades de mensajes. Las implementaciones concretas son `MessagesEn` (por defecto) y `MessagesEs`.

Los mensajes soportan formato printf: `%d`, `%s`, `%1$.2f`, `%2$.2f`.

---

## Alphabets (object)

Constantes de caracteres para usar con `shouldOnlyContain`, `notContain`, `mustContainOne`, `mustContainMin`.

| Nombre | Valor |
|--------|-------|
| `BIN` | `01` |
| `OCT` | `01234567` |
| `HEX` | `0123456789aAbBcCdDeEfF` |
| `NUMBER` | `0123456789` |
| `ALPHABET` |_mixto inglés_ |
| `ALPHA_LOWERCASE` | `abcdefghijklmnopqrstuvwxyz` |
| `ALPHA_UPPERCASE` | `ABCDEFGHIJKLMNOPQRSTUVWXYZ` |
| `ALPHA_NUMERIC` | `NUMBER` + `ALPHABET` |
| `ALPHABET_ES` | _mixto español con ñ, acentos, üÜ_ |
| `ALPHA_LOWERCASE_ES` | _minúsculas español_ |
| `ALPHA_UPPERCASE_ES` | _mayúsculas español_ |
| `ALPHA_NUMERIC_ES` | `NUMBER` + `ALPHABET_ES` |

---

## RegularExpression (object)

34 constantes de patrones regex. Ver código fuente para valores completos (algunos son muy largos para tablas).

| Nombre | Uso |
|--------|-----|
| `NUMBER`, `ALPHABET`, `ALPHA_NUMERIC`, etc. | Regex para validadores |
| `EMAIL`, `LINK`, `WWW_LINK`, etc. | Regex para formatos |
| `NAME`, `NAME_ES`, etc. | Regex para nombres |
| `INTEGER`, `DECIMAL` | Regex para números |
| `IP`, `IPV4`, `IPV6` | Regex para IPs |
| `TIME`, `TIME12`, `TIME24` | Regex para horas |
| `PHONE`, `CARD` | Regex para teléfono y tarjeta |