纷纭天气WebAPI:（受限）    
    天气:(全球)
        * [当前天气详情](https://dev.qweather.com/docs/api/weather/weather-now/)         顶部大Card展示，点击跳转到详情页 
        * [24小时天气预报](https://dev.qweather.com/docs/api/weather/weather-hourly-forecast/)      使用ViewPage等滚动，点击跳转到详情页
        * [3/7/15天的天气预报](https://dev.qweather.com/docs/api/weather/weather-daily-forecast/)    使用ViewPage等滚动，点击跳转到详情页
    城市查询:
        * [城市信息查询搜索(返回列表)](https://dev.qweather.com/docs/api/geo/city-lookup/) 
        * [热门城市查询(返回列表)](https://dev.qweather.com/docs/api/geo/top-city/)
    天气生活指数：(WebApi仅仅可以查询当天)
        * [天气生活指数](https://dev.qweather.com/docs/api/indices/)
        * [实时空气质量](https://dev.qweather.com/docs/api/air/air-now/)
        * [空气质量预报](https://dev.qweather.com/docs/api/air/air-daily-forecast/)
    预警:
        * [天气灾害预警](https://dev.qweather.com/docs/api/warning/weather-warning/)
        * [预警城市列表](https://dev.qweather.com/docs/api/warning/weather-warning-city-list/)
    * [图标](https://dev.qweather.com/docs/start/icons/)


* 图片就用网站的[后面给屏幕添加动画](
    @天气     添加 大风、大雨、闪电、冰雹、雾霾等
    @生活     
    @预警     
    @台风等    
  )
  
* model文件夹存放的是实际的UI层需要的数据。entry文件夹是请求原始的数据流，其中经过转换为model层实现 分离(这里直接用原始数据流了)



200	请求成功
204	请求成功，但你查询的地区暂时没有你需要的数据。
400	请求错误，可能包含错误的请求参数或缺少必选的请求参数。
401	认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误（如使用SDK的KEY去访问Web API）。
402	超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。
403	无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。
404	查询的数据或地区不存在。
429	超过限定的QPM（每分钟访问次数），请参考QPM说明
500	无响应或超时，接口服务异常请联系我们