package cn.iichen.diverseweather.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.ToastUtils

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.base
 * @ClassName:      BaseActivity
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/19 16:15
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/19 16:15
 * @UpdateRemark:   更新说明：基类Activity，可以在此进行二次封装
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


abstract class BaseActivity : AppCompatActivity(),LifecycleObserver{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayRes())
        initView()
        initData()
    }

    open fun initData() {}

    open fun initView() {}

    abstract fun getLayRes(): Int

    open fun showSnackbar(isSuccess: Boolean, msg: String) {
//        SnackbarUtils.with(mContentView)
//            .setDuration(SnackbarUtils.LENGTH_LONG)
//            .setMessage(msg)
//            .apply {
//                if (isSuccess) {
//                    showSuccess()
//                } else {
//                    showError()
//                }
//            }
        ToastUtils.showShort(msg)
    }
}














