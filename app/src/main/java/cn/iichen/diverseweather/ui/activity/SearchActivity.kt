package cn.iichen.diverseweather.ui.activity

import android.Manifest
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.base.BaseActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import timber.log.Timber

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity
 * @ClassName:      SearchActivity
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/20 13:38
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/20 13:38
 * @UpdateRemark:   更新说明：城市搜索页面
 * @Version:        更新说明: 1.0
┏┓　　　┏┓
┏┛┻━━━┛┻┓
┃　　　　　　　┃
┃　　　━　　　┃
┃　┳┛　┗┳　┃
┃　　　　　　　┃
┃　　　┻　　　┃
┃　　　　　　　┃
┗━┓　　　┏━┛
┃　　　┃   神兽保佑
┃　　　┃   代码无BUG！
┃　　　┗━━━┓
┃　　　　　　　┣┓
┃　　　　　　　┏┛
┗┓┓┏━┳┓┏┛
┃┫┫　┃┫┫
┗┻┛　┗┻┛
 */


class SearchActivity : BaseActivity(){
    override fun getLayRes(): Int = R.layout.activity_search

    override fun initData() {
        super.initData()
        doRequestForegroundLocationPermission()
    }

    // 申请前台位置权限
    private fun doRequestForegroundLocationPermission() {
        // 检查定位权限并请求定位
        PermissionUtils.permission(Manifest.permission.ACCESS_COARSE_LOCATION)
            .rationale { activity, shouldRequest ->  MaterialDialog(this).show {
                message(text = "需要定位权限才能查看天气哦！")
                positiveButton(text = "去打开") {
                    shouldRequest.again(true)
                    LogUtils.d("去打开")
                }
                negativeButton(text = "取消") {
                    LogUtils.d("取消")
                    shouldRequest.again(false)
                }
            }}
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    showSnackbar(true, "LOCATION is granted")
                }

                override fun onDenied(permissionsDeniedForever: List<String>,
                                      permissionsDenied: List<String>) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        showSnackbar(false, "LOCATION is denied forever")
                    } else {
                        showSnackbar(false, "LOCATION is denied")
                    }
                }
            })
            .request()
    }

    // 申请后台台位置权限    (api 11 直接跳转到设置进行设置 api 10直接进行权限申请)
    private fun doRequestBackgroundLocationPermission() {
        // 检查定位权限并请求定位
        PermissionUtils.permission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            .rationale { activity, shouldRequest ->  MaterialDialog(this).show {
                message(text = "申请后台定位权限！")
                positiveButton(text = "去打开") {
                    shouldRequest.again(true)
                    Timber.d("去打开")
                }
                negativeButton(text = "取消") {
                    shouldRequest.again(false)
                    Timber.d("取消")
                }
            }}
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    showSnackbar(true, "LOCATION is granted")
                }

                override fun onDenied(permissionsDeniedForever: List<String>,
                                      permissionsDenied: List<String>) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        showSnackbar(false, "LOCATION is denied forever")
                    } else {
                        showSnackbar(false, "LOCATION is denied")
                    }
                }
            })
            .request()
    }

    fun request(view: View) {
        doRequestForegroundLocationPermission()
    }
}








