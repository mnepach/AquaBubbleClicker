plugins {
    id("com.android.application") version "8.9.3" apply false
    id("com.android.library") version "8.9.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.multiplatform") version "2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.24" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}