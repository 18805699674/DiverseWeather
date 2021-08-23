package cn.iichen.diverseweather.ui.activity.search

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.blankj.utilcode.util.LogUtils
import java.security.AccessControlContext
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.data.remote.ApiResult
import cn.iichen.diverseweather.ext.Ext
import cn.iichen.diverseweather.utils.MultiStateView
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.loc.em
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity.search
 * @ClassName:      SearchViewModel
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/21 21:16
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/21 21:16
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
class SearchViewModel @ViewModelInject constructor(): ViewModel() {
    // 定位状态
    private val _locationState = MutableLiveData<Boolean>()
    val locationState : LiveData<Boolean> = _locationState
    //声明AMapLocationClient类对象
    lateinit var mLocationClient: AMapLocationClient
    lateinit var mLocationOption: AMapLocationClientOption
    lateinit var mLocationListener: AMapLocationListener

    fun initLocationParams(context: Context){
        mLocationClient = AMapLocationClient(context);
        // 配置定位的参数
        mLocationOption = AMapLocationClientOption()
        //声明定位回调监听器
        mLocationListener = AMapLocationListener {
            it.let {
                if (it.errorCode == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    LogUtils.d(it.address)
                    ToastUtils.showShort(R.string.location_success)
                    // 对应的区
                    val kv = MMKV.defaultMMKV()
                    kv.encode(Ext.LONGITUDE,it.longitude)
                    kv.encode(Ext.LATITUDE,it.latitude)
                    kv.encode(Ext.DISTRICK,it.district)
                    _locationState.postValue(true)
                }else {
                    LogUtils.e("location Error, ErrCode:"
                            + it.errorCode + ", errInfo:"
                            + it.errorInfo
                    )
                    ToastUtils.showShort(it.errorInfo)
                    _locationState.postValue(false)
                }
            }

        }
        mLocationClient.setLocationListener(mLocationListener);
        // 设置高精度定位模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        // 单次定位
        mLocationOption.isOnceLocation = true
        mLocationOption.isOnceLocationLatest = true
        // 定位完返回地址数据
        mLocationOption.isNeedAddress = true
        // 允许模拟位置
        mLocationOption.isMockEnable = true
        mLocationClient.setLocationOption(mLocationOption)
    }

    fun startLocation(){
        mLocationClient.startLocation()
    }

    fun destroyLocation(){
        mLocationClient.stopLocation()
        mLocationClient.onDestroy()
    }


//  获取热门城市 （使用SDK模式）
    private val _mLoading = MutableLiveData<MultiStateView.ViewState>()
    val mLoading : LiveData<MultiStateView.ViewState> = _mLoading
    private val _locationBeanList = MutableLiveData<List<GeoBean.LocationBean>>()
    val locationBeanList:LiveData<List<GeoBean.LocationBean>> = _locationBeanList
    fun getGeoTopCity(context: Context) {
        _mLoading.postValue(MultiStateView.ViewState.LOADING)
        if(!NetworkUtils.isAvailable()){
            _mLoading.postValue(MultiStateView.ViewState.NONET)
            return
        }
        QWeather.getGeoTopCity(context, 20, Range.CN, Lang.ZH_HANS, object :
            QWeather.OnResultGeoListener {
            override fun onError(p0: Throwable?) {
                p0?.run {
                    ToastUtils.showShort(message)
                }
                _mLoading.postValue(MultiStateView.ViewState.ERROR)
            }

            override fun onSuccess(p0: GeoBean?) {
                p0?.run {
                    if(Ext.SUCCESS == code.code){// 请求成功且有数据
                        _mLoading.postValue(MultiStateView.ViewState.CONTENT)
                        _locationBeanList.postValue(locationBean)
                    }else{
                        if(Ext.EMPTY == code.code)
                            _mLoading.postValue(MultiStateView.ViewState.EMPTY)
                        else{
                            _mLoading.postValue(MultiStateView.ViewState.ERROR)
                            ToastUtils.showShort(code.txt)
                        }
                    }
                }
            }
        }
        )
    }
}
/*
200	请求成功
204	请求成功，但你查询的地区暂时没有你需要的数据。
400	请求错误，可能包含错误的请求参数或缺少必选的请求参数。
401	认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误（如使用SDK的KEY去访问Web API）。
402	超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。
403	无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。
404	查询的数据或地区不存在。
429	超过限定的QPM（每分钟访问次数），请参考QPM说明
500	无响应或超时，接口服务异常请联系我们
 */




















