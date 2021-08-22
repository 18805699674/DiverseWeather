package cn.iichen.plugin

object Versions {
    const val retrofit = "2.9.0"
    const val okhttpLogging = "4.9.0"
    const val appcompat = "1.3.1"
    const val coreKtx = "1.6.0"
    const val constraintlayout = "2.1.0"
    const val paging = "3.0.1"
    const val timber = "4.7.1"
    const val kotlin = "1.4.20"
    const val kotlinCoroutinesCore = "1.3.7"
    const val kotlinCoroutinesAndrid = "1.3.6"
    const val koin = "2.1.5"
    const val work = "2.2.0"
    const val room = "2.3.0"
    const val cardview = "1.0.0"
    const val recyclerview = "1.0.0"
    const val fragment = "1.3.6"
    const val anko = "0.10.8"
    const val swiperefreshlayout = "1.1.0"
    const val junit = "4.13.1"
    const val junitExt = "1.1.2"
    const val espressoCore = "3.3.0"
    const val runtime = "1.1.0"
    const val hit = "2.38.1"
    const val hitViewModule = "1.0.0-alpha01"
    const val appStartup = "1.0.0"
    const val material = "1.4.0"
    const val immersionBar = "3.0.0"
    const val codeUtils = "1.30.6"
    const val dialog = "3.3.0"
    const val mmkv = "1.2.10"
    const val smartSwipe = "1.0.8"
    const val permission = "4.9.1"
    const val loading = "1.4.0"
    const val recycle = "3.0.4"
}

object BaseRecycle{
   const val core = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.recycle}"
}

object Loading{
    const val core = "com.github.ybq:Android-SpinKit:${Versions.loading}"
}

object SmartSwipe{
    const val core =  "com.billy.android:smart-swipe:${Versions.smartSwipe}"
    //android x环境下兼容NestedScrolling
//    const val extend =x 'com.billy.android:smart-swipe-x:latestVersion
}

object Mmkv{
    const val core = "com.tencent:mmkv-static:${Versions.mmkv}"
}

object Dialog{
    const val core =  "com.afollestad.material-dialogs:core:${Versions.dialog}"
//    const val input =  "com.afollestad.material-dialogs:input:${Versions.dialog}"
//    const val file =  "com.afollestad.material-dialogs:files:${Versions.dialog}"
//    const val color =  "com.afollestad.material-dialogs:color:${Versions.dialog}"
//    const val datatime =  "com.afollestad.material-dialogs:datetime:${Versions.dialog}"
    const val bottomSheets =  "com.afollestad.material-dialogs:bottomsheets:${Versions.dialog}"
    const val lifecycle =  "com.afollestad.material-dialogs:lifecycle:${Versions.dialog}"
}

object Permission{
    const val core = "com.github.permissions-dispatcher:permissionsdispatcher:${Versions.permission}"
    const val compiler = "com.github.permissions-dispatcher:permissionsdispatcher-processor:${Versions.permission}"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"

    const val workRuntime = "androidx.work:work-runtime:${Versions.work}"
    const val workTesting = "androidx.work:work-testing:${Versions.work}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val swiperefreshlayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
}

object Android {
    const val meteria = "com.google.android.material:material:${Versions.material}"
}

object Hilt {
    const val daggerRuntime = "com.google.dagger:hilt-android:${Versions.hit}"
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hit}"
    const val viewModule = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hitViewModule}"
    const val compiler = "androidx.hilt:hilt-compiler:${Versions.hitViewModule}"
}

object Coil {
    const val runtime = "io.coil-kt:coil:${Versions.runtime}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
    const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val testing = "androidx.room:room-testing:${Versions.room}"
}

object Fragment {
    const val runtime = "androidx.fragment:fragment:${Versions.fragment}"
    const val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
}

object Kt {
    const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinesCore}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesAndrid}"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Koin {
    const val core = "org.koin:koin-core:${Versions.koin}"
    const val androidCore = "org.koin:koin-android:${Versions.koin}"
    const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val androidScope = "org.koin:koin-android-scope:$${Versions.koin}"
}

object Anko {
    const val common = "org.jetbrains.anko:anko-commons:${Versions.anko}"
    const val sqlite = "org.jetbrains.anko:anko-sqlite:${Versions.anko}"
    const val coroutines = "org.jetbrains.anko:anko-coroutines:${Versions.anko}"
    const val design = "org.jetbrains.anko:anko-design:${Versions.anko}" // For SnackBars
}

object Retrofit {
    const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}"
}

object Depend {
    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestJunit = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

object ImmersionBar{
    // 基础依赖包，必须要依赖
    const val core = "com.gyf.immersionbar:immersionbar:${Versions.immersionBar}"
    // fragment快速实现（可选）
    const val component = "com.gyf.immersionbar:immersionbar-components:${Versions.immersionBar}"
    // kotlin扩展（可选）
    const val ktx = "com.gyf.immersionbar:immersionbar-ktx:${Versions.immersionBar}"
}

object CodeUtils{
    const val core = "com.blankj:utilcodex:${Versions.codeUtils}"
}