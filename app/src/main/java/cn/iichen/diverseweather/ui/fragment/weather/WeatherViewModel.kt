package cn.iichen.diverseweather.ui.fragment.weather

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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

@ExperimentalCoroutinesApi
@FlowPreview
class WeatherViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val mLoading = ObservableField<MultiStateView.ViewState>()

    // 并发请求多个接口的数据
    fun fetchWeather(location:String){
        if(!NetworkUtils.isAvailable()){
            mLoading.set(MultiStateView.ViewState.NONET)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            mLoading.set(MultiStateView.ViewState.LOADING)
            val res = async {
                fetchWeatherNow(location)
            }
            LogUtils.d()
            mLoading.set(MultiStateView.ViewState.CONTENT)
        }
    }
    // 实时天气
    private val _weatherNow = MutableLiveData<WeatherNowBean.NowBaseBean>()
    val weatherNow: LiveData<WeatherNowBean.NowBaseBean> = _weatherNow
    private suspend fun fetchWeatherNow(location:String) {
        return repository.fetchWeatherNow(location)
            .collectLatest {
                it.doFailure { throwable ->
                    ToastUtils.showShort(throwable?.message ?: "failure")
                }
                it.doSuccess { value ->
                    if(value.code == Ext.SUCCESS){
                        _weatherNow.postValue(value.now!!)
                    }else{
                        LogUtils.d(value.code)
                    }
                }
            }
    }

}


















