package cn.iichen.diverseweather.ui.fragment.weather

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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
    val mLoading = ObservableField<MultiStateView.ViewState>()
    // 当前定位城市 区
    val districk = ObservableField<String>()
    // 记录当前的 经纬度
    lateinit var location:String

    // 实时天气 使用WebApi其余用SDK了 演示一下
    val weatherNow = ObservableField<NowBaseBean>()
    fun fetchWeatherNow() {
        if(!NetworkUtils.isAvailable()){
            mLoading.set(MultiStateView.ViewState.NONET)
            return
        }
        viewModelScope.launch{
            repository.fetchWeatherNow(location)
                .onStart {
                    mLoading.set(MultiStateView.ViewState.LOADING)
                }
                .catch {
                    mLoading.set(MultiStateView.ViewState.ERROR)
                }
                .collectLatest {
                    it.doFailure { throwable ->
                        ToastUtils.showShort(throwable?.message ?: "failure")
                        mLoading.set(MultiStateView.ViewState.ERROR)
                    }
                    it.doSuccess { value ->
                        if(value.code == Ext.SUCCESS){
                            weatherNow.set(value.now)
                            mLoading.set(MultiStateView.ViewState.CONTENT)
                        }
                        else if(value.code == Ext.EMPTY)
                            mLoading.set(MultiStateView.ViewState.EMPTY)
                        else{
                            mLoading.set(MultiStateView.ViewState.ERROR)
                            LogUtils.d(value.code)
                        }
                    }
                }
        }
    }
}


















