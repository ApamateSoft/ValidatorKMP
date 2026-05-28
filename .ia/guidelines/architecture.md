# Arquitectura

## Estructura de módulos

```
src/
├── commonMain/          # Código compartido (API pública completa)
├── androidMain/        # Implementaciones Android (actual de expect/actual)
├── iosMain/             # Implementaciones iOS (actual de expect/actual)
├── commonTest/          # Tests compartidos (kotlin.test)
└── androidInstrumentedTest/  # Tests que requieren runtime Android
```

## Paquetes

Todos los archivos públicos usan `com.apamatesoft.validatorkmp` o subpaquetes:

| Subpaquete | Contenido | Visibilidad |
|---|---|---|
| (raíz) | `Validator`, `Validator.Builder`, `Rule`, `Validate` | `Rule` es `internal` |
| `.dates` | `DateFormats` (enum), `validateDate`, `validateMinAge`, `validateExpirationDate`, `pattern()` | Público |
| `.exceptions` | `InvalidEvaluationException` | Público |
| `.internal` | `format()` | `internal` |
| `.messages` | `Messages` (interface), `MessagesEn`, `MessagesEs` | Público |
| `.utils` | `Alphabets` (object), `RegularExpression` (object), `Validators` (object) | Público |

## Patrones clave

### Expect/Actual

Las funciones de validación de fechas y `DateFormats.pattern()` usan `expect` en commonMain y `actual` en androidMain/iosMain:

```kotlin
// commonMain/dates/DateValidators.kt
expect fun validateDate(evaluate: String, format: DateFormats): Boolean
expect fun validateMinAge(evaluate: String, format: DateFormats, age: Int): Boolean
expect fun validateExpirationDate(evaluate: String, format: DateFormats): Boolean

// commonMain/dates/DateFormats.kt
expect fun DateFormats.pattern(): String
```

- **Android** usa `java.text.SimpleDateFormat` y `java.util.Calendar`.
- **iOS** usa `NSDateFormatter` y `NSCalendar`.

### Builder Pattern

`Validator.Builder` permite construcción declarativa y encadenable:

```kotlin
val validator = Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build()
```

Cada método del Builder retorna `Builder` para encadenamiento. `build()` retorna un `Validator` inmutable.

### Reglas (Rule + Validate)

Las reglas se almacenan como `Rule(message, validate)` donde `Validate` es un typealias:

```kotlin
typealias Validate = (String?) -> Boolean
```

Las reglas se evalúan en orden. Si una falla, se lanza `InvalidEvaluationException` y no se evalúan las restantes.

### Mensajes (Messages)

- Interface `Messages` con 34 propiedades de mensajes.
- `MessagesEn` (default) y `MessagesEs` son implementaciones concretas.
- Se cambian globalmente via `Validator.setMessages(MessagesEs())`.
- Los mensajes soportan formato printf: `%d`, `%s`, `%1$.2f`, `%2$.2f`.
- El formateo se hace con `internal fun format()` (no `String.format`, no disponible en KMP).

### Validadores Booleanos

El object `Validators` contiene funciones booleanas puras. Cada regla de `Validator` delega a una función de `Validators`. Esto permite uso directo sin construir un `Validator`:

```kotlin
Validators.required("hello")  // true
Validators.email("a@b.com")   // true
```

## Convenciones

- **No `java.*`** en commonMain. Todo debe ser KMP-compatible.
- **No dependencias externas** en commonMain.
- **`@Throws(InvalidEvaluationException::class)`** en todos los métodos `*OrFail`.
- **KDoc** en todo miembro público.
- **Tests**: `commonTest` para lógica compartida, `androidInstrumentedTest` para código que requiere runtime Android (fechas).
- **`internal`** para lo que no es API pública (`Rule`, `Format`).