package com.boxiaoyun.common.constants;

/**
 * @author
 */
public class CommonConstants {
    /**
     * 默认超级管理员账号
     */
    public final static String ROOT = "admin";

    /**
     * 默认最小页码
     */
    public static final int MIN_PAGE = 0;
    /**
     * 最大显示条数
     */
    public static final int MAX_LIMIT = 999;
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE = 1;
    /**
     * 默认显示条数
     */
    public static final int DEFAULT_LIMIT = 10;
    /**
     * 页码 KEY
     */
    public static final String PAGE_KEY = "page";
    /**
     * 显示条数 KEY
     */
    public static final String PAGE_LIMIT_KEY = "limit";
    /**
     * 排序字段 KEY
     */
    public static final String PAGE_SORT_KEY = "sort";
    /**
     * 排序方向 KEY
     */
    public static final String PAGE_ORDER_KEY = "order";

    /**
     * 客户端ID KEY
     */
    public static final String SIGN_API_KEY = "ApiKey";

    /**
     * 客户端秘钥 KEY
     */
    public static final String SIGN_SECRET_KEY = "SecretKey";

    /**
     * 随机字符串 KEY
     */
    public static final String SIGN_NONCE_KEY = "NONCE";
    /**
     * 时间戳 KEY
     */
    public static final String SIGN_TIMESTAMP_KEY = "TIMESTAMP";
    /**
     * 签名类型 KEY
     */
    public static final String SIGN_SIGN_TYPE_KEY = "SIGN_TYPE";
    /**
     * 签名结果 KEY
     */
    public static final String SIGN_SIGN_KEY = "SIGN";

    /**
     * 资源扫描
     */
    public static final String API_RESOURCE = "cloud_api_resource";

    /**
     * 访问日志
     */
    public static final String API_ACCESS_LOGS = "cloud_api_access_logs";

    /**
     * 登录日志
     */
    public static final String ACCOUNT_LOGS = "cloud_account_logs";

    /**
     * token
     */
    public static final String TOKEN_NAME = "token";
    /**
     * 用户id
     */
    public static final String JWT_KEY_USER_ID = "userid";
    /**
     * 用户名称
     */
    public static final String JWT_KEY_NAME = "name";
    /**
     * 用户账号
     */
    public static final String JWT_KEY_ACCOUNT = "account";

    /**
     * 组织id
     */
    @Deprecated
    public static final String JWT_KEY_ORG_ID = "orgid";
    /**
     * 岗位id
     */
    @Deprecated
    public static final String JWT_KEY_STATION_ID = "stationid";

    /**
     * 租户 编码
     */
    public static final String TENANT = "tenant";
    /**
     * 动态数据库名前缀。  每个项目配置死的
     */
    public static final String DATABASE_NAME = "database_name";

//    /**
//     * 是否boot项目
//     */
//    @Deprecated
//    public static final String IS_BOOT = "boot";

    /**
     * 灰度发布版本号
     */
    public static final String GRAY_VERSION = "grayversion";
    /*
    * 验证码
    * */
    public static final String CAPTCHA = "captcha:";
}
