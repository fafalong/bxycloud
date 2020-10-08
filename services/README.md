### 微服务模块
+ 服务提供者
+ 无需安全配置类
+ 不支持接口扫描

#### 项目快速搭建
1. 配置pom
```
  <dependencies>
  <!-- 引入所有依赖,可以到父项目dependencyManagement管理 -->
      <dependencies>
              <dependency>
                  <groupId>com.boxiaoyun</groupId>
                  <artifactId>cloud-dependencies</artifactId>
                  <version>1.0.0</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
        </dependencies>
        <!--引入公共jar-->
        <dependency>
            <groupId>com.boxiaoyun</groupId>
            <artifactId>common-starter</artifactId>
        </dependency>
    </dependencies>
```

2. 配置bootstrap.yml
```
server:
    port: 8233
spring:
    application:
        name: ${artifactId}
    cloud:
        sentinel:
            transport:
                dashboard: localhost:8680
            eager: true
        #手动配置Bus id,
        bus:
            id: ${spring.application.name}:${server.port}
        nacos:
            config:
                namespace: ${config.namespace}
                refreshable-dataids: common.properties
                server-addr: ${config.server-addr}
                shared-dataids: common.properties,db.properties,redis.properties,rabbitmq.properties
            discovery:
                namespace: ${config.namespace}
                server-addr: ${discovery.server-addr}
    main:
        allow-bean-definition-overriding: true
    #解决restful 404错误 spring.mvc.throw-exception-if-no-handler-found=true spring.resources.add-mappings=false
    mvc:
        throw-exception-if-no-handler-found: true
    resources:
        add-mappings: false
    profiles:
        active: ${profile.name}
        
management:
    endpoints:
        web:
            exposure:
                include: '*'
                
cloud:
    swagger2:
        enabled: true
        description: 微服务
        title: 微服务

#mybatis plus 设置
mybatis-plus:
 #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.boxiaoyun.base.client.**.entity
  mapper-locations: classpath:mapper/*.xml

```

3. 创建Application.class
```
  @EnableFeignClients
  @EnableDiscoveryClient
  @SpringBootApplication
  @MapperScan(basePackages = "com.boxiaoyun.system.mapper")
  public class Application {
      public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
  }
```

4. 项目创建成功
