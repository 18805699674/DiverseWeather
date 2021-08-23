package cn.iichen.diverseweather.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.iichen.diverseweather.R
import com.gyf.immersionbar.ktx.immersionBar

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.base
 * @ClassName:      BaseFragment
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/20 11:06
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/20 11:06
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


abstract class BaseFragment : Fragment() {
    lateinit var mContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            // 上面的文本颜色
            statusBarDarkFont(true)
            navigationBarColor(R.color.colorPrimary)
        }
        val rootView: View = initView(inflater)
        initData(context)
        return rootView
    }

    /**
     * 初始化数据
     * @param context Context?
     */
    open fun initData(context: Context?){}

    /**
     * 初始化布局
     */
    abstract fun initView(inflate: LayoutInflater): View

}





