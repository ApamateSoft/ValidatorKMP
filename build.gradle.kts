import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URI
import java.security.MessageDigest
import java.util.Base64
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.library") version "8.10.0"
    id("maven-publish")
    id("signing")
}

group = "com.apamatesoft"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    val xcf = XCFramework("ValidatorKMP")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "ValidatorKMP"
            xcf.add(this)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.apamatesoft.validatorkmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            pom {
                name.set("ValidatorKMP")
                description.set("Facilitates the validation of Strings by chaining a series of rules")
                url.set("https://github.com/ApamateSoft/ValidatorKMP")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        name.set("Jesús Alberto Mendoza Sánchez")
                        email.set("jealmesa@gmail.com")
                        organization.set("ApamateSoft")
                        organizationUrl.set("https://apamatesoft.com/")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ApamateSoft/ValidatorKMP")
                    developerConnection.set("scm:git:ssh://github.com/ApamateSoft/ValidatorKMP")
                    url.set("https://github.com/ApamateSoft/ValidatorKMP")
                }
            }
        }
    }

}

val signingKey: String? by project
val signingPassword: String? by project

if (signingKey != null && signingPassword != null) {
    signing {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}

val publishToMavenCentralPortal by tasks.registering {
    dependsOn("publishToMavenLocal")
    group = "publishing"
    description = "Publish all artifacts to Maven Central Portal via REST API"

    doLast {
        val groupPath = project.group.toString().replace('.', '/')
        val ver = project.version.toString()
        val localRepo = file("${System.getProperty("user.home")}/.m2/repository/$groupPath")
        val bundleDir = layout.buildDirectory.dir("maven-bundle").get().asFile
        val bundleFile = layout.buildDirectory.file("maven-bundle.zip").get().asFile

        bundleDir.deleteRecursively()
        bundleDir.mkdirs()
        bundleFile.delete()

        localRepo.listFiles()?.forEach { artifact ->
            val versionDir = artifact.resolve(ver)
            if (versionDir.isDirectory) {
                val target = bundleDir.resolve("$groupPath/${artifact.name}/$ver")
                versionDir.copyRecursively(target, overwrite = true)
            }
        }

        bundleDir.walkTopDown().forEach { if (it.name.contains("maven-metadata-local")) it.delete() }

        // Generate MD5 and SHA1 checksums for every file (required by Maven Central Portal)
        bundleDir.walkTopDown().forEach { file ->
            if (file.isFile && !file.name.endsWith(".md5") && !file.name.endsWith(".sha1")) {
                val data = file.readBytes()
                val md5 = MessageDigest.getInstance("MD5").digest(data).joinToString("") { "%02x".format(it) }
                val sha1 = MessageDigest.getInstance("SHA-1").digest(data).joinToString("") { "%02x".format(it) }
                file.resolveSibling("${file.name}.md5").writeText(md5)
                file.resolveSibling("${file.name}.sha1").writeText(sha1)
            }
        }

        ZipOutputStream(bundleFile.outputStream()).use { zos ->
            bundleDir.walkTopDown().forEach { file ->
                if (file.isFile) {
                    val entry = ZipEntry(file.relativeTo(bundleDir).path.replace("\\", "/"))
                    zos.putNextEntry(entry)
                    file.inputStream().use { it.copyTo(zos) }
                    zos.closeEntry()
                }
            }
        }

        val url = URI("https://central.sonatype.com/api/v1/publisher/upload").toURL()
        val tokenUser = providers.gradleProperty("mavenCentralTokenUser").get()
        val tokenPass = providers.gradleProperty("mavenCentralTokenPassword").get()
        val auth = Base64.getEncoder().encodeToString("$tokenUser:$tokenPass".toByteArray())
        val boundary = "----${System.currentTimeMillis()}"

        val part1 = "--$boundary\r\nContent-Disposition: form-data; name=\"bundle\"; filename=\"bundle.zip\"\r\nContent-Type: application/zip\r\n\r\n".toByteArray()
        val part2 = "\r\n--$boundary--\r\n".toByteArray()
        val body = ByteArrayOutputStream()
        body.write(part1)
        bundleFile.inputStream().use { it.copyTo(body) }
        body.write(part2)

        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.setRequestProperty("Authorization", "Basic $auth")
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
        conn.doOutput = true
        conn.outputStream.use { body.writeTo(it) }

        val status = conn.responseCode
        val response = if (status in 200..299) conn.inputStream.reader().readText()
            else conn.errorStream?.reader()?.readText() ?: ""

        if (status == 201) {
            logger.lifecycle("Deployment ID: $response")
            logger.lifecycle("Uploaded to Maven Central Portal. Check status at:")
            logger.lifecycle("  https://central.sonatype.com/publishing/deployments")
        } else {
            throw GradleException("Upload failed ($status): $response")
        }
    }
}
