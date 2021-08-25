package cn.iichen.diverseweather.ui.activity.search

import android.Manifest
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import cn.iichen.diverseweather.R
import cn.iichen.diverseweather.base.BaseActivity
import cn.iichen.diverseweather.databinding.ActivitySearchBinding
import cn.iichen.diverseweather.ext.Ext
import cn.iichen.diverseweather.ui.activity.main.MainActivity
import cn.iichen.diverseweather.utils.MultiStateView
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ToastUtils
import permissions.dispatcher.*
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ClickUtils
import com.blankj.utilcode.util.ConvertUtils.dp2px
import com.gyf.immersionbar.ktx.immersionBar
import com.qweather.sdk.bean.geo.GeoBean
import com.tencent.mmkv.MMKV
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.jetbrains.anko.startActivity


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
    private val topCityAdapter: Adapter by lazy { Adapter(ArrayList()) }

    override fun initView() {
        super.initView()

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_search)

        binding.apply {
            seachViewModel = viewmodel
            adapter = topCityAdapter
            itemDecoration = ItemDecoration(dp2px(20F))

            // 使用BindingAdapter与LiveData需要加入此行
            lifecycleOwner = this@SearchActivity

            topCityAdapter.apply {
                animationEnable = true
                setOnItemClickListener { adapter, _, position ->
                    val bean: GeoBean.LocationBean = adapter.data[position] as GeoBean.LocationBean
                    // 存储城市信息
                    val kv = MMKV.defaultMMKV()
                    kv.encode(Ext.LONGITUDE, bean.lon)
                    kv.encode(Ext.LATITUDE, bean.lat)
                    kv.encode(Ext.DISTRICK, bean.name)
                    // 跳转到首页
                    this@SearchActivity.finish()
                    startActivity<MainActivity>()
                }
            }

            // 无网络和加载失败网络重试
            stateView.retry {
                viewmodel.getGeoTopCity()
            }

            // 取消按钮时间处理
            ClickUtils.applyGlobalDebouncing(cancel) {
                val kv = MMKV.defaultMMKV()
                if (kv.decodeDouble(Ext.DISTRICK, -1.0) == -1.0) {// 未定位
                    ToastUtils.showShort(R.string.location_tip)
                    doRequestForegroundLocationPermissionWithPermissionCheck()
                } else {
                    this@SearchActivity.finish()
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        // 获取热门城市
        viewmodel.apply {
            // 初始化定位信息
            initLocationParams(this@SearchActivity)

            // SDK内的接口回调内 不知道怎么直接emit.所以定一个Livedata进行刷新
            getGeoTopCity()

            // 是否定位成功 成功跳转到主页面，失败需要重新定位或手动选择一个地址
            locationState.observe(this@SearchActivity,{
                if(it){ //跳转到主页面
                    this@SearchActivity.finish()
                    startActivity<MainActivity>()
                }else{  //失败需要重新定位或手动选择一个地址

                }
            })
        }

        // 申请权限并开启定位
        doRequestForegroundLocationPermissionWithPermissionCheck()
    }

    // 申请前台位置权限
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun doRequestForegroundLocationPermission() {
        ToastUtils.showShort(R.string.locationing)
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








