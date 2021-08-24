package cn.iichen.diverseweather.data.repository

import cn.iichen.diverseweather.data.entity.WeatherNowBean
import cn.iichen.diverseweather.data.remote.Api
import cn.iichen.diverseweather.data.remote.ApiResult
import cn.iichen.diverseweather.ext.Ext
import com.qweather.sdk.bean.geo.GeoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception

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

    override suspend fun fetchGeoTopCity(): List<GeoBean.LocationBean>  =
        suspendCancellableCoroutine<List<GeoBean.LocationBean>> {

        }
}




















