package cn.iichen.diverseweather.ui.activity.main

import android.view.KeyEvent
import cn.iichen.diverseweather.R
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import cn.iichen.diverseweather.base.BaseActivity
import cn.iichen.diverseweather.databinding.ActivityMainBinding
import cn.iichen.diverseweather.ui.fragment.LifeFragment
import cn.iichen.diverseweather.ui.fragment.MoreFragment
import cn.iichen.diverseweather.ui.fragment.WarnFragment
import cn.iichen.diverseweather.ui.fragment.weather.WeatherFragment
import com.blankj.utilcode.util.ToastUtils
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.system.exitProcess

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: Adapter
    private lateinit var tabs: MutableList<String>
    private lateinit var fragmens: MutableList<Fragment>
    private lateinit var icons: MutableList<Int>


    override fun initView() {
        super.initView()

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        tabs = mutableListOf<String>(getString(R.string.weather),getString(R.string.life),getString(R.string.warn),getString(R.string.more))
        fragmens = mutableListOf(WeatherFragment(),LifeFragment(),WarnFragment(),MoreFragment())
        icons = mutableListOf(R.mipmap.weather,R.mipmap.life,R.mipmap.warn,R.mipmap.more)

        adapter = Adapter(this,fragmens)

        binding.apply {
            viewPager.adapter = adapter
//            viewPager.currentItem = 2
        }

        TabLayoutMediator(binding.tabLay,binding.viewPager
        ) { tab, position ->
            tab.text = tabs[position]
            tab.icon = getDrawable(icons[position])
        }.attach()


    }



    //退出时的时间
    private var mExitTime: Long = 0
    //对返回键进行监听
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            exit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    //退出方法
    private fun exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showShort(R.string.agin_exit)
            mExitTime = System.currentTimeMillis()
        } else {
            //用户退出处理
            finish()
            exitProcess(0)
        }
    }
}