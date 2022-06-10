package dependencies

import dependencies.android.androidX
import dependencies.android.vmLifeCycle
import dependencies.kotlin.coroutine
import dependencies.retrofit_okhttp.gson
import dependencies.retrofit_okhttp.okHttp
import dependencies.retrofit_okhttp.retrofit
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appLibraries() {
    androidCore()
    materialDesign()
    testUnit()
    androidX()
    vmLifeCycle()
    coroutine()
    gson()
    okHttp()
    retrofit()
    glide()
    NavGraph()
//    koinKotlin("3.2.0-beta-1")
    gander()
    dagger()
    androidPaging()
    exoPlayer()
    DaggerHilt()
}