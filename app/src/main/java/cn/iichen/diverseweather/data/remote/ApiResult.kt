package cn.iichen.diverseweather.data.remote

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.data.remote
 * @ClassName:      ApiResult
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/22 19:28
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/22 19:28
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

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>()

    data class Failure(val throwable: Throwable?) : ApiResult<Nothing>()
}

inline fun <reified T> ApiResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is ApiResult.Success) {
        success(value)
    }
}

inline fun <reified T> ApiResult<T>.doFailure(failure: (Throwable?) -> Unit) {
    if (this is ApiResult.Failure) {
        failure(throwable)
    }
}












