# ValidatorKMP

Facilitates the validation of Strings by chaining series of rules. Kotlin Multiplatform library targeting **Android** and **iOS**.

## Translations
- [Spanish](translations/README-es.md)

## Installation

### Gradle (Android)

```kotlin
implementation("com.apamatesoft:ValidatorKMP:2.0.0-beta01")
```

### Swift Package Manager (iOS)

1. Build the XCFramework: `./gradlew assembleValidatorKMPXCFramework`
2. Host `ValidatorKMP.xcframework.zip` on a GitHub Release
3. Add to your `Package.swift`:

```swift
.binaryTarget(
    name: "ValidatorKMP",
    url: "https://github.com/ApamateSoft/ValidatorKMP/releases/download/2.0.0-beta01/ValidatorKMP.xcframework.zip",
    checksum: "<<<SHA256_OF_ZIP>>>"
)
```

## Getting started

### Kotlin (Android / commonMain)

```kotlin
import com.apamatesoft.validatorkmp.Validator
import com.apamatesoft.validatorkmp.exceptions.InvalidEvaluationException

val validator = Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build()

try {
    validator.validOrFail("pin", "123456")
} catch (e: InvalidEvaluationException) {
    println("key=${e.key} value=${e.value} message=${e.message}")
}
```

### Swift (iOS)

```swift
import ValidatorKMP

let validator = Validator.Builder()
    .required(message: "Required")
    .email(message: "Invalid email")
    .build()

do {
    try validator.validOrFail(key: "email", evaluate: "user@example.com")
    print("Valid")
} catch let e as InvalidEvaluationException {
    print("key=\(e.key) value=\(e.value ?? "nil") msg=\(e.message ?? "")")
} catch {
    print("Unknown error: \(error)")
}
```

## Custom rules

```kotlin
val validator = Validator()
validator.rule("Must be 'xxx'") { evaluate -> evaluate == "xxx" }
```

## Predefined rules

| Rule                | Description                                                                                                    |
|---------------------|----------------------------------------------------------------------------------------------------------------|
| `required`          | Validates that the String is not null or empty                                                                 |
| `length`            | Validates that the String has an exact length of characters                                                    |
| `minLength`         | Validates that the String length is not less than the condition                                                |
| `maxLength`         | Validates that the String length is not greater than the condition                                             |
| `rangeLength`       | Validates that the String length is in the established range                                                    |
| `regExp`            | Validates that the String matches a regular expression                                                         |
| `email`             | Validates that the String has an email format                                                                  |
| `number`            | Validates that the String is a numeric format                                                                  |
| `link`              | Validates that the String is a link format                                                                     |
| `wwwLink`           | Validates that the String is a link with www format                                                            |
| `httpLink`          | Validates that the String is a link with http format                                                           |
| `httpsLink`         | Validates that the String is a link with https format                                                          |
| `ip`                | Validates that the String is an IP format                                                                      |
| `ipv4`              | Validates that the String is an IPv4 format                                                                    |
| `ipv6`              | Validates that the String is an IPv6 format                                                                   |
| `time`              | Validates that the String is a time format                                                                     |
| `time12`            | Validates that the String is a time with 12-hour format                                                        |
| `time24`            | Validates that the String is a time with 24-hour format                                                        |
| `name`              | Validates that the String is a proper name                                                                     |
| `numberPattern`     | Validates that the String matches the pattern, replacing x's with numbers                                     |
| `onlyLetters`       | Validates that the String contains only letters                                                                |
| `onlyAlphanumeric`  | Validates that the String contains only alphanumeric characters                                                |
| `onlyNumbers`       | Validates that the String contains only numeric characters                                                      |
| `shouldOnlyContain` | Validates that the String only contains characters included in the alphabet                                     |
| `notContain`        | Validates that the String does not contain any character included in the alphabet                              |
| `mustContainOne`    | Validates that the String contains at least one character included in the alphabet                             |
| `mustContainMin`    | Validates that the String contains at least a minimum number of characters included in the alphabet             |
| `maxValue`          | Validates that the numeric value of the String is not greater than the condition                               |
| `minValue`          | Validates that the numeric value of the String is not less than the condition                                  |
| `rangeValue`        | Validates that the numeric value of the String is in the established range                                     |
| `date`              | Validates that the String matches a predefined date format                                                      |
| `minAge`            | Validates that the period from the entered date to the current date is at least a minimum age                  |
| `expirationDate`   | Validates that the entered date has not expired                                                                 |

### Builder pattern

```kotlin
val validator = Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build()
```

