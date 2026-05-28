# Skill: Añadir Regla de Validación

## Objetivo

Añadir una nueva regla de validación al sistema: función en `Validators`, métodos en `Validator` y `Validator.Builder`, mensaje en `Messages`, y tests.

## Prerrequisitos

- La regla debe tener un nombre claro y un propósito único.
- Debe existir una función booleana pura en `Validators` que implemente la lógica.

## Reglas

1. Toda nueva regla requiere cambios en **6 archivos** como mínimo (ver tabla).
2. La función en `Validators` debe aceptar `String?` como primer parámetro y retornar `Boolean`.
3. Los métodos en `Validator` y `Validator.Builder` deben tener dos sobrecargas: una con `message` personalizado y otra sin él (usa mensaje por defecto).
4. El mensaje por defecto se agrega como propiedad en `Messages`, `MessagesEn` y `MessagesEs`.
5. Si el mensaje usa formato (`%d`, `%s`, `%1$.2f`), invocar `format()` desde `internal fun format`.
6. El nombre del mensaje en `Messages` sigue el patrón: `{ruleName}Message` (camelCase).
7. Todo método `*OrFail` en `Validator` que pueda lanzar la excepción debe tener `@Throws(InvalidEvaluationException::class)`.
8. Los tests se colocan en `commonTest` dentro del paquete correspondiente.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `src/commonMain/.../utils/Validators.kt` | Modificar | Añadir función booleana |
| `src/commonMain/.../Validator.kt` | Modificar | Añadir método(s) de regla |
| `src/commonMain/.../messages/Messages.kt` | Modificar | Añadir propiedad de mensaje |
| `src/commonMain/.../messages/MessagesEn.kt` | Modificar | Añadir mensaje en inglés |
| `src/commonMain/.../messages/MessagesEs.kt` | Modificar | Añadir mensaje en español |
| `src/commonTest/.../utils/ValidatorsTest.kt` | Modificar | Añadir tests del validador |
| `src/commonTest/.../ValidatorTest.kt` | Modificar | Añadir tests de la regla |

## Workflow

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Añadir función en `Validators` | Firma: `fun ruleName(evaluate: String?, ...): Boolean`. Retornar `false` si `evaluate` es null/vacío cuando aplique. |
| 2 | Añadir propiedad en `Messages` | `val ruleNameMessage: String` |
| 3 | Añadir mensaje en `MessagesEn` | Ejemplo: `override val ruleNameMessage: String = "Error message %s"` |
| 4 | Añadir mensaje en `MessagesEs` | Ejemplo: `override val ruleNameMessage: String = "Mensaje de error %s"` |
| 5 | Añadir métodos en `Validator` | Dos sobrecargas: `fun ruleName(param, message: String)` y `fun ruleName(param)`. Usar `format()` si el mensaje tiene placeholders. |
| 6 | Añadir métodos en `Validator.Builder` | Mismas dos sobrecargas. Retornan `Builder`. |
| 7 | Añadir tests de `Validators.ruleName()` | Casos: null, vacío, válido, inválido, edge cases. |
| 8 | Añadir tests de `Validator.ruleName()` | Verificar que la regla se agrega correctamente y falla con el mensaje correcto. |
| 9 | Ejecutar tests | `./gradlew testDebugUnitTest --rerun-tasks` debe pasar sin errores. |

## Ejemplo real

Añadir una regla `startsWith` que valida que el String empiece con un prefijo dado:

**1. Validators.kt:**
```kotlin
fun startsWith(evaluate: String?, prefix: String): Boolean {
    if (!required(evaluate)) return false
    return evaluate!!.startsWith(prefix)
}
```

**2. Messages.kt:**
```kotlin
val startsWithMessage: String
```

**3. MessagesEn.kt:**
```kotlin
override val startsWithMessage: String = "Must start with %s"
```

**4. MessagesEs.kt:**
```kotlin
override val startsWithMessage: String = "Debe comenzar con %s"
```

**5. Validator.kt:**
```kotlin
fun startsWith(prefix: String, message: String) =
    rule(format(message, prefix)) { Validators.startsWith(it, prefix) }
fun startsWith(prefix: String) = startsWith(prefix, messages.startsWithMessage)
```

**6. Validator.Builder:**
```kotlin
fun startsWith(prefix: String, message: String): Builder =
    rule(format(message, prefix)) { Validators.startsWith(it, prefix) }
fun startsWith(prefix: String): Builder = startsWith(prefix, messages.startsWithMessage)
```

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| Añadir lógica de validación directamente en `Validator.rule()` | Delegar a `Validators.ruleName()` | Separación de concerns, testeabilidad |
| Usar `String.format()` en commonMain | Usar `internal fun format()` | `String.format` no existe en KMP |
| Olvidar la sobrecarga sin `message` | Siempre añadir ambas sobrecargas | API consistente |
| Añadir `@Throws` en métodos que no lanzan excepción | Solo en `validOrFail` y `compareOrFail` | Solo esos métodos lanzan la excepción |
| Poner tests de validación de fechas en `commonTest` | Ponerlos en `androidInstrumentedTest` | Las funciones `validateDate` etc. son `expect/actual` y requieren plataforma |

## Checklist de validación

- [ ] Función añadida en `Validators.kt` con KDoc.
- [ ] Mensaje añadido en `Messages.kt`, `MessagesEn.kt`, `MessagesEs.kt`.
- [ ] Métodos añadidos en `Validator.kt` y `Validator.Builder` con KDoc.
- [ ] Tests añadidos en `ValidatorsTest.kt` y `ValidatorTest.kt`.
- [ ] `./gradlew testDebugUnitTest --rerun-tasks` pasa sin errores.