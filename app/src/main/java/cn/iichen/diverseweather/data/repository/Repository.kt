package cn.iichen.diverseweather.data.repository

import cn.iichen.diverseweather.data.entity.WeatherNowBean
import cn.iichen.diverseweather.data.remote.ApiResult
import com.qweather.sdk.bean.geo.GeoBean
import kotlinx.coroutines.flow.Flow

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


interface Repository {
    suspend fun fetchWeatherNow(location:String) : Flow<ApiResult<WeatherNowBean>>

    suspend fun fetchGeoTopCity() : ApiResult<List<GeoBean.LocationBean>>
}























