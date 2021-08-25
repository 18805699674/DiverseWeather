package cn.iichen.diverseweather.ui.binding

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.ui.activity.search.Adapter
import cn.iichen.diverseweather.ui.activity.search.ItemDecoration
import cn.iichen.diverseweather.utils.MultiStateView
import coil.load
import com.qweather.sdk.bean.geo.GeoBean
import timber.log.Timber

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


@BindingAdapter("bindingAvator")
fun bindingAvator(imageView: ImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.mipmap.logo)
    }
}


// 多状态
@BindingAdapter("bindingViewState")
fun bindingViewState(stateView:MultiStateView,viewState: MultiStateView.ViewState){
    stateView.viewState = viewState
}

// 时间绑定 android:onClick="@{()-> presenter.loginTask(loginbean)}"

@BindingAdapter("bindRetry")
fun bindingViewRetry(stateView: MultiStateView,call:()->Unit){
    stateView.retry(call)
}

// 点击跳转
//@BindingAdapter("bindClick")
//fun bindingClick(view: View, model: PokemonItemModel) {
//    view.setOnClickListener {
//        DetailActivity.jumpAcrtivity(
//            view.context,
//            model
//        )
//    }
//}

// 给返回按钮绑定 退出销毁到上一个页面       true标识就是 点击返回，false不是
@BindingAdapter("bindFinish")
fun bindingFinish(view: View, finish: Boolean) {
    val ctx = view.context
    if (ctx is Activity && finish) {
        view.setOnClickListener { ctx.finish() }
    }
}

// 搜索页面 绑定RecycleView
@BindingAdapter("bindAdapter", "bindData","bindItemDecoration")
fun bindingSearchAdapter(
    recyclerView: RecyclerView,
    adapter: Adapter,
    data: MutableList<GeoBean.LocationBean>?,
    itemDecoration: ItemDecoration
) {
    data?.let {
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(itemDecoration)
        adapter.setNewInstance(data)
    }
}
