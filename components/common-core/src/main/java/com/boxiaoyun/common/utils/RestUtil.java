package com.boxiaoyun.common.utils;

import com.boxiaoyun.common.configuration.CommonProperties;
import com.boxiaoyun.common.event.RemoteRefreshRouteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

/**
 * 自定义RestTemplate请求工具类
 *
 * @author:
 * @date: 2018/12/11 15:51
 * @description:
 */
@Slf4j
public class RestUtil {

    private CommonProperties common;
    private ApplicationEventPublisher publisher;
    private BusProperties busProperties;
    private RestTemplate restTemplate;

    public RestUtil(RestTemplate restTemplate, CommonProperties common, BusProperties busProperties, ApplicationEventPublisher publisher) {
        this.common = common;
        this.publisher = publisher;
        this.busProperties = busProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * 构建网关Oauth2 client_credentials方式请求
     *
     * @return
     */
    public OAuth2RestTemplate buildOAuth2ClientRequest() {
        return buildOAuth2ClientRequest(common.getClientId(), common.getClientSecret(), common.getAccessTokenUri());
    }

    /**
     * 构建网关Oauth2 client_credentials方式请求
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUri
     * @return
     */
    public OAuth2RestTemplate buildOAuth2ClientRequest(String clientId, String clientSecret, String accessTokenUri) {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        return restTemplate;
    }

    /**
     * 构建网关Oauth2 password方式请求
     *
     * @return
     */
    public OAuth2RestTemplate buildOAuth2PasswordRequest(String username, String password) {
        return buildOAuth2PasswordRequest(common.getClientId(), common.getClientSecret(), common.getAccessTokenUri(), username, password);
    }

    /**
     * 构建网关Oauth2 password方式请求
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUri
     * @param username
     * @param password
     * @return
     */
    public OAuth2RestTemplate buildOAuth2PasswordRequest(String clientId, String clientSecret, String accessTokenUri, String username, String password) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setUsername(username);
        resource.setPassword(password);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        resource.setAuthenticationScheme(AuthenticationScheme.form);
        resource.setGrantType("password");
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        return restTemplate;
    }

    public <T> T post(String url, Map<String, String> headers, Map<String, String> params, Class<T> tClass) {
        return exchange(url, HttpMethod.POST, headers, params, tClass);
    }

    public <T> T get(String url, Map<String, String> headers, Map<String, String> params, Class<T> tClass) {
        return exchange(url, HttpMethod.GET, headers, params, tClass);
    }

    public <T> T exchange(String url, HttpMethod httpMethod, Map<String, String> headers, Map<String, String> params, Class<T> tClass) {
        //header信息，包括了http basic认证信息
        MultiValueMap<String, String> headersMap = new LinkedMultiValueMap<>();
        if (headers != null) {
            headersMap.setAll(headers);
        }
        //body请求体部分
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
        if (params != null) {
            bodyMap.setAll(params);
        }
        //merge成为一个HttpEntity
        HttpEntity<MultiValueMap<String, String>> multiValueMapHttpEntity = new HttpEntity<>(bodyMap, headersMap);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, multiValueMapHttpEntity, tClass);
        return responseEntity.getBody();
    }

    /**
     * 上传文件到文件系统
     *
     * @param file
     * @return
     */
    public <T> T upload(File file, String uploadUrl, Class<T> tClass) {
        if (file != null && !file.exists()) {
            return null;
        }
        //将文件传入文件管理系统
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        ResponseEntity<T> responseEntity = restTemplate.postForEntity(uploadUrl, param, tClass);
        //删除本地文件
        file.delete();
        return responseEntity.getBody();
    }


    /**
     * 刷新网关
     * 注:不要频繁调用!
     * 1.资源权限发生变化时可以调用
     * 2.流量限制变化时可以调用
     * 3.IP访问发生变化时可以调用
     * 4.智能路由发生变化时可以调用
     */
    public void refreshGateway() {
        try {
            publisher.publishEvent(new RemoteRefreshRouteEvent(this, busProperties.getId(), null));
            log.info("refreshGateway:success");
        } catch (Exception e) {
            log.error("refreshGateway error:{}", e.getMessage());
        }
    }

    public CommonProperties getCommon() {
        return common;
    }

    public void setCommon(CommonProperties common) {
        this.common = common;
    }
}
