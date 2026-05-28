# Skill: Añadir Idioma de Mensajes

## Objetivo

Añadir una nueva implementación de `Messages` para un nuevo idioma, más tests.

## Prerrequisitos

- El idioma debe tener traducciones para las 34 propiedades de `Messages`.
- El nombre de la clase sigue el patrón `Messages{Idioma}` (ej: `MessagesFr`, `MessagesPt`, `MessagesDe`).

## Reglas

1. La clase implementa la interface `Messages` y está en el paquete `com.apamatesoft.validatorkmp.messages`.
2. Todas las 34 propiedades deben ser `override val`.
3. Los mensajes pueden contener placeholders de formato: `%d` (entero), `%s` (string), `%1$.2f` (decimal con 2 decimales), `%2$.2f` (segundo decimal).
4. No usar `String.format()` en los mensajes — los placeholders se procesan con la función `internal fun format()`.
5. Los tests verifican que todas las propiedades no son null ni vacías.
6. El archivo se coloca en `src/commonMain/kotlin/com/apamatesoft/validatorkmp/messages/`.
7. Los tests se colocan en `src/commonTest/kotlin/com/apamatesoft/validatorkmp/messages/`.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `src/commonMain/.../messages/MessagesXX.kt` | Crear | Nueva clase implementando Messages |
| `src/commonTest/.../messages/MessagesXXTest.kt` | Crear | Tests de la nueva clase |

## Workflow

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Crear clase `MessagesXX` | Implementar las 34 propiedades de `Messages` con traducciones al nuevo idioma. |
| 2 | Verificar formato de placeholders | Asegurar que `%d`, `%s`, `%1$.2f`, `%2$.2f` están en las posiciones correctas y coinciden con los parámetros que recibe cada regla. |
| 3 | Crear test `MessagesXXTest` | Verificar que todas las 34 propiedades no son null ni vacías. Opcionalmente verificar que los placeholders están presentes. |
| 4 | Ejecutar tests | `./gradlew testDebugUnitTest --rerun-tasks` debe pasar sin errores. |
| 5 | Actualizar README | Añadir el nuevo idioma en la sección de mensajes por defecto del README principal y la traducción en español. |

## Ejemplo real

Añadir mensajes en portugués (`MessagesPt`):

**1. MessagesPt.kt:**
```kotlin
package com.apamatesoft.validatorkmp.messages

/** Portuguese messages for all predefined validation rules. */
class MessagesPt : Messages {
    override val compareMessage: String = "Não coincidem"
    override val dateMessage: String = "A data não coincide com o formato %s"
    override val emailMessage: String = "E-mail inválido"
    // ... las 34 propiedades
    override val wwwLinkMessage: String = "Link www inválido"
}
```

**2. MessagesPtTest.kt:**
```kotlin
package com.apamatesoft.validatorkmp.messages

import kotlin.test.Test
import kotlin.test.assertTrue

class MessagesPtTest {
    private val messages = MessagesPt()

    @Test
    fun allPropertiesAreNonNullAndNonEmpty() {
        val properties = listOf(
            messages.compareMessage,
            messages.dateMessage,
            // ... las 34 propiedades
            messages.wwwLinkMessage
        )
        properties.forEach { prop ->
            assertTrue(prop.isNotEmpty(), "Expected non-empty message")
        }
    }
}
```

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| Cambiar el orden de los placeholders (`%1$.2f` antes de `%2$.2f`) | Mantener el mismo orden que MessagesEn | El código los pasa en orden fijo |
| Usar `String.format` en los mensajes | Usar solo placeholders `%d`, `%s`, `%1$.2f` | `String.format` no existe en KMP |
| Olvidar alguna de las 34 propiedades | Verificar contra la interface `Messages` | No compila si falta alguna |
| Poner tests en `androidInstrumentedTest` | Ponerlos en `commonTest` | Los mensajes son commonMain, no necesitan runtime Android |

## Placeholder Reference

Las reglas que usan formato en sus mensajes:

| Regla | Placeholders | Ejemplo de mensaje |
|-------|-------------|-------------------|
| `length` | `%d` | `"It requires %d characters"` |
| `minLength` | `%d` | `"%d or more characters are required"` |
| `maxLength` | `%d` | `"%d or less characters required"` |
| `rangeLength` | `%d`, `%d` | `"between %d to %d characters"` |
| `regExp` | `%s` | `"does not match %s"` |
| `maxValue` | `%1$.2f` | `"cannot be greater than %1$.2f"` |
| `minValue` | `%1$.2f` | `"cannot be less than %1$.2f"` |
| `rangeValue` | `%1$.2f`, `%2$.2f` | `"between %1$.2f and %2$.2f"` |
| `numberPattern` | `%s` | `"Does not match pattern %s"` |
| `date` | `%s` | `"does not match the format %s"` |
| `minAge` | `%d` | `"at least %d years old"` |
| `mustContainOne` | `%s` | `"at least one of: %s"` |
| `mustContainMin` | `%d`, `%s` | `"at least %d of: %s"` |
| `shouldOnlyContain` | `%s` | `"only: %s"` |
| `notContain` | `%s` | `"not admitted: %s"` |

## Checklist de validación

- [ ] Clase `MessagesXX` creada con las 34 propiedades.
- [ ] Todos los placeholders coinciden con los de `MessagesEn` (mismo tipo, mismo orden).
- [ ] Test `MessagesXXTest` creado verificando que ninguna propiedad es null/vacía.
- [ ] `./gradlew testDebugUnitTest --rerun-tasks` pasa sin errores.
- [ ] README actualizado con el nuevo idioma.