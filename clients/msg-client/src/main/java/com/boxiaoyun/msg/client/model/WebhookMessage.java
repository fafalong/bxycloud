package com.boxiaoyun.msg.client.model;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 异步通知消息
 *
 * @author
 */
@ApiModel("异步通知消息")
public class WebhookMessage extends BaseMessage {
    private static final long serialVersionUID = 1566807113989212480L;
    /**
     * 通知回调路径
     */
    @ApiModelProperty("通知地址")
    private String url;
    /**
     * 请求内容
     */
    @ApiModelProperty("通知内容")
    private Map<String, String> content = Maps.newLinkedHashMap();
    /**
     * 通知业务类型
     */
    @ApiModelProperty("通知业务类型")
    private String type;
    @ApiModelProperty("通知标题")
    private String title;
    @ApiModelProperty("业务主键")
    private String key;

    public WebhookMessage() {
        super();
    }

    /**
     * 构建消息
     * @param key 业务逐渐
     * @param title 通知标题
     * @param url     通知地址
     * @param type    通知业务类型
     * @param content 请求数据
     */
    public WebhookMessage(String key, String title, String url, String type, Map<String, String> content) {
        this.key = key;
        this.title = title;
        this.url = url;
        this.content = content;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "WebhookMessage{" +
                "url='" + url + '\'' +
                ", content=" + content +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
