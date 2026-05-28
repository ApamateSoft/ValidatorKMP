# Skill: Añadir Alfabeto o Expresión Regular

## Objetivo

Añadir una nueva constante al object `Alphabets` o al object `RegularExpression`, más tests.

## Prerrequisitos

- El nombre debe estar en `UPPER_SNAKE_CASE`.
- Para alfabetos: el valor es un String con los caracteres permitidos.
- Para regex: el valor es un String con un patrón regex válido.

## Reglas

1. Las constantes se declaran como `const val NOMBRE: String`.
2. Los nombres de alfabetos son descriptivos del conjunto de caracteres (ej: `ALPHA_LOWERCASE_ES`).
3. Los nombres de regex corresponden al validador que la usa (ej: `EMAIL`, `IPV6`).
4. Todo nuevo alfabeto o regex debe tener al menos un test de verificación.
5. Los tests van en `commonTest` en `AlphabetsTest` o `RegularExpressionTest`.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `src/commonMain/.../utils/Alphabets.kt` | Modificar | Añadir constante con KDoc |
| `src/commonTest/.../utils/AlphabetsTest.kt` | Modificar | Añadir test |

O:

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `src/commonMain/.../utils/RegularExpression.kt` | Modificar | Añadir constante con KDoc |
| `src/commonTest/.../utils/RegularExpressionTest.kt` | Modificar | Añadir test |

## Workflow

### Añadir Alfabeto

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Añadir constante en `Alphabets` | `const val NOMBRE: String = "valor"` con KDoc |
| 2 | Añadir test en `AlphabetsTest` | Verificar que la constante equals el valor esperado |
| 3 | Ejecutar tests | `./gradlew testDebugUnitTest --rerun-tasks` |

### Añadir Expresión Regular

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Añadir constante en `RegularExpression` | `const val NOMBRE: String = "^patron$"` con KDoc. **Ojo**: en Kotlin, `\` debe escaparse como `\\` en strings. |
| 2 | Añadir test en `RegularExpressionTest` | Verificar que la constante equals el valor esperado |
| 3 | Si la regex será usada por un nuevo validador, crear también un test en `ValidatorsTest` | Verificar que `Validators.nombreFuncion()` funciona correctamente |
| 4 | Ejecutar tests | `./gradlew testDebugUnitTest --rerun-tasks` |

## Ejemplo real

Añadir un alfabeto para números hexadecimales simplificado:

**1. Alphabets.kt:**
```kotlin
/** Hexadecimal characters (uppercase only): 0123456789ABCDEF */
const val HEX_UPPER: String = "0123456789ABCDEF"
```

**2. AlphabetsTest.kt:**
```kotlin
@Test
fun hexUpperCaseValue() {
    assertEquals("0123456789ABCDEF", Alphabets.HEX_UPPER)
}
```

Añadir una regex para códigos postales estadounidenses:

**1. RegularExpression.kt:**
```kotlin
/** Matches US ZIP code (5 digits or 5+4): 12345 or 12345-6789 */
const val US_ZIP: String = "^\\d{5}(-\\d{4})?$"
```

**2. RegularExpressionTest.kt:**
```kotlin
@Test
fun usZipValue() {
    assertEquals("^\\d{5}(-\\d{4})?$", RegularExpression.US_ZIP)
}
```

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| Usar `val` en vez de `const val` | Usar `const val` | Garantiza compilación en tiempo de compilación, sin inicialización lazy |
| Olvidar escapar `\` en regex | Escribir `\\d` en lugar de `\d` | Kotlin strings requieren doble escape para backslash |
| Añadir un alfabeto sin tests | Siempre añadir test de valor | Evitar regressions inadvertidas |
| Añadir regex que ya existe | Verificar `RegularExpression` primero | Duplicación innecesaria |

## Checklist de validación

- [ ] Constante añadida con `const val` y KDoc.
- [ ] Test añadido en el archivo de tests correspondiente.
- [ ] `./gradlew testDebugUnitTest --rerun-tasks` pasa sin errores.