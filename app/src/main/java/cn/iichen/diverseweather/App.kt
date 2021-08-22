package cn.iichen.diverseweather

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.qweather.sdk.view.HeConfig
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 日志、运行配置
        init(this)
        // 其他库
        initPacket()
    }

    private fun initPacket() {
        HeConfig.init("HE2108191358591295", "4c85ffe4575c40d38fd3f36aef6f6ca8")
        //切换至开发版服务
        HeConfig.switchToDevService()

        MMKV.initialize(this)
    }

    private fun init(context: Context) {
        if (!BuildConfig.DEBUG) {
            Timber.plant(CrashReportingTree())
            return
        }
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
        )
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
        Timber.plant(Timber.DebugTree())
        return Unit
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//            Timber.tag("release---------------------");
            Timber.d("----------$message-----------");
        }
    }
}
/*
    MMKV使用：
        1. 存储与读取
            val kv: MMKV = MMKV.defaultMMKV()     使用方法
            kv.encode("bool", true)
            kv.decodeBool("bool")
        2. 删除和查询
            MMKV kv = MMKV.defaultMMKV();
            kv.removeValueForKey("bool");
            kv.containsKey("bool")
        3. 隔离分开
            MMKV kv = MMKV.mmkvWithID("MyID");
            kv.encode("bool", true);
        4. 跨进程
            MMKV kv = MMKV.mmkvWithID("InterProcessKV", MMKV.MULTI_PROCESS_MODE);
            kv.encode("bool", true);

    侧滑库：
        https://qibilly.com/SmartSwipe-tutorial/pages/consumers/SpaceConsumer.html

    MaterialDialog： https://github.com/afollestad/material-dialogs/blob/main/documentation/CORE.md
        # 自动销毁
            MaterialDialog(this).show {
              lifecycleOwner(owner)
            }
        # BottomSheet
            MaterialDialog ( this , BottomSheet (WRAP_CONTENT)).show {
                // 设置高度
                setPeekHeight(res =  R .dimen.my_default_peek_height)
               //文字，内部转换为 dp 所以 16dp
              cornerRadius( 16f )
               //鼓励使用dimen，因为从一个地方更改所有实例更容易
              cornerRadius(res =  R . dimen.my_corner_radius)
            }

    用户引导：
        https://github.com/binIoter/GuideView
        https://github.com/hongyangAndroid/Highlight

    骨架屏：
        https://github.com/ethanhua/Skeleton

    高德定位文档：
        https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation

    权限申请框架 注解方法不能为 private
        https://github.com/permissions-dispatcher/PermissionsDispatcher

    加载框：    https://github.com/ybq/Android-SpinKit
        <com.github.ybq.android.spinkit.SpinKitView
            xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/spin_kit"
            style="@style/SpinKitView.Large.Circle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            app:SpinKit_Color="@color/colorAccent" />

         好看一点：Wave(这里用)、CubeGrid

    RotatingPlane、DoubleBounce、Wave、WanderingCubes、Pulse、ChasingDots、ThreeBounce、Circle、CubeGrid、FadingCircle、FoldingCube、RotatingCircle


    BaseQuickAdapter:   https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/1-BaseQuickAdapter.md


    天气Api修改为 WebApi ：   https://dev.qweather.com/docs/api/
 */


/**
 * 1. 所有使用 Hilt 的 App 必须包含 一个使用 @HiltAndroidApp 注解的 Application
 * 2. @HiltAndroidApp 将会触发 Hilt 代码的生成，包括用作应用程序依赖项容器的基类
 * 3. 生成的 Hilt 组件依附于 Application 的生命周期，它也是 App 的父组件，提供其他组件访问的依赖
 * 4. 在 Application 中设置好 @HiltAndroidApp 之后，就可以使用 Hilt 提供的组件了，
 *    Hilt 提供的 @AndroidEntryPoint 注解用于提供 Android 类的依赖（Activity、Fragment、View、Service、BroadcastReceiver）等等
 *    Application 使用 @HiltAndroidApp 注解
 */

/*
    1. 依赖注入Android类
        Application（其中通过使用 @HiltAndroidApp）
        Activity    （ComponentActivity ）
        Fragment    （androidx.Fragment ）
        View
        Service
        BroadcastReceiver

    2. 使用 @inject注入 --- 前提需要 @Module @Provides 提供实例load
        class Truck @Inject constructor() {    // 告知 Hilt如何构造 Truck类
            fun deliver() {
                println("Truck is delivering cargo.")
            }
        }


 */