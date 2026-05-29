Versión en [Inglés](../README.md)

# ValidatorKMP

Facilita la validación de Strings encadenando una serie de reglas. Librería Kotlin Multiplatform para **Android** e **iOS**.

[![Maven Central](https://img.shields.io/maven-central/v/com.apamatesoft/ValidatorKMP?color=blue)](https://central.sonatype.com/artifact/com.apamatesoft/ValidatorKMP)

## Instalación

### Gradle (Android / KMP)

```kotlin
implementation("com.apamatesoft:ValidatorKMP:1.0.0")
```

### Maven

```xml
<dependency>
    <groupId>com.apamatesoft</groupId>
    <artifactId>ValidatorKMP</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Swift Package Manager (iOS)

1. Compilar el XCFramework: `./gradlew assembleValidatorKMPXCFramework`
2. Alojar `ValidatorKMP.xcframework.zip` en un GitHub Release
3. Agregar al `Package.swift`:

```swift
.binaryTarget(
    name: "ValidatorKMP",
    url: "https://github.com/ApamateSoft/ValidatorKMP/releases/download/1.0.0/ValidatorKMP.xcframework.zip",
    checksum: "<<<SHA256_OF_ZIP>>>"
)
```

## Empezando

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
    .required(message: "Requerido")
    .email(message: "Correo inválido")
    .build()

do {
    try validator.validOrFail(key: "email", evaluate: "usuario@ejemplo.com")
    print("Válido")
} catch let e as InvalidEvaluationException {
    print("key=\(e.key) value=\(e.value ?? "nil") msg=\(e.message ?? "")")
} catch {
    print("Error desconocido: \(error)")
}
```

## Reglas personalizadas

```kotlin
val validator = Validator()
validator.rule("Debe ser 'xxx'") { evaluate -> evaluate == "xxx" }
```

## Reglas predefinidas

| Regla               | Descripción                                                                                                 |
|---------------------|-------------------------------------------------------------------------------------------------------------|
| `required`          | Valida que el String no sea nulo ni vacío                                                                  |
| `length`            | Valida que el String tenga una longitud exacta de caracteres                                               |
| `minLength`         | Valida que la longitud del String no sea menor que la condición                                            |
| `maxLength`         | Valida que la longitud del String no sea mayor que la condición                                             |
| `rangeLength`       | Valida que la longitud del String esté en el rango establecido                                             |
| `regExp`            | Valida que el String coincida con la expresión regular                                                     |
| `email`             | Valida que el String tenga formato de correo electrónico                                                   |
| `number`            | Valida que el String tenga formato numérico                                                                |
| `link`              | Valida que el String sea un enlace                                                                          |
| `wwwLink`           | Valida que el String sea un enlace con formato www                                                         |
| `httpLink`          | Valida que el String sea un enlace con formato http                                                        |
| `httpsLink`         | Valida que el String sea un enlace con formato https                                                       |
| `ip`                | Valida que el String tenga formato de IP                                                                   |
| `ipv4`              | Valida que el String tenga formato de IPv4                                                                  |
| `ipv6`              | Valida que el String tenga formato de IPv6                                                                  |
| `time`              | Valida que el String tenga formato de hora                                                                 |
| `time12`            | Valida que el String tenga formato de hora de 12 horas                                                     |
| `time24`            | Valida que el String tenga formato de hora de 24 horas                                                    |
| `name`              | Valida que el String sea un nombre propio                                                                  |
| `numberPattern`     | Valida que el String coincida con el patrón, reemplazando las x con números                               |
| `onlyLetters`       | Valida que el String contenga solo letras                                                                  |
| `onlyAlphanumeric`  | Valida que el String contenga solo caracteres alfanuméricos                                                |
| `onlyNumbers`       | Valida que el String contenga solo caracteres numéricos                                                   |
| `shouldOnlyContain` | Valida que el String solo contenga caracteres incluidos en el alfabeto                                     |
| `notContain`        | Valida que el String no contenga caracteres incluidos en el alfabeto                                      |
| `mustContainOne`    | Valida que el String contenga al menos un carácter incluido en el alfabeto                                |
| `mustContainMin`    | Valida que el String contenga al menos un mínimo de caracteres incluidos en el alfabeto                    |
| `maxValue`          | Valida que el valor numérico del String no sea mayor que la condición                                      |
| `minValue`          | Valida que el valor numérico del String no sea menor que la condición                                     |
| `rangeValue`        | Valida que el valor numérico del String esté en el rango establecido                                       |
| `date`              | Valida que el String coincida con un formato de fecha predefinido                                          |
| `minAge`            | Valida que el período desde la fecha ingresada hasta la fecha actual sea mayor o igual a una edad mínima  |
| `expirationDate`   | Valida que la fecha ingresada no haya expirado                                                              |

### Patrón Builder

```kotlin
val validator = Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build()
```

Todas las reglas predefinidas aceptan un parámetro opcional `message`. Si se omite, se utiliza el mensaje por defecto.

### Comparar dos Strings

```kotlin
val validator = Validator.Builder()
    .required()
    .minLength(8)
    .setNotMatchMessage("Las contraseñas no coinciden")
    .build()

try {
    validator.compareOrFail("password", "abc12345", "abc12345")
} catch (e: InvalidEvaluationException) {
    println(e.message)
}
```

### Copiar un validador

```kotlin
val template = Validator.Builder()
    .required()
    .email()
    .build()

val copy1 = template.copy()
val copy2 = template.copy()
```

## Validación de fechas

Las reglas de fecha utilizan el enum `DateFormats` en lugar de cadenas de formato para compatibilidad multiplataforma.

```kotlin
val validator = Validator.Builder()
    .date(DateFormats.DD_MM_YYYY)
    .minAge(DateFormats.DD_MM_YYYY, 18)
    .expirationDate(DateFormats.MM_DD_YYYY)
    .build()
```

Formatos disponibles: `YYYY_MM_DD`, `DD_MM_YYYY`, `MM_DD_YYYY`, `YYYY_MM_DD_HH_MM`, `DD_MM_YYYY_HH_MM`, `HH_MM`, `HH_MM_SS`.

## Mensajes por defecto

Los mensajes en las reglas predefinidas son opcionales. Los mensajes por defecto provienen de `MessagesEn`.

### Cambiar idioma de los mensajes

```kotlin
Validator.setMessages(MessagesEs())
```

| Regla               | Inglés (Por defecto)                                     | Español                                                   |
|---------------------|----------------------------------------------------------|-----------------------------------------------------------|
| `compare`           | Not match                                                | No coinciden                                              |
| `date`              | The date does not match the format %s                    | La fecha no coincide con el formato %s                    |
| `email`             | Email invalid                                            | Correo electrónico invalido                               |
| `expirationDate`    | Expired date                                             | Fecha expirada                                            |
| `httpLink`          | Invalid http link                                        | Enlace http inválido                                      |
| `httpsLink`         | Invalid https link                                       | Enlace https inválido                                     |
| `ip`                | Invalid IP                                               | IP inválida                                               |
| `ipv4`              | Invalid IPv4                                             | IPv4 inválida                                             |
| `ipv6`              | Invalid IPv6                                             | IPv6 inválida                                             |
| `length`            | It requires %d characters                                | Se requiere %d caracteres                                 |
| `link`              | Invalid link                                             | Enlace inválido                                           |
| `maxLength`         | %d or less characters required                           | Se requiere %d o menos caracteres                         |
| `maxValue`          | The value cannot be greater than %1$.2f                  | El valor no puede ser mayor a %1$.2f                      |
| `minAge`            | You must be at least %d years old                        | Se debe tener al menos %d años                            |
| `minLength`         | %d or more characters are required                       | Se requiere %d o más caracteres                           |
| `minValue`          | The value cannot be less than %1$.2f                     | El valor no puede ser menor a %1$.2f                      |
| `mustContainMin`    | At least %d of the following characters are required: %s | Se requiere al menos %d de los siguientes caracteres: %s   |
| `mustContainOne`    | At least one of the following characters is required: %s | Se requiere al menos uno de los siguientes caracteres: %s  |
| `name`              | Invalid personal name                                    | Nombre personal inválido                                  |
| `notContain`        | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s                |
| `number`            | It is not a number                                       | No es un número                                           |
| `numberPattern`     | Does not match pattern %s                                | No coincide con el patrón %s                              |
| `onlyAlphanumeric`  | Just alphanumeric characters                             | Solo caracteres alfanuméricos                             |
| `onlyLetters`       | Only letters                                             | Solo letras                                               |
| `onlyNumbers`       | Only numbers                                             | Solo números                                              |
| `rangeLength`       | The text must contain between %d to %d characters        | El texto debe contener entre %d a %d caracteres           |
| `rangeValue`        | The value must be between %1$.2f and %2$.2f              | El valor debe estar entre %1$.2f y %2$.2f                 |
| `regExp`            | The value does not match the regular expression %s       | El valor no coincide con la expresión regular %s          |
| `required`          | Required                                                 | Requerido                                                 |
| `shouldOnlyContain` | They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s              |
| `time`              | Time invalid                                             | Hora inválida                                             |
| `time12`            | Invalid 12 hour format                                   | Formato 12 horas invalido                                 |
| `time24`            | Invalid 24 hour format                                   | Formato 24 horas invalido                                 |
| `wwwLink`           | Invalid www link                                         | Enlace www inválido                                       |

## Utilidades

### Alfabetos

| Nombre              | Alfabeto                                                                    |
|---------------------|-----------------------------------------------------------------------------|
| BIN                 | 01                                                                          |
| OCT                 | 01234567                                                                    |
| HEX                 | 0123456789aAbBcCdDeEfF                                                      |
| NUMBER              | 0123456789                                                                  |
| ALPHABET            | aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ                        |
| ALPHA_LOWERCASE     | abcdefghijklmnopqrstuvwxyz                                                  |
| ALPHA_UPPERCASE     | ABCDEFGHIJKLMNOPQRSTUVWXYZ                                                  |
| ALPHA_NUMERIC       | 0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ              |
| ALPHABET_ES         | aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚüÜvVwWxXyYzZ          |
| ALPHA_LOWERCASE_ES  | aábcdeéfghiíjklmnñoópqrstuúüvwxyz                                            |
| ALPHA_UPPERCASE_ES  | AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚÜVWXYZ                                            |
| ALPHA_NUMERIC_ES    | 0123456789aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚüÜvVwWxXyYzZ |

### Expresiones regulares

| Nombre                | Patrón                                                        |
|-----------------------|---------------------------------------------------------------|
| NUMBER                | `^\d+$`                                                       |
| ALPHABET              | `^[a-zA-Z]+$`                                                 |
| ALPHABET_UPPERCASE    | `^[A-Z]+$`                                                    |
| ALPHABET_LOWERCASE    | `^[a-z]+$`                                                    |
| SPECIAL_CHARACTERS    | `^[^A-z\s\d][\\\^]?$`                                        |
| ALPHABET_ES           | `^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$`                                  |
| ALPHA_NUMERIC         | `^[a-zA-Z0-9]+$`                                              |
| ALPHA_NUMERIC_ES      | `^[a-zA-Z0-9áéíóúüñÁÉÍÓÚÜÑ]+$`                               |
| NAME                  | `^[a-zA-Z]+(\s?[a-zA-Z])*$`                                   |
| NAME_LOWERCASE        | `^[a-z]+(\s?[a-z])*$`                                         |
| NAME_UPPERCASE        | `^[A-Z]+(\s?[A-Z])*$`                                         |
| NAME_CAPITALIZE       | `^[A-Z][a-z]+(\s?[A-Z][a-z]+)*$`                              |
| NAME_ES               | `^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+(\s?[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ])*$`      |
| NAME_LOWERCASE_ES     | `^[a-záéíóúüñ]+(\s?[a-záéíóúüñ])*$`                          |
| NAME_UPPERCASE_ES     | `^[A-ZÁÉÍÓÚÜÑ]+(\s?[A-ZÁÉÍÓÚÜÑ])*$`                          |
| NAME_CAPITALIZE_ES    | `^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(\s?[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$` |
| INTEGER               | `^-?\d+$`                                                     |
| DECIMAL               | `^-?\d*\.?\d+?$`                                              |
| EMAIL                 | ver código fuente                                             |
| LINK                  | ver código fuente                                             |
| WWW_LINK              | ver código fuente                                             |
| HTTP_LINK             | ver código fuente                                             |
| HTTPS_LINK            | ver código fuente                                             |
| IP                    | ver código fuente                                             |
| IPV4                  | ver código fuente                                             |
| IPV6                  | ver código fuente                                             |
| PHONE                 | ver código fuente                                             |
| TIME                  | ver código fuente                                             |
| TIME12                | ver código fuente                                             |
| TIME24                | ver código fuente                                             |
| CARD                  | ver código fuente                                             |

### Validadores

Todas las reglas utilizan validadores booleanos puros disponibles en el objeto `Validators`.

```kotlin
import com.apamatesoft.validatorkmp.utils.Validators
import com.apamatesoft.validatorkmp.dates.DateFormats

Validators.required("hello")           // true
Validators.email("a@b.com")           // true
Validators.minAge("2000-01-01", DateFormats.YYYY_MM_DD, 18) // depende de la fecha actual
```

## Recomendaciones y ejemplos

Comúnmente se requieren validar múltiples Strings con las mismas reglas. Se recomienda definir los validadores por contexto, usando el método `.copy()` para reutilizar.

```kotlin
import com.apamatesoft.validatorkmp.Validator
import com.apamatesoft.validatorkmp.exceptions.InvalidEvaluationException
import com.apamatesoft.validatorkmp.utils.Alphabets.NUMBER
import com.apamatesoft.validatorkmp.utils.Alphabets.ALPHA_LOWERCASE
import com.apamatesoft.validatorkmp.utils.Alphabets.ALPHA_UPPERCASE

val emailValidator = Validator.Builder()
    .required()
    .email()
    .build()

val phoneValidator = Validator.Builder()
    .required()
    .numberPattern("(xxxx) xx-xx-xxx")
    .build()

val passwordValidator = Validator.Builder()
    .required()
    .minLength(12)
    .mustContainMin(3, ALPHA_LOWERCASE)
    .mustContainMin(3, ALPHA_UPPERCASE)
    .mustContainMin(3, NUMBER)
    .mustContainMin(3, "@~_/")
    .build()
```

### Usando copias

```kotlin
val emailCopy = emailValidator.copy()
val passwordCopy = passwordValidator.copy()

try {
    emailCopy.validOrFail("email", "user@example.com")
    passwordCopy.compareOrFail("psw", "abc12345678", "abc12345678")
} catch (e: InvalidEvaluationException) {
    when (e.key) {
        "email" -> println("Error en correo: ${e.message}")
        "psw"   -> println("Error en contraseña: ${e.message}")
    }
}
```

## Cambios respecto a v1.x (Java)

- **Kotlin Multiplatform**: Targets Android + iOS. No hay target JVM independiente.
- **Sin anotaciones**: `@Required`, `@Email`, etc. fueron eliminados.
- **Sin `isValid`/`isMatch`/`onInvalidEvaluation`**: Usar `validOrFail`/`compareOrFail` con try/catch.
- **Sin `Validator.validOrFail(Object)`**: El validador estático con reflexión POJO fue eliminado.
- **Reglas de fecha usan el enum `DateFormats`** en lugar de cadenas de formato.
- **Correcciones**: `rangeValueMessage` ahora usa `%2$.2f` para el máximo; `ALPHA_NUMERIC_ES` ahora incluye caracteres españoles; `üÜ` fue añadido a todos los alfabetos españoles.
- **Paquete**: `com.apamatesoft.validatorkmp`

## Licencia

Licencia MIT. Ver [LICENSE](../LICENSE) para más detalles.