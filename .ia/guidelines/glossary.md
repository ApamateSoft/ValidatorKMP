# Glosario

Términos del dominio de ValidatorKMP con su significado y referencia en el código.

| Término | Definición | Referencia en código |
|---------|-----------|---------------------|
| **Regla** | Par condicion + mensaje. Si la condición retorna `false`, la validación falla con ese mensaje. | `Rule`, `Validator.rule()` |
| **Validador** | Función booleana pura que recibe un String y retorna true/false. No lanza excepciones. | `Validators.required()`, `Validators.email()`, etc. |
| **Builder** | Patrón de construcción encadenable para crear `Validator` con reglas predefinidas. | `Validator.Builder` |
| **Evaluación** | El proceso de verificar todas las reglas de un `Validator` contra un String. | `Validator.validOrFail()`, `Validator.compareOrFail()` |
| **Clave (key)** | Identificador del campo siendo validado. Se incluye en `InvalidEvaluationException` para saber qué campo falló. | `InvalidEvaluationException.key` |
| **Mensaje** | Texto de error asociado a una regla. Soporta formato printf (`%d`, `%s`, `%1$.2f`). | `Messages`, `Rule.message` |
| **Alfabeto** | Conjunto de caracteres usado como condición en reglas de contenido. | `Alphabets.NUMBER`, `Alphabets.ALPHABET_ES`, etc. |
| **Formato de fecha** | Enum que representa un patrón de fecha multiplataforma. | `DateFormats` |
| **Patrón (pattern)** | Representación string del formato de fecha específico de plataforma. | `DateFormats.pattern()` |
| **Formato (format function)** | Función interna que reemplaza placeholders en mensajes. Equivalente a `String.format` pero KMP-compatible. | `internal fun format()` |
| **Validación fallida** | Cuando al menos una regla retorna `false`. Se lanza `InvalidEvaluationException`. | `InvalidEvaluationException` |
| **Copia de validador** | Replica de un `Validator` con las mismas reglas y mensaje de no-coincidencia. | `Validator.copy()` |
| **Comparación** | Validación de que dos Strings son iguales antes de aplicar las reglas. | `Validator.compareOrFail()` |
| **No-coincidencia** | Error cuando los Strings comparados no son iguales. Mensaje configurable. | `Validator.setNotMatchMessage()` |
| **Expect/Actual** | Patrón KMP para declarar una función en commonMain y proporcionar implementación en androidMain/iosMain. | `validateDate`, `validateMinAge`, `validateExpirationDate`, `DateFormats.pattern()` |
| **Mensaje por defecto** | Mensaje que se usa cuando no se provee un mensaje personalizado en una regla. Viene de `Messages`. | `Validator.required()` vs `Validator.required(message)` |