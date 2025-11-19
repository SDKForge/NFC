plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.build.logic.library.kmp)
    alias(libs.plugins.build.logic.library.android)
    alias(libs.plugins.build.logic.library.publishing)
}

kotlin {
    androidLibrary {
        namespace = "dev.sdkforge.nfc.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.sharedDomain)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
