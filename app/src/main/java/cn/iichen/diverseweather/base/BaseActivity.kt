package cn.iichen.diverseweather.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.iichen.diverseweather.ui.activity.main.MainActivity

import cn.iichen.diverseweather.R

import com.billy.android.swipe.SmartSwipeBack
import com.gyf.immersionbar.ktx.immersionBar


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

abstract class BaseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(getLayRes())
        // 除了指定的MainActivity 全部滑动返回
        SmartSwipeBack.activitySlidingBack(
            application
        ) { activity -> //根据传入的activity，返回true代表需要侧滑返回；false表示不需要侧滑返回
            activity !is MainActivity
        }

        immersionBar {
            statusBarColor(R.color.colorPrimary)
            // 上面的文本颜色
            statusBarDarkFont(true)
            navigationBarColor(R.color.colorPrimary)
        }

        initView()
        initData()
    }

    open fun initData() {}

    open fun initView() {}

}














