package cn.iichen.diverseweather.ui.fragment.Weather

import androidx.databinding.ObservableBoolean
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
import com.qweather.sdk.bean.geo.GeoBean
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

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
    private val _mLoading = MutableLiveData<MultiStateView.ViewState>()
    val mLoading : LiveData<MultiStateView.ViewState> = _mLoading

    // 实时天气
    fun fetchWeatherNow(location:String) = liveData{
        if(!NetworkUtils.isAvailable()){
            _mLoading.postValue(MultiStateView.ViewState.NONET)
            return@liveData
        }
        repository.fetchWeatherNow(location)
            .onStart {
                _mLoading.postValue(MultiStateView.ViewState.LOADING)
            }
            .catch {
                _mLoading.postValue(MultiStateView.ViewState.ERROR)
            }
            .collectLatest {
                it.doFailure { throwable ->
                    ToastUtils.showShort(throwable?.message ?: "failure")
                    _mLoading.postValue(MultiStateView.ViewState.ERROR)
                }
                it.doSuccess { value ->
                    if(value.code == Ext.SUCCESS){
                        emit(value.now)
                        _mLoading.postValue(MultiStateView.ViewState.CONTENT)
                    }
                    else if(value.code == Ext.EMPTY)
                        _mLoading.postValue(MultiStateView.ViewState.EMPTY)
                    else{
                        _mLoading.postValue(MultiStateView.ViewState.ERROR)
                        LogUtils.d(value.code)
                    }

                }
            }
    }

}


















