# soc-common
云海基础库，目前分为两个模块：
* soc-common-core：核心模块，封装常用的一些组件
    * com.treeyh.common.constants：常量包，定义一些常量；
    * com.treeyh.common.exception：异常包，定义程序异常，错误信息，以及校验工具类；
    * com.treeyh.common.model：模型包，定义一些通用对象；
    * com.treeyh.common.utils：工具包，一些常用工具；
    * com.treeyh.common.zookeeper：zookeeper工具包；
* soc-common-web：web模块，封装spring boot项目的一些基础逻辑
    * com.treeyh.common.web.cache：缓存包
    * com.treeyh.common.web.config：项目基础配置模包
    * com.treeyh.common.web.context：程序上下文包
    * com.treeyh.common.web.filter：过滤器包，记录输入输出日志，请求校验等
    * com.treeyh.common.web.listener：监听器包，进行优雅程序启停等操作
    * com.treeyh.common.web.utils：web工具包
