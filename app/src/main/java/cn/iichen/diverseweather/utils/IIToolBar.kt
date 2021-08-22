package cn.iichen.diverseweather.utils

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
import android.widget.ImageButton
import cn.iichen.diverseweather.R
import com.billy.android.swipe.SmartSwipe.dp2px


/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.utils
 * @ClassName:      IIToolBar
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/21 22:51
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/21 22:51
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


class IIToolBar : Toolbar {
    private lateinit var mCenterTitle: TextView
    private lateinit var mLeftIcon: ImageButton
    private lateinit var mSettingIcon: ImageButton
    private lateinit var mContext: Context



    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context)
    }

    private fun init(context: Context) {
        this.mContext = context
    }

    fun setCenterTitle(text: CharSequence?): IIToolBar {
        val context = this.context
        mCenterTitle =  TextView(context)
        this.mCenterTitle = TextView(context)
        mCenterTitle.text = text
        this.mCenterTitle.gravity = Gravity.CENTER_VERTICAL
        this.mCenterTitle.setSingleLine()
        this.mCenterTitle.setTextAppearance(R.style.ToolbarTitle)
        val lp:LayoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
        lp.setMargins(left, top, right, bottom);
        addView(mCenterTitle, lp);
        return this
    }

    fun setLeftIcon(text: CharSequence?): IIToolBar {

        return this
    }
}
























