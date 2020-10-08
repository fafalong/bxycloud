package com.boxiaoyun.site.service.impl;

import com.boxiaoyun.common.model.ResultBody;
import com.boxiaoyun.common.security.BaseUserDetails;
import com.boxiaoyun.common.security.oauth2.SocialProperties;
import com.boxiaoyun.common.utils.WebUtil;
import com.boxiaoyun.site.service.feign.SystemDeveloperServiceClient;
import com.boxiaoyun.system.client.constants.SystemConstants;
import com.boxiaoyun.system.client.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Security用户信息获取实现类
 *
 * @author
 */
@Slf4j
@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemDeveloperServiceClient systemDeveloperServiceClient;
    @Autowired
    private SocialProperties clientProperties;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        HttpServletRequest request =  WebUtil.getHttpServletRequest();;
        Map<String, String> parameterMap = WebUtil.getParamMap(request);
        // 第三方登录标识
        String thirdParty = parameterMap.get("thirdParty");
        ResultBody<UserInfo> resp = systemDeveloperServiceClient.login(username,thirdParty);
        UserInfo account = resp.getData();
        if (account == null || account.getAccountId()==null) {
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }
        String domain = account.getDomain();
        Long accountId = account.getAccountId();
        Long userId = account.getUserId();
        String password = account.getPassword();
        String nickName = account.getNickName();
        String avatar = account.getAvatar();
        String accountType = account.getAccountType();
        boolean accountNonLocked = account.getStatus().intValue() != SystemConstants.ACCOUNT_STATUS_LOCKED;
        boolean credentialsNonExpired = true;
        boolean enabled = account.getStatus().intValue() == SystemConstants.ACCOUNT_STATUS_NORMAL ? true : false;
        boolean accountNonExpired = true;
        BaseUserDetails userDetails = new BaseUserDetails();
        userDetails.setDomain(domain);
        userDetails.setAccountId(accountId);
        userDetails.setUserId(userId);
        userDetails.setUsername(username);
        userDetails.setPassword(password);
        userDetails.setNickName(nickName);
        userDetails.setAuthorities(account.getAuthorities());
        userDetails.setAvatar(avatar);
        userDetails.setAccountId(accountId);
        userDetails.setAccountNonLocked(accountNonLocked);
        userDetails.setAccountNonExpired(accountNonExpired);
        userDetails.setAccountType(accountType);
        userDetails.setCredentialsNonExpired(credentialsNonExpired);
        userDetails.setEnabled(enabled);
        userDetails.setClientId(clientProperties.getClient().get("portal").getClientId());
        return userDetails;
    }
}
