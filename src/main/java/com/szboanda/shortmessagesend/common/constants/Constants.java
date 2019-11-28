package com.szboanda.shortmessagesend.common.constants;

/**
 * 协同平台常量类
 * 
 * @author zhangsheng
 *
 */
public final class Constants {
    
    
    
    
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DATA_SOURCE = "jdbc/default";
    
    
	
	/**
	 * 重发任务时每次对去任务的个数
	 */
    public static final int RESEND_PAGE_SIZE = 100;
    


    /**
     * 监听操作类型
     * 
     * @author zhangsheng
     *
     */
    public static final class ListenerOperation {
        /**
         * 保存应用系统信息
         */
        public static final String SYSTEM_SAVE = "SYSTEM_SAVE";

        /**
         * 删除应用系统信息
         */
        public static final String SYSTEM_DELETE = "SYSTEM_DELETE";

        /**
         * 保存协同服务信息
         */
        public static final String SERVICE_SAVE = "SERVICE_SAVE";

        /**
         * 删除协同服务信息
         */
        public static final String SERVICE_DELETE = "SERVICE_DELETE";

        /**
         * 订阅关系
         */
        public static final String SUBSCRIPTION_SAVE = "SUBSCRIPTION_SAVE";

        /**
         * 删除订阅关系
         */
        public static final String SUBSCRIPTION_DELETE = "SUBSCRIPTION_DELETE";

    }

    /**
     * 缓存名称
     * 
     * @author zhangsheng
     *
     */
    public static final class CacheName {

        /**
         * 应用系统
         */
        public static final String SYSTEM = "SYSTEM";

        /**
         * 协同服务
         */
        public static final String SERVICE = "SERVICE";

        /**
         * 订阅关系
         */
        public static final String SUBSCRIPTION = "SUBSCRIPTION";

    }

    /**
     * 消息状态码
     * 
     * @author zhangsheng
     *
     */
    public static final class MsgStatus {

        /**
         * 正常
         */
        public static final String STATUS_000 = "000";

        /**
         * 协同服务内部错误
         */
        public static final String STATUS_100 = "100";

        /**
         * 子系统调用协同服务发送消息错误，检查服务地址是否存在以及网络是否通畅
         */
        public static final String STATUS_101 = "101";

        /**
         * 子系统协同消息处理出错
         */
        public static final String STATUS_102 = "102";

        /**
         * 目标系统拒绝服务,确认服务开启
         */
        public static final String STATUS_103 = "103";
        
        /**
         * 目标系统未找到相应的资源
         */
        public static final String STATUS_104 = "104";
        
        /**
         * 子系统接收消息解析参数出错
         */
        public static final String STATUS_105 = "105";

        /**
         * 发布系统无效,检查系统key与平台配置是否一直
         */
        public static final String STATUS_199 = "199";
        
        /**
         * 无调用权限，确认发布者为当前服务的发布者
         */
        public static final String STATUS_200 = "200";

        /**
         * 无有效的订阅系统，确认当前消息服务有订阅者
         */
        public static final String STATUS_201 = "201";

        /**
         * 协同平台token值与ebs-client中 协同平台token值不一致
         */
        public static final String STATUS_202 = "202";

        /**
         * ip来源与ebs-client中 ip项不一致
         */
        public static final String STATUS_203 = "203";

        /**
         * hash校验失败
         */
        public static final String STATUS_204 = "204";

        /**
         * 客户端ip、port 与协同平台一致、属于同一个服务、不合理
         */
        public static final String STATUS_205 = "205";

        /**
         * 无响应的消息处理器
         */
        public static final String STATUS_210 = "210";

        /**
         * 参数格式校验不通过
         */
        public static final String STATUS_300 = "300";

        /**
         * 数据重复
         */
        public static final String STATUS_400 = "400";

        /**
         * 污染源编码重复
         */
        public static final String STATUS_401 = "401";

        /**
         * 流程类型序号重复
         */
        public static final String STATUS_402 = "402";

        /**
         * 流程实例序号重复
         */
        public static final String STATUS_403 = "403";

        /**
         * 节点实例序号重复
         */
        public static final String STATUS_404 = "404";
        
        
        /**
         * 无有效数据
         */
        public static final String STATUS_410 = "410";
        

        /**
         * 服务出错
         */
        public static final String STATUS_500 = "500";
        
        /**
         * 服务参数解析失败
         */
        public static final String STATUS_501 = "501";

        /**
         * 用户密码校验错误
         */
        public static final String STATUS_601 = "601";
        
        /**
         * MQ消息服务内部错误
         */
        public static final String STATUS_801 = "801";
    }
    
    
    /**
     * 数据库类型
     * @author zhangsheng
     *
     */
    public static final class DatabaseName{
        
        /**
         * oralce
         */
        public static final String ORACLE = "Oracle";
        
        /**
         * MySQL
         */
        public static final String MYSQL = "MySQL";
        
        /**
         * SQL Server
         */
        public static final String SQLSERVER = "Microsoft SQL Server";
    }
    
    
    /**
     * 数据库类型
     * @author zhangsheng
     *
     */
    public static final class ServiceUrls{
    	
        /**
         * 默认数据源
         */
        public static final String ROOT_URL_KEY = "service.url";
    	
    	/**
    	 * 污染源匹配
    	 */
    	public static final String FOULS_MATCH = "restful/service/foulssourcewebservice/getsimilarfoulssource";
    	
    	/**
    	 * 污染源同步
    	 */
    	public static final String SAVE_FOULS = "restful/service/foulssourcewebservice/syncpollution";
    	
    	
    }
    
    /**
     * 文件传输方式
     * @author zhangsheng
     *
     */
    public static final class FileTransmit{
        
        /**
         * 配置key
         */
        public static final String CONFIG_KEY = "fileTransmit.method";
        
        /**
         * 通过mongo中转
         */
        public static final String MONGO = "mongo";
        
        
        /**
         * 直接通过http流进行
         */
        public static final String STREAM = "stream";
        
        
        /**
         * mongo文件标识信息在xml文本中的node名称
         */
        public static final String NODE_NAME = "mongoFile";
        
        /**
         * 下载文件时，保存文件路径的前缀
         */
        public static final String STORE_PRESS_KEY = "fileTransmit.filepress";
        
        /**
         * 下载文件时，保存文件路径的前缀默认值
         */
        public static final String STORE_PRESS_DEFAULT = "D:\\ebs\\";
        
        
    }
    

    /**
     * 
     * @Title:
     * @author zhangsheng
     * @since JDK1.6
     * @history 2018年4月24日 zhangsheng 新建
     */
    public static final class EbsClientKey {

        /**
         * http请求读取时间
         */
        public static final String HTTP_READ_TIMEOUT = "service.timeout";
        
        
        
    }
}
