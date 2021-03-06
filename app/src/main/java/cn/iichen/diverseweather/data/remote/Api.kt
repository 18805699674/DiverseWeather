package cn.iichen.diverseweather.data.remote

import android.content.Context
import cn.iichen.diverseweather.data.entity.NowBaseBean
import cn.iichen.diverseweather.data.entity.WeatherNowBean
import com.blankj.utilcode.util.LogUtils
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.intellij.lang.annotations.Flow
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.data.remote
 * @ClassName:      Api
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/22 18:24
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/22 18:24
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


interface Api {
    //    https://devapi.qweather.com/v7/weather/now  实时天气
    @GET("weather/now")
    suspend fun fetchWeatherNow(
        @Query("location") location:String,
        @Query("key") key:String = "c869e831072e464db165953498b1a84f"
    ) : WeatherNowBean
}


















