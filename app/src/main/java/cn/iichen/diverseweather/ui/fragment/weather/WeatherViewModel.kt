package cn.iichen.diverseweather.ui.fragment.weather

import android.util.Log
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.data.entity.NowBaseBean
import cn.iichen.diverseweather.data.remote.doFailure
import cn.iichen.diverseweather.data.remote.doSuccess
import cn.iichen.diverseweather.data.repository.Repository
import cn.iichen.diverseweather.ext.Ext
import cn.iichen.diverseweather.utils.MultiStateView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.qweather.sdk.bean.weather.WeatherNowBean
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import java.lang.Exception

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.fragment.Weather
 * @ClassName:      WeatherViewModel
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/23 21:44
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/23 21:44
 * @UpdateRemark:   更新说明：Fuck code, go to hell, serious people who maintain it：
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
// 仓库层的 Flow可以转为LiveData与其一起使用
@ExperimentalCoroutinesApi
@FlowPreview
class WeatherViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    // 当前定位城市 区
    val districk = ObservableField<String>()
    // 当前时间刷新使用
    val nowTime = ObservableField<String>()

    // 记录当前的 经纬度
    lateinit var location:String


    // 实时天气 使用WebApi其余用SDK了 演示一下
    val weatherNow = ObservableField<NowBaseBean>()
    fun fetchWeatherNow()  = liveData {
        repository.fetchWeatherNow(location)
            .collectLatest {
                it.doFailure { throwable ->
                    ToastUtils.showShort(throwable?.message ?: "failure")
                    emit(false)
                }
                it.doSuccess { value ->
                    // 刷新UI
                    emit(true)
                    if(value.code == Ext.SUCCESS){
                        // dataBinding视图绑定
                        weatherNow.set(value.now)
                    }
                    else if(value.code == Ext.EMPTY)
                        ToastUtils.showShort("无数据!")
                    else{
                        LogUtils.d(value.code)
                    }
                }
            }
    }


    // 分钟降水  暂时开发版 无权限
    fun fetchMinuteLy() {
        viewModelScope.launch {
            try{
                val data = repository.fetchMinuteLy(location)
                data.doFailure {
                    ToastUtils.showShort(it?.message ?: "failure")
                }
                data.doSuccess {
                    LogUtils.d(it.summary)
                    it.minutelyList.forEach {
                        LogUtils.d("${it.precip}-${it.type}")
                    }
                }
            }catch (e: Exception){
                ToastUtils.showShort(e.message ?: "failure")
                LogUtils.d(e.message ?: "failure")
            }
        }
    }
}


















