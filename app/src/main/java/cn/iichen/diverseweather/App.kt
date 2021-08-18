package cn.iichen.diverseweather

import android.app.Application
import android.content.Context
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    fun init(context: Context) {
        this.mContext = context
        if (!BuildConfig.DEBUG) {
            Timber.plant(CrashReportingTree())
            return
        }
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
        )
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        Timber.plant(Timber.DebugTree())
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//            Timber.tag("release---------------------");
            Timber.d("----------$message-----------");
        }
    }
}