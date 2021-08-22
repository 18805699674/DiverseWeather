package cn.iichen.diverseweather.ui.activity.search

import cn.iichen.diverseweather.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qweather.sdk.bean.geo.GeoBean

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity.search
 * @ClassName:      Adapter
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/22 21:37
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/22 21:37
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


class Adapter(list: List<GeoBean.LocationBean>) :
    BaseQuickAdapter<GeoBean.LocationBean, BaseViewHolder>(R.layout.recycle_city) {

    override fun convert(holder: BaseViewHolder, item: GeoBean.LocationBean) {
        holder.setText(R.id.city,item.name)
    }
}











