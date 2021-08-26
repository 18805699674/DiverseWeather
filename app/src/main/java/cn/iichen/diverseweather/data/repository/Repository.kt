package cn.iichen.diverseweather.data.repository

import cn.iichen.diverseweather.data.entity.WeatherNowBean
import cn.iichen.diverseweather.data.remote.ApiResult
import com.qweather.sdk.bean.MinutelyBean
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
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
    // 实时天气
    suspend fun fetchWeatherNow(location:String) : Flow<ApiResult<WeatherNowBean>>

    // 热门城市
    suspend fun fetchGeoTopCity() : ApiResult<List<GeoBean.LocationBean>>

    // 分钟降水
    suspend fun fetchMinuteLy(location:String) : ApiResult<MinutelyBean>

    // 逐天预报
    suspend fun fetchWeather15D(location:String) : ApiResult<MutableList<WeatherDailyBean.DailyBean>>

    // 逐小数预报
    suspend fun fetchWeatherHourly(location:String) : ApiResult<MutableList<WeatherHourlyBean.HourlyBean>>

    // 城市信息搜索

    // 天气生活质量

    // 天气质量实况

    // 天气质量逐天预报

    //

    //

    // 灾害预警

    // 灾害预警列表

    //

    //
    //
    //
    //
    //
    //
}