All predefined rules accept an optional `message` parameter. Without it, the default message is used.

### Compare two Strings

```kotlin
val validator = Validator.Builder()
    .required()
    .minLength(8)
    .setNotMatchMessage("Passwords do not match")
    .build()

try {
    validator.compareOrFail("password", "abc12345", "abc12345")
} catch (e: InvalidEvaluationException) {
    println(e.message)
}
```

### Copy a validator

```kotlin
val template = Validator.Builder()
    .required()
    .email()
    .build()

val copy1 = template.copy()
val copy2 = template.copy()
```

## Date validation

Date rules use the `DateFormats` enum instead of raw format strings for cross-platform compatibility.

```kotlin
val validator = Validator.Builder()
    .date(DateFormats.DD_MM_YYYY)
    .minAge(DateFormats.DD_MM_YYYY, 18)
    .expirationDate(DateFormats.MM_DD_YYYY)
    .build()
```

Available formats: `YYYY_MM_DD`, `DD_MM_YYYY`, `MM_DD_YYYY`, `YYYY_MM_DD_HH_MM`, `DD_MM_YYYY_HH_MM`, `HH_MM`, `HH_MM_SS`.

## Default messages

The messages in predefined rules are optional. Default messages come from `MessagesEn`.

### Change message language

```kotlin
Validator.setMessages(MessagesEs())
```

| Rule               | English (Default)                                        | Spanish                                                   |
|--------------------|----------------------------------------------------------|-----------------------------------------------------------|
| `compare`          | Not match                                                | No coinciden                                              |
| `date`             | The date does not match the format %s                    | La fecha no coincide con el formato %s                    |
| `email`            | Email invalid                                            | Correo electrónico invalido                               |
| `expirationDate`   | Expired date                                             | Fecha expirada                                            |
| `httpLink`         | Invalid http link                                        | Enlace http inválido                                      |
| `httpsLink`        | Invalid https link                                       | Enlace https inválido                                     |
| `ip`               | Invalid IP                                               | IP inválida                                               |
| `ipv4`             | Invalid IPv4                                             | IPv4 inválida                                             |
| `ipv6`             | Invalid IPv6                                             | IPv6 inválida                                             |
| `length`           | It requires %d characters                                | Se requiere %d caracteres                                 |
| `link`             | Invalid link                                             | Enlace inválido                                           |
| `maxLength`        | %d or less characters required                           | Se requiere %d o menos caracteres                         |
| `maxValue`         | The value cannot be greater than %1$.2f                  | El valor no puede ser mayor a %1$.2f                      |
| `minAge`           | You must be at least %d years old                        | Se debe tener al menos %d años                            |
| `minLength`        | %d or more characters are required                       | Se requiere %d o más caracteres                           |
| `minValue`         | The value cannot be less than %1$.2f                      | El valor no puede ser menor a %1$.2f                      |
| `mustContainMin`   | At least %d of the following characters are required: %s | Se requiere al menos %d de los siguientes caracteres: %s  |
| `mustContainOne`   | At least one of the following characters is required: %s | Se requiere al menos uno de los siguientes caracteres: %s  |
| `name`             | Invalid personal name                                    | Nombre personal inválido                                  |
| `notContain`       | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s                |
| `number`           | It is not a number                                       | No es un número                                           |
| `numberPattern`    | Does not match pattern %s                                | No coincide con el patrón %s                              |
| `onlyAlphanumeric` | Just alphanumeric characters                             | Solo caracteres alfanuméricos                             |
| `onlyLetters`      | Only letters                                             | Solo letras                                               |
| `onlyNumbers`      | Only numbers                                             | Solo números                                              |
| `rangeLength`      | The text must contain between %d to %d characters        | El texto debe contener entre %d a %d caracteres           |
| `rangeValue`       | The value must be between %1$.2f and %2$.2f              | El valor debe estar entre %1$.2f y %2$.2f                 |
| `regExp`           | The value does not match the regular expression %s       | El valor no coincide con la expresión regular %s          |
| `required`         | Required                                                 | Requerido                                                 |
| `shouldOnlyContain`| They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s              |
| `time`             | Time invalid                                             | Hora inválida                                             |
| `time12`           | Invalid 12 hour format                                   | Formato 12 horas invalido                                 |
| `time24`           | Invalid 24 hour format                                   | Formato 24 horas invalido                                 |
| `wwwLink`          | Invalid www link                                         | Enlace www inválido                                       |

## Utilities

### Alphabets

