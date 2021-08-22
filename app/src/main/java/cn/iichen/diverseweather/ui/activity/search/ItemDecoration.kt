package cn.iichen.diverseweather.ui.activity.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView




/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity.search
 * @ClassName:      ItemDecoration
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/22 22:42
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/22 22:42
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

class ItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space
        outRect.bottom = space
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % 3 == 0) {
            outRect.left = 0
        }
    }
}














