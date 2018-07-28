com.bupt.wechatplugin:
    
    controller: 控制器类
        WechatController: 
            1.微信服务器验证
            2.接收平台json数据并发送模板消息
    
    dao： 数据库方法
        UserDao: User Mapping 
    
    omain: 模型实体
        AccessToken：accesstoken模型
        BaseMessage：消息基类
        News：文章消息类
        NewsMessage：图文消息类 
        TextMessage: 文本消息类
        TemplateNews: 模板消息类
        User：用户模型
        Device： 设备模型
    
    service: 业务服务层（接口）
        CoreService： 解析平台发送的json数据
        WechatService： 暂无功能
        
        impl：业务服务接口具体实现
        
    util  工具包
        JsonUtil： 处理json.XML的转换
        MessageUtil: 消息的接收和发送处理
        weixinUtil: 
            1. 微信服务器校验方法
            2. 获取access_token 方法
            3. 与微信服务器交互的Json格式封装
     
resource.application.yml 配置信息

pom.xml  工程配置和依赖文件