package cn.iichen.diverseweather.ui.activity

import android.view.WindowManager
import cn.iichen.diverseweather.base.BaseActivity
import cn.iichen.diverseweather.ext.Ext
import cn.iichen.diverseweather.ui.activity.main.MainActivity
import cn.iichen.diverseweather.ui.activity.search.SearchActivity
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.*
import org.jetbrains.anko.startActivity

/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity
 * @ClassName:      SplashActivity
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/23 14:38
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/23 14:38
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
@ExperimentalCoroutinesApi
@FlowPreview
class SplashActivity : BaseActivity(){
    override fun initView() {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.initView()
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.IO) {
            val kv = MMKV.defaultMMKV()
            if(kv.decodeDouble(Ext.DISTRICK,-1.0)==-1.0) {// 未定位
                withContext(Dispatchers.Main){
                    startActivity<SearchActivity>()
                }
            }else{
                withContext(Dispatchers.Main){
                    startActivity<MainActivity>()
                }
            }
        }
    }
}



