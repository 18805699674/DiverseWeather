package cn.iichen.diverseweather.ui.fragment.weather

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.activityViewModels
import cn.iichen.diverseweather.base.BaseFragment
import cn.iichen.diverseweather.databinding.TabFragWeatherBinding
import cn.iichen.diverseweather.ext.Ext
import cn.iichen.diverseweather.ext.getHourTime
import cn.iichen.diverseweather.ui.activity.search.SearchActivity
import com.blankj.utilcode.util.*
import com.loc.fe
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.observeOn
import org.jetbrains.anko.startActivity

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.data.entity
 * @ClassName:      placeHodler
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/19 15:57
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/19 15:57
 * @UpdateRemark:   更新说明：底部导航Tab页  基本天气页
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

@FlowPreview
@ExperimentalCoroutinesApi
class WeatherFragment : BaseFragment() {
    private lateinit var binding : TabFragWeatherBinding
    private val mViewModel: WeatherViewModel by activityViewModels()
    lateinit var anima:ValueAnimator


    override fun initData(context: Context?) {
        val mmkv = MMKV.defaultMMKV()

        resources.getIdentifier("","", AppUtils.getAppPackageName())

        mViewModel.apply {
            // location后续从EventBus内获取 并修改后触发重新请求数据刷新页面
            location = "${mmkv.decodeString(Ext.LONGITUDE)},${mmkv.decodeString(Ext.LATITUDE)}"
            districk.set( mmkv.decodeString(Ext.DISTRICK))
            nowTime.set(TimeUtils.getNowDate().getHourTime())
            if(!NetworkUtils.isAvailable()) {
                ToastUtils.showShort("无网络链接!")
            }else{
                // 请求实时天气
                fetchWeatherNow().observe(this@WeatherFragment){}
                // 请求分钟降水  开发版无权限调用
                fetchMinuteLy()
                // 15天预报
                fetchWeather15D()
                // 24小数预报
                fetchWeatherHourly()
            }
        }
    }

    override fun initView(inflate: LayoutInflater): View {
        binding = TabFragWeatherBinding.inflate(inflate)

        anima = ValueAnimator.ofFloat(0F,360F)
        anima.addUpdateListener {
            val cur = it.animatedValue as Float
            binding.weatherRefresh.rotation = cur
        }
        anima.repeatCount = INFINITE
        anima.interpolator = AccelerateDecelerateInterpolator()
        anima.duration = 1200

        binding.apply {
            weatherViewModel = mViewModel

            // 刷新按钮 逻辑
            weatherRefresh.setOnClickListener {
                if(!NetworkUtils.isAvailable()) {
                    ToastUtils.showShort("无网络链接!")
                    return@setOnClickListener
                }
                if(anima.isRunning){ // 正在刷新
                    ToastUtils.showShort("正在刷新请稍后！")
                }else{
                    anima.start()
                    mViewModel.apply {
                        fetchWeatherNow().observe(this@WeatherFragment){
                            ToastUtils.showShort("刷新完成！")
                            anima.cancel()
                        }
                        nowTime.set(TimeUtils.getNowDate().getHourTime())
                    }
                }
            }
            // 今日详情跳转
            ClickUtils.applyGlobalDebouncing(arrayOf(weatherCard,weatherCard)){
                ToastUtils.showShort("跳转到今日天气详情")
            }

            // 添加城市、更改城市(1. 跳转到已添加的地区列表 2. 编辑和添加两个功能入口) 暂时直接跳转到添加
            weatherAdd.setOnClickListener {
                context?.startActivity<SearchActivity>()
            }

            lifecycleOwner = this@WeatherFragment
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        anima.cancel()
    }
}


