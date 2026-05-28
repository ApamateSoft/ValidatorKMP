import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("com.android.library") version "8.10.0"
    id("maven-publish")
}

group = "com.apamatesoft"
version = "2.0.0-beta01"

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
            groupId = "com.apamatesoft"
            version = "2.0.0-beta01"
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
