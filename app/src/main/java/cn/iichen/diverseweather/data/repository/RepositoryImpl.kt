package cn.iichen.diverseweather.data.repository

import cn.iichen.diverseweather.data.entity.WeatherNowBean
import cn.iichen.diverseweather.data.remote.Api
import cn.iichen.diverseweather.data.remote.ApiResult
import cn.iichen.diverseweather.ext.Ext
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
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
}




















