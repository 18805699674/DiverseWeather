package cn.iichen.diverseweather.data.repository

import cn.iichen.diverseweather.data.entity.WeatherNowBean
import cn.iichen.diverseweather.data.remote.Api
import cn.iichen.diverseweather.data.remote.ApiResult
import cn.iichen.diverseweather.ext.Ext
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.qweather.sdk.bean.MinutelyBean
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.view.HeContext.context
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resumeWithException

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.data.repository
 * @ClassName:      RepositoryImpl
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/23 21:22
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/23 21:22
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


class RepositoryImpl(
    val api: Api,
) : Repository{
    // 实时天气
    override suspend fun fetchWeatherNow(location: String): Flow<ApiResult<WeatherNowBean>> {
        return flow {
            try {
                val weatherNowBean = api.fetchWeatherNow(location)
                emit(ApiResult.Success(weatherNowBean))
            }catch (e:Exception){
                emit(ApiResult.Failure(e.cause))
            }
        }.flowOn(Dispatchers.IO)
    }

    // 热门城市
    @ExperimentalCoroutinesApi
    override suspend fun fetchGeoTopCity(): ApiResult<List<GeoBean.LocationBean>> =
        suspendCancellableCoroutine {
            QWeather.getGeoTopCity(context, 20, Range.CN, Lang.ZH_HANS, object :
                QWeather.OnResultGeoListener {
                    override fun onError(p0: Throwable?) {
                        p0?.run {
                            ToastUtils.showShort(message)
                            it.resumeWithException(this)
                        }
                    }
                    override fun onSuccess(p0: GeoBean?) {
                        p0?.run {
                            if(Ext.SUCCESS == code.code){// 请求成功且有数据
                                it.resume(ApiResult.Success(locationBean)){ LogUtils.d("数据回调被取消！")}

                            }else{
                                if(Ext.EMPTY == code.code)
                                    it.resume(ApiResult.Success(ArrayList())){ LogUtils.d("数据回调被取消！")}
                                else{
                                    it.resume(ApiResult.Failure(Throwable(code.txt))){ LogUtils.d("数据回调被取消！")}
                                }
                            }
                        }
                    }
                }
            )
        }

    // 分钟降水
    @ExperimentalCoroutinesApi
    override suspend fun fetchMinuteLy(location: String): ApiResult<MinutelyBean> =
        suspendCancellableCoroutine {
            QWeather.getMinuteLy(context, location, object :
                QWeather.OnResultMinutelyListener {
                    override fun onError(p0: Throwable?) {
                        p0?.run {
                            it.resumeWithException(this)
                        }
                    }

                    override fun onSuccess(p0: MinutelyBean?) {
                    p0?.run {
                        if (Ext.SUCCESS == code.code)// 请求成功且有数据
                            it.resume(ApiResult.Success(this)) {
                                LogUtils.d("数据回调被取消！")
                            } else {
                            if (Ext.EMPTY == code.code)
                                it.resume(ApiResult.Failure(Throwable("请求的数据为空"))) { LogUtils.d("数据回调被取消！") }
                            else {
                                it.resume(ApiResult.Failure(Throwable(code.txt))) { LogUtils.d("数据回调被取消！") }
                            }
                        }
                    }
                }
                }
            )
        }

    // 15天预报
    @ExperimentalCoroutinesApi
    override suspend fun fetchWeather15D(location: String): ApiResult<MutableList<WeatherDailyBean.DailyBean>> =
        suspendCancellableCoroutine {
            QWeather.getWeather15D(context, location, object :
                QWeather.OnResultWeatherDailyListener  {
                    override fun onError(p0: Throwable?) {
                        p0?.run {
                            it.resumeWithException(this)
                        }
                    }

                    override fun onSuccess(p0: WeatherDailyBean?) {
                        p0?.run {
                            if (Ext.SUCCESS == code.code)// 请求成功且有数据
                                it.resume(ApiResult.Success(this.daily)) {
                                    LogUtils.d("数据回调被取消！")
                                } else {
                                if (Ext.EMPTY == code.code)
                                    it.resume(ApiResult.Failure(Throwable("请求的数据为空"))) { LogUtils.d("数据回调被取消！") }
                                else {
                                    it.resume(ApiResult.Failure(Throwable(code.txt))) { LogUtils.d("数据回调被取消！") }
                                }
                            }
                        }
                    }
                }
            )
        }

    // 24小数预报
    override suspend fun fetchWeatherHourly(location: String): ApiResult<MutableList<WeatherHourlyBean.HourlyBean>> =
        suspendCancellableCoroutine {
            //getWeather24Hourly   （getWeather72Hourly    getWeather168Hourly 无权限）
            QWeather.getWeather24Hourly(context, location, object :
                QWeather.OnResultWeatherHourlyListener  {
                    override fun onError(p0: Throwable?) {
                        p0?.run {
                            it.resumeWithException(this)
                        }
                    }

                    override fun onSuccess(p0: WeatherHourlyBean?) {
                        p0?.run {
                            if (Ext.SUCCESS == code.code)// 请求成功且有数据
                                it.resume(ApiResult.Success(this.hourly)) {
                                    LogUtils.d("数据回调被取消！")
                                } else {
                                if (Ext.EMPTY == code.code)
                                    it.resume(ApiResult.Failure(Throwable("请求的数据为空"))) { LogUtils.d("数据回调被取消！") }
                                else {
                                    it.resume(ApiResult.Failure(Throwable(code.txt))) { LogUtils.d("数据回调被取消！") }
                                }
                            }
                        }
                    }
                }
            )
        }
}




















