# Skill Template

## Propósito

Template para crear skills de forma consistente. Toda skill nueva en `.ia/skills/` debe seguir esta estructura.

## Estructura obligatoria

```markdown
# [Nombre de la skill]

## Objetivo

1-2 frases describiendo qué produce esta skill.

## Prerrequisitos

- Lista de condiciones que deben cumplirse antes de ejecutar la skill.
- Incluir archivos que deben existir, conocimientos necesarios, etc.

## Reglas

- Lista numerada de reglas obligatorias.
- Incluir convenciones de命名, ubicación de archivos, patrones a seguir.
- Incluir anti-patterns explícitos.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| ruta/al/archivo | Crear / Modificar | Qué cambiar |

## Workflow

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Paso 1 | Descripción |
| 2 | Paso 2 | Descripción |
| ... | ... | ... |

## Ejemplo real

Breve ejemplo con código del proyecto mostrando el resultado de aplicar la skill.

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| ... | ... | ... |

## Checklist de validación

- [ ] Archivo creado en la ubicación correcta.
- [ ] Paquete correcto.
- [ ] KDoc en todo miembro público.
- [ ] Test creado en commonTest.
- [ ] `./gradlew testDebugUnitTest` pasa sin errores.
```

## Notas

- El nombre del archivo debe ser `snake_case.md`.
- El objetivo debe ser atómico: una skill = un tipo de cambio.
- Los ejemplos reales deben venir del código actual del proyecto, no inventados.
- Las reglas deben ser verificables objetivamente (sí/no).