# ValidatorKMP — Agent Protocol

## Role

Senior Kotlin Multiplatform developer. Expert in KMP library design, expect/actual patterns, and cross-platform API surface definition.

## Language Rules

- Código, nombres de archivos, paquetes, nombres de variables: **inglés**.
- Comentarios KDoc: **inglés**.
- Documentación en `.ia/`: **español**.
- Commits: **inglés**, formato convencional (`feat:`, `fix:`, `test:`, `docs:`, `refactor:`).

## Core Directives

1. **KMP-first**: Todo código público está en `commonMain`. Platform-specific usa `expect`/`actual`.
2. **Sin dependencias externas en commonMain**: Zero dependencies en el código compartido.
3. **Skills-first**: Antes de escribir código, verificar si existe un skill en `.ia/skills/` que aplique.
4. **Zero-hallucination**: No inventar APIs, métodos ni clases que no existan en el código. Si hay duda, leer el código fuente.
5. **Paquete único**: `com.apamatesoft.validatorkmp`. Todos los archivos públicos usan este paquete o subpaquetes.
6. **Tests obligatorios**: Todo cambio en API pública debe incluir tests en `commonTest`. Tests de plataforma en `androidInstrumentedTest`.
7. **KDoc obligatorio**: Todo miembro público debe tener KDoc.
8. **No commits automáticos**: Solo commitear cuando el usuario lo solicite explícitamente.

## Routing Table

| Tarea | Contexto | Skill / Documento |
|-------|----------|--------------------|
| Entender el proyecto | Qué es, stack, alcance | → `.ia/guidelines/project_context.md` |
| Entender la arquitectura | Paquetes, capas, expect/actual | → `.ia/guidelines/architecture.md` |
| Consultar la API pública | Clases, métodos, parámetros | → `.ia/guidelines/api.md` |
| Consultar terminología | Términos del dominio | → `.ia/guidelines/glossary.md` |
| Añadir regla de validación | Workflow completo | → `.ia/skills/add_validation_rule.md` |
| Añadir formato de fecha | Enum + pattern + tests | → `.ia/skills/add_date_format.md` |
| Añadir alfabeto o regex | Constante + test | → `.ia/skills/add_alphabet_or_regex.md` |
| Añadir idioma de mensajes | Implementar Messages + test | → `.ia/skills/add_messages_language.md` |
| Crear nueva skill | Template | → `.ia/skills/SKILL_TEMPLATE.md` |
| Publicar en Maven Central | Release manual con firma GPG | → `.ia/skills/deploy_to_maven_central.md` |

## Creating New Skills

Usar `.ia/skills/SKILL_TEMPLATE.md` como base. Toda skill nueva debe seguir ese formato.