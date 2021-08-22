package cn.iichen.diverseweather.ui.activity.search

import android.Manifest
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.base.BaseActivity
import cn.iichen.diverseweather.databinding.ActivitySearchBinding
import cn.iichen.diverseweather.utils.MultiStateView
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ToastUtils
import permissions.dispatcher.*
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.qweather.sdk.bean.geo.GeoBean
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


/**
 *
 * @ProjectName:    DiverseWeather
 * @Package:        cn.iichen.diverseweather.ui.activity
 * @ClassName:      SearchActivity
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2021/8/20 13:38
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2021/8/20 13:38
 * @UpdateRemark:   更新说明：城市搜索页面
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

@AndroidEntryPoint
@ExperimentalCoroutinesApi
@FlowPreview
@RuntimePermissions
class SearchActivity : BaseActivity(){

    private lateinit var binding : ActivitySearchBinding
    private val viewmodel : SearchViewModel by viewModels()
    lateinit var adapter:Adapter

    override fun initView() {
        super.initView()
        adapter = Adapter(ArrayList<GeoBean.LocationBean>())

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_search)
        // 初始化定位信息
        viewmodel.initLocationParams(this)

        immersionBar{
            titleBar(binding.toolbar)
        }

        binding.apply {
            seachViewModel = viewmodel
            recycle.adapter = adapter
            adapter.setOnItemClickListener { adapter, view, position ->
                ToastUtils.showShort("$position")
            }
            adapter.animationEnable = true
            // 无网络和加载失败网络重试
            stateView.getView(MultiStateView.ViewState.NONET)?.run {
                setOnClickListener {
                    stateView.viewState = MultiStateView.ViewState.LOADING
                    viewmodel.getGeoTopCity(this@SearchActivity)
                }
            }
            stateView.getView(MultiStateView.ViewState.ERROR)?.run {
                setOnClickListener {
                    stateView.viewState = MultiStateView.ViewState.LOADING
                    viewmodel.getGeoTopCity(this@SearchActivity)
                }
            }
        }

        // 获取热门城市
        viewmodel.getGeoTopCity(this)
        viewmodel.mLoading.observe(this,{
            binding.stateView.viewState = it
        })
        
        viewmodel.locationBeanList.observe(this,{
            adapter.setNewInstance(it.toMutableList())
        })
    }

    override fun initData() {
        super.initData()

//        doRequestForegroundLocationPermissionWithPermissionCheck()
    }

    // 申请前台位置权限
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun doRequestForegroundLocationPermission() {
        ToastUtils.showShort("开始定位！")
        viewmodel.startLocation()
    }
    // 展示为什么需要权限
    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun showLocationRationale(request: PermissionRequest){
        MaterialDialog(this).show {
            message(text = "需要定位权限才能继续哦！")
            positiveButton {
                request.proceed()
            }
            negativeButton {
                request.cancel()
            }
        }
    }
    // 被拒绝后的提示
    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun showDeniedMessage() {
        ToastUtils.showShort("需要定位权限！")
    }

    // 拒绝且不再询问
    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun onNeverAskAgain() {
        MaterialDialog(this).show {
            message(text = "需要定位权限才能继续哦！")
            positiveButton {
                // 跳转到设置页面
                ToastUtils.showShort("跳转到设置页面！")
                AppUtils.launchAppDetailsSettings()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 停止定位
        viewmodel.destroyLocation()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}