| Name               | Alphabet                                                                   |
|--------------------|----------------------------------------------------------------------------|
| BIN                | 01                                                                         |
| OCT                | 01234567                                                                   |
| HEX                | 0123456789aAbBcCdDeEfF                                                     |
| NUMBER             | 0123456789                                                                 |
| ALPHABET           | aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ                       |
| ALPHA_LOWERCASE    | abcdefghijklmnopqrstuvwxyz                                                 |
| ALPHA_UPPERCASE    | ABCDEFGHIJKLMNOPQRSTUVWXYZ                                                 |
| ALPHA_NUMERIC      | 0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ             |
| ALPHABET_ES        | aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚüÜvVwWxXyYzZ           |
| ALPHA_LOWERCASE_ES | aábcdeéfghiíjklmnñoópqrstuúüvwxyz                                           |
| ALPHA_UPPERCASE_ES | AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚÜVWXYZ                                           |
| ALPHA_NUMERIC_ES   | 0123456789aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚüÜvVwWxXyYzZ |

### Regular expressions

| Name                 | Pattern                                                       |
|----------------------|---------------------------------------------------------------|
| NUMBER               | `^\d+$`                                                       |
| ALPHABET             | `^[a-zA-Z]+$`                                                 |
| ALPHABET_UPPERCASE   | `^[A-Z]+$`                                                    |
| ALPHABET_LOWERCASE   | `^[a-z]+$`                                                    |
| SPECIAL_CHARACTERS   | `^[^A-z\s\d][\\\^]?$`                                        |
| ALPHABET_ES          | `^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$`                                  |
| ALPHA_NUMERIC        | `^[a-zA-Z0-9]+$`                                              |
| ALPHA_NUMERIC_ES     | `^[a-zA-Z0-9áéíóúüñÁÉÍÓÚÜÑ]+$`                               |
| NAME                 | `^[a-zA-Z]+(\s?[a-zA-Z])*$`                                   |
| NAME_LOWERCASE       | `^[a-z]+(\s?[a-z])*$`                                         |
| NAME_UPPERCASE       | `^[A-Z]+(\s?[A-Z])*$`                                         |
| NAME_CAPITALIZE      | `^[A-Z][a-z]+(\s?[A-Z][a-z]+)*$`                              |
| NAME_ES              | `^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+(\s?[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ])*$`      |
| NAME_LOWERCASE_ES    | `^[a-záéíóúüñ]+(\s?[a-záéíóúüñ])*$`                          |
| NAME_UPPERCASE_ES    | `^[A-ZÁÉÍÓÚÜÑ]+(\s?[A-ZÁÉÍÓÚÜÑ])*$`                          |
| NAME_CAPITALIZE_ES   | `^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(\s?[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$` |
| INTEGER              | `^-?\d+$`                                                     |
| DECIMAL              | `^-?\d*\.?\d+?$`                                              |
| EMAIL                | see source                                                    |
| LINK                 | see source                                                    |
| WWW_LINK             | see source                                                    |
| HTTP_LINK            | see source                                                    |
| HTTPS_LINK           | see source                                                    |
| IP                   | see source                                                    |
| IPV4                 | see source                                                    |
| IPV6                 | see source                                                    |
| PHONE                | see source                                                    |
| TIME                 | see source                                                    |
| TIME12               | see source                                                    |
| TIME24               | see source                                                    |
| CARD                 | see source                                                    |

### Validators

All rules use pure boolean validators available in the `Validators` object.

```kotlin
import com.apamatesoft.validatorkmp.utils.Validators
import com.apamatesoft.validatorkmp.dates.DateFormats

Validators.required("hello")           // true
Validators.email("a@b.com")           // true
Validators.minAge("2000-01-01", DateFormats.YYYY_MM_DD, 18) // depends on current date
```

## What changed from v1.x (Java)

- **Kotlin Multiplatform**: Android + iOS targets. No standalone JVM target.
- **No annotations**: `@Required`, `@Email`, etc. are removed.
- **No `isValid`/`isMatch`/`onInvalidEvaluation`**: Use `validOrFail`/`compareOrFail` with try/catch.
- **No `Validator.validOrFail(Object)`**: The static POJO-reflection validator is removed.
- **Date rules use `DateFormats` enum** instead of raw format strings.
- **Bug fixes**: `rangeValueMessage` now correctly uses `%2$.2f` for max; `ALPHA_NUMERIC_ES` now includes Spanish characters; `üÜ` added to all Spanish alphabets.
- **Package**: `com.apamatesoft.validatorkmp`

## License

MIT License. See [LICENSE](LICENSE) for details.