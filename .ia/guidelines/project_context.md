# Contexto del Proyecto

## Qué es

ValidatorKMP es una librería Kotlin Multiplatform que facilita la validación de Strings encadenando una serie de reglas. Diseñada para Android e iOS, sin dependencias externas.

## Stack tecnológico

| Componente | Versión / Detalle |
|---|---|
| Kotlin | 2.1.21 |
| Android Gradle Plugin | 8.10.0 |
| Gradle | 8.14 |
| Targets | androidTarget, iosX64, iosArm64, iosSimulatorArm64 |
| Dependencias externas | **Ninguna** en commonMain |
| Test framework | kotlin("test") |

## Paquete

`com.apamatesoft.validatorkmp` con subpaquetes:
- `.dates` — DateFormats, validateDate, validateMinAge, validateExpirationDate
- `.exceptions` — InvalidEvaluationException
- `.internal` — format (interno)
- `.messages` — Messages, MessagesEn, MessagesEs
- `.utils` — Alphabets, RegularExpression, Validators

## Qué es

- Librería de validación de Strings por encadenamiento de reglas.
- API declarativa vía `Validator.Builder()`.
- Validación por excepción: `validOrFail` / `compareOrFail`.
- Copia de validadores: `copy()`.
- Mensajes por defecto en inglés y español.
- Validadores booleanos puros en `Validators` object.
- Validación de fechas multiplataforma via `expect`/`actual`.

## Qué NO es

- No es un framework de validación de objetos (no hay reflexión POJO).
- No tiene anotaciones (`@Required`, `@Email`, etc.).
- No tiene `isValid`/`isMatch`/`onInvalidEvaluation` (se usa `validOrFail`/`compareOrFail`).
- No tiene `Validator.validOrFail(Object)` (no hay validador estático por reflexión).
- No soporta JVM standalone (solo Android + iOS).
- No tiene dependencias externas en commonMain.

## Notas de diseño

- `rangeValueMessage` usa `%2$.2f` para el valor máximo.
- `ALPHA_NUMERIC_ES` incluye caracteres españoles (ñ, acentos, üÜ).
- `üÜ` está incluido en todos los alfabetos españoles.

## Publicación

- Maven coordinate: `com.apamatesoft:ValidatorKMP:1.0.0`
- iOS: XCFramework `ValidatorKMP` distribuido como binary target
- Repositorio: `https://github.com/ApamateSoft/ValidatorKMP`