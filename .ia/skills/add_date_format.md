# Skill: Añadir Formato de Fecha

## Objetivo

Añadir un nuevo valor al enum `DateFormats` y sus implementaciones `pattern()` en Android e iOS, más tests.

## Prerrequisitos

- El nuevo formato debe tener un nombre en `UPPER_SNAKE_CASE`.
- Debe existir un patrón de fecha equivalente en ambos formatos (Java `SimpleDateFormat` y Unicode `NSDateFormatter`).

## Reglas

1. El valor se añade al enum `DateFormats` en `commonMain`.
2. Se añade una rama en la función `pattern()` en `androidMain`.
3. Se añade una rama en la función `pattern()` en `iosMain`.
4. Los patrones de Android usan sintaxis `SimpleDateFormat` (ej: `yyyy-MM-dd`).
5. Los patrones de iOS usan sintaxis Unicode (ej: `yyyy-MM-dd`, que es compatible con `NSDateFormatter`).
6. Los tests de formato de fecha van en `androidInstrumentedTest`, no en `commonTest`, porque `pattern()` es `expect/actual`.
7. Se añade un test en `DateFormatsPatternTest` verificando que el nuevo patrón retorna el string correcto.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `src/commonMain/.../dates/DateFormats.kt` | Modificar | Añadir valor al enum |
| `src/androidMain/.../dates/DateFormats.kt` | Modificar | Añadir rama en `pattern()` |
| `src/iosMain/.../dates/DateFormats.kt` | Modificar | Añadir rama en `pattern()` |
| `src/androidInstrumentedTest/.../dates/DateFormatsPatternTest.kt` | Modificar | Añadir test de patrón |

## Workflow

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Añadir valor al enum | En `DateFormats.kt` commonMain, añadir `NUEVO_FORMATO` después del último valor. |
| 2 | Añadir rama Android | En `androidMain/DateFormats.kt`, añadir `DateFormats.NUEVO_FORMATO -> "patternAndroid"`. |
| 3 | Añadir rama iOS | En `iosMain/DateFormats.kt`, añadir `DateFormats.NUEVO_FORMATO -> "patternIOS"`. |
| 4 | Añadir test de patrón | En `DateFormatsPatternTest`, añadir test verificando que `NUEVO_FORMATO.pattern()` retorna el string esperado. |
| 5 | Ejecutar tests | `./gradlew testDebugUnitTest --rerun-tasks` + `connectedDebugAndroidTest` en dispositivo. |

## Ejemplo real

Añadir formato `DD_MM_YYYY_HH_MM_SS` (fecha con segundos):

**1. DateFormats.kt (commonMain):**
```kotlin
enum class DateFormats {
    YYYY_MM_DD,
    DD_MM_YYYY,
    MM_DD_YYYY,
    YYYY_MM_DD_HH_MM,
    DD_MM_YYYY_HH_MM,
    HH_MM,
    HH_MM_SS,
    DD_MM_YYYY_HH_MM_SS  // nuevo
}
```

**2. androidMain/DateFormats.kt:**
```kotlin
DateFormats.DD_MM_YYYY_HH_MM_SS -> "dd/MM/yyyy HH:mm:ss"
```

**3. iosMain/DateFormats.kt:**
```kotlin
DateFormats.DD_MM_YYYY_HH_MM_SS -> "dd/MM/yyyy HH:mm:ss"
```

**4. DateFormatsPatternTest.kt:**
```kotlin
@Test
fun ddMmYyyyHhMmSsPattern() {
    assertEquals("dd/MM/yyyy HH:mm:ss", DateFormats.DD_MM_YYYY_HH_MM_SS.pattern())
}
```

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| Añadir tests de `pattern()` en `commonTest` | Añadirlos en `androidInstrumentedTest` | `pattern()` es `expect/actual` |
| Poner el patrón directamente en `Validator.date()` | Usar `DateFormats.pattern()` | El patrón es específico de plataforma |
| Olvidar añadir la rama en iOS | Siempre añadir en ambos `actual` | Sin implementación iOS, no compila para iOS |

## Checklist de validación

- [ ] Valor añadido al enum `DateFormats`.
- [ ] Rama añadida en `androidMain/DateFormats.kt`.
- [ ] Rama añadida en `iosMain/DateFormats.kt`.
- [ ] Test de patrón añadido en `DateFormatsPatternTest`.
- [ ] `./gradlew testDebugUnitTest --rerun-tasks` pasa sin errores.
- [ ] Verificar compilación iOS en Mac (si está disponible).