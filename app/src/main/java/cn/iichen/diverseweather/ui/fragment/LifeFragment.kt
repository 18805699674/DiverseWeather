package cn.iichen.diverseweather.ui.fragment

import android.content.Context
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.base.BaseFragment
import timber.log.Timber

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.data.db.entity
 * @ClassName:      placeHodler
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/19 15:57
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/19 15:57
 * @UpdateRemark:   更新说明：底部导航Tab页  生活指数以及空气质量等
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


class LifeFragment : BaseFragment() {
    override fun initLayout(): Int = R.layout.tab_frag_life

    override fun initData(context: Context?) {
        Timber.d("LifeFragment")
    }
}


