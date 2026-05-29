# Skill: Publicar en Maven Central

## Objetivo

Publicar el proyecto ValidatorKMP en Maven Central Portal, incluyendo firma GPG de artefactos y verificación de namespace.

## Prerrequisitos

- Cuenta activa en https://central.sonatype.com
- Namespace `com.apamatesoft` verificado en Maven Central Portal (DNS TXT record)
- Token de usuario generado en Maven Central Portal (Profile → User Token)
- Clave GPG generada y subida a keyserver
- `~/.gradle/gradle.properties` con credenciales configuradas

## Reglas

1. Publicar siempre desde `main` con el repositorio limpio.
2. La versión se define en `build.gradle.kts` → `version = "X.Y.Z"`.
3. No publicar versiones duplicadas (Maven Central rechaza versiones existentes).
4. La verificación del namespace se hace UNA vez, no por release.
5. **No usar** `maven-publish` directamente contra el Portal — su REST API no acepta PUT como repositorio Maven.
6. Usar la tarea `publishToMavenCentralPortal` que empaqueta los artefactos en un ZIP y lo sube vía POST.

## Archivos involucrados

| Archivo | Acción | Descripción |
|---------|--------|-------------|
| `build.gradle.kts` | Modificar | `version`, POM info, tarea `publishToMavenCentralPortal` |
| `~/.gradle/gradle.properties` | Crear/Modificar | Credenciales Maven Central + GPG |
| `gradle.properties` | Modificar (opcional) | `kotlin.native.ignoreDisabledTargets` |

## Workflow

### Setup inicial (solo una vez)

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Crear cuenta en Maven Central | https://central.sonatype.com → Sign up |
| 2 | Generar User Token | Profile → User Token → Generate → copiar username/password |
| 3 | Verificar namespace | Publishing → Namespaces → Add `com.apamatesoft` → agregar TXT record en DNS de Squarespace (TXT `@` con el valor dado) → esperar propagación → Verify |
| 4 | Generar clave GPG | `gpg --full-generate-key` (RSA 4096, 2y expiry) |
| 5 | Subir clave pública | `gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>` |
| 6 | Exportar clave privada | `gpg --armor --export-secret-keys <KEY_ID>` |
| 7 | Convertir a una línea | Reemplazar saltos de línea por `\n` literal |
| 8 | Configurar `~/.gradle/gradle.properties` | Ver propiedades abajo |

### Propiedades en `~/.gradle/gradle.properties`

```properties
mavenCentralTokenUser=<user-token-username>
mavenCentralTokenPassword=<user-token-password>
signingKey=-----BEGIN PGP PRIVATE KEY BLOCK-----\n...\n-----END PGP PRIVATE KEY BLOCK-----
signingPassword=<passphrase-o-vacío>
```

> `signingKey` debe ser una sola línea con `\n` literal entre líneas.  
> `signingPassword` puede quedar vacío si la clave no tiene passphrase.

### Publicar release

| # | Acción | Detalle |
|---|--------|---------|
| 1 | Verificar versión | `build.gradle.kts` → `version` debe ser la deseada |
| 2 | Build + test | `./gradlew build` debe pasar sin errores |
| 3 | Publicar a local | `./gradlew publishToMavenLocal` — genera firma `.asc` en `~/.m2/repository` |
| 4 | Publicar a Portal | `./gradlew publishToMavenCentralPortal` — empaqueta y sube vía REST API |
| 5 | Verificar en portal | https://central.sonatype.com/publishing/deployments — estado `validating` → `published` |
| 6 | Tag en git (opcional) | `git tag v1.0.0 && git push origin v1.0.0` |

## Detalle técnico: Cómo funciona el Portal

El Maven Central Portal (nuevo, 2024+) **no acepta** el protocolo estándar de `maven-publish` (PUT en rutas de artefacto). En su lugar expone una REST API que recibe un ZIP con la estructura Maven completa:

```
POST https://central.sonatype.com/api/v1/publisher/upload
Authorization: Basic base64(tokenUser:tokenPassword)
Content-Type: multipart/form-data; boundary=...

--boundary
Content-Disposition: form-data; name="bundle"; filename="bundle.zip"
Content-Type: application/zip

<binary ZIP content>
--boundary--
```

Respuesta exitosa: `201 Created` con el `deploymentId` en el body.

La tarea `publishToMavenCentralPortal` en `build.gradle.kts` automatiza:
1. `publishToMavenLocal` (construye, firma, publica en `~/.m2/repository`)
2. Lee los artefactos de `~/.m2/repository/com/apamatesoft/`
3. Genera checksums `.md5` y `.sha1` para cada archivo (requisito del Portal)
4. Crea un ZIP con la estructura Maven completa
5. Lo sube al Portal vía HTTP multipart POST

## Ejemplo real

```bash
# Publicar v1.0.0
./gradlew publishToMavenCentralPortal
```

Output esperado:
```
> Task :signKotlinMultiplatformPublication
> Task :signAndroidReleasePublication
> Task :publishKotlinMultiplatformPublicationToMavenLocal
> Task :publishAndroidReleasePublicationToMavenLocal
> Task :publishToMavenCentralPortal
Deployment ID: 2f3b252e-c891-4d23-92f4-5e0ca42eda4d
Uploaded to Maven Central Portal. Check status at:
  https://central.sonatype.com/publishing/deployments
```

## Anti-patterns

| No hacer | Hacer en su lugar | Por qué |
|----------|-------------------|---------|
| Poner credenciales en `gradle.properties` del proyecto | Usar `~/.gradle/gradle.properties` | El proyecto está versionado en git |
| Publicar sin firmar | Configurar `signing` siempre | Maven Central rechaza artefactos sin firma |
| Usar staging legacy (`s01.oss.sonatype.org`) | Usar `publishToMavenCentralPortal` | El legacy da 402 para cuentas del nuevo Portal |
| Configurar `publishing.repositories` para el Portal | Usar la tarea custom con ZIP | El Portal no acepta PUT directo |
| Publicar desde `feature/*` | Publicar solo desde `main` | Control de calidad |

## Checklist de validación

- [ ] `~/.gradle/gradle.properties` tiene `mavenCentralTokenUser` y `mavenCentralTokenPassword`.
- [ ] `~/.gradle/gradle.properties` tiene `signingKey` y `signingPassword`.
- [ ] Namespace `com.apamatesoft` aparece como **Verified** en Maven Central Portal.
- [ ] `./gradlew build` pasa sin errores.
- [ ] `./gradlew publishToMavenLocal` genera artefactos con `.asc` (firmados).
- [ ] `./gradlew publishToMavenCentralPortal` completa con `Deployment ID` y status 201.
- [ ] El deployment aparece en https://central.sonatype.com/publishing/deployments como `published`.
