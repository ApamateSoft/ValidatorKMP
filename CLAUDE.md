# ValidatorKMP — Claude Code Context

> Este archivo guía a Claude Code sobre la estructura de documentación del proyecto.

## Estructura de documentación

```
AGENTS.md               # Protocolo maestro (rol, directivas, routing table) — raíz del proyecto
CLAUDE.md                # Contexto para Claude Code — raíz del proyecto
.ia/
├── guidelines/             # El QUÉ del proyecto
│   ├── project_context.md  # Propósito, stack, alcance, qué es/no es
│   ├── architecture.md     # Paquetes, capas, expect/actual, Builder
│   ├── api.md              # API pública completa
│   └── glossary.md         # Términos del dominio
└── skills/                 # El CÓMO del proyecto
    ├── SKILL_TEMPLATE.md    # Template para crear nuevas skills
    ├── add_validation_rule.md
    ├── add_date_format.md
    ├── add_alphabet_or_regex.md
    └── add_messages_language.md
```

## Regla principal

Antes de generar código, verificar si existe un skill en `.ia/skills/` que aplique al tipo de cambio solicitado. Si existe, seguir el workflow del skill.

## Routing rápido

- **¿Qué es este proyecto?** → `.ia/guidelines/project_context.md`
- **¿Cómo está organizado el código?** → `.ia/guidelines/architecture.md`
- **¿Qué APIs hay?** → `.ia/guidelines/api.md`
- **¿Qué significa X término?** → `.ia/guidelines/glossary.md`
- **¿Cómo añado una regla de validación?** → `.ia/skills/add_validation_rule.md`
- **¿Cómo añado un formato de fecha?** → `.ia/skills/add_date_format.md`
- **¿Cómo añado un alfabeto o regex?** → `.ia/skills/add_alphabet_or_regex.md`
- **¿Cómo añado un idioma de mensajes?** → `.ia/skills/add_messages_language.md`