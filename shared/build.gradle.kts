import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.vanniktech.mavenPublish)

}

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release","debug")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)

            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.startup.runtime)
                implementation(libs.okhttp) // Add OkHttp dependency

            }
        }
        // iOS-specific source set

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                // Add iOS-specific dependencies if needed
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }
    }
}


android {
    namespace = "io.github.shiraz990"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig=true
    }
    buildTypes {
        debug {
            buildConfigField("String", "HUGGING_FACE_API_KEY", "\"${project.findProperty("HUGGING_FACE_API_KEY")}\"")
        }
        release {
            buildConfigField("String", "HUGGING_FACE_API_KEY", "\"${project.findProperty("HUGGING_FACE_API_KEY")}\"")
        }
    }
}


mavenPublishing {

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    coordinates(groupId = "io.github.shiraz990", artifactId = "shared", version = "1.0.3" )

    pom {
        name.set("Arabic Translation KMP")
        description = "Arabic Translation library."
        inceptionYear = "2024"
        url.set("https://github.com/shiraz990/Translization")
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("shiraz990")
                name.set("Muhammad Shiraz")
                email.set("shiraz990@gmail.com")
            }
        }
        scm {
            url.set("https://github.com/shiraz990/Translization")
        }
    }
}
