package com.boxiaoyun.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author LYD
 */
@Slf4j
public class ServerConfiguration implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    private String hostAddress;

    private String contextPath;

    public String getHostAddress() {
        return hostAddress;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getUrl() {
        return "http://" + this.hostAddress + ":" + this.serverPort + this.contextPath;
    }

    public int getPort() {
        return this.serverPort;
    }


    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        ServerProperties serverProperties = event.getApplicationContext().getBean(ServerProperties.class);
        this.serverPort = event.getWebServer().getPort();
        this.contextPath = serverProperties.getServlet().getContextPath()!=null?serverProperties.getServlet().getContextPath():"";
        try {
            InetAddress address = InetAddress.getLocalHost();
            this.hostAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        log.info("hostAddress[{}] serverPort[{}] contextPath[{}]",hostAddress,serverPort,contextPath);
    }

}
