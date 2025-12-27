// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
   /* ext{

    }*/
}

plugins {
    id("com.android.application") version "8.9.1" apply false
    //1.9.10 versión de kotlin plugin compatible con ksp
    id("org.jetbrains.kotlin.android") version "2.1.21" apply false
    //2.48 versión de dagger hilt compatible con ksp
    id("com.google.dagger.hilt.android") version "2.57.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.21" apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
}