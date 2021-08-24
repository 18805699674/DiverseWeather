package cn.iichen.diverseweather.ui.fragment.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import cn.iichen.diverseweather.base.BaseFragment
import cn.iichen.diverseweather.databinding.TabFragWeatherBinding
import cn.iichen.diverseweather.ext.Ext
import com.blankj.utilcode.util.LogUtils
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

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

    private lateinit var location:String

    override fun initData(context: Context?) {
        val mmkv = MMKV.defaultMMKV()
        location = "${mmkv.decodeDouble(Ext.LONGITUDE)},${mmkv.decodeDouble(Ext.LATITUDE)}";

        mViewModel.apply {
            fetchWeather(location)
        }
    }

    override fun initView(inflate: LayoutInflater): View {
        binding = TabFragWeatherBinding.inflate(inflate)

        binding.apply {
            weatherViewModel = mViewModel

            stateViewWeather.retry {
                mViewModel.fetchWeather(location)
            }

            lifecycleOwner = this@WeatherFragment
        }

        return binding.root
    }
}


