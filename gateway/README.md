### 网关模块
+ API网关(api-gateway-server|api-zuul-server)  
  管理的需要对外开放、需要认证授权的资源服务。例如：APP接口服务,提供给第三方调用的接口服务
  
#### 开放API网关(api-gateway-server|api-zuul-server)

+ 接口鉴权   access-control: true
+ 参数签名验证  check-sign: true
+ 在线调试文档 api-debug: true

#### 网关请求标识（X-Request-Id） 
每次请求生成唯一ID,加入请求头X-Request-Id:4a8f266a-a655-40a3-b5fc-b97d64c4404b , 并向下游服务传递。
并写入响应请求头，方便查看与追踪

#### 修改网关启动参数
```
cloud:
  # 开放api
  api:
    # 参数签名验证
    check-sign: true
    # 访问权限控制
    access-control: true
    # swagger调试,生产环境设为false
    api-debug: true
    # 始终放行
    permit-all:
      - /*/login/**
      - /*/logout/**
      - /*/oauth/**
      - /actuator/health
      - /actuator/info
    # 忽略权限鉴定
    authority-ignores:
      - /*/authority/granted/me
      - /*/authority/granted/me/menu
      - /*/current/user/**
    # 签名忽略
    sign-ignores:
      - /**/login/**
      - /**/logout/**
```

#### 网关认证方式
添加请求头Headers  
Authorization  =  Bearer {access_token} 

#### OAuth2获取access_token
<a target="_blank" href="https://gitee.com//open-cloud/wikis/pages?sort_id=1396294&doc_id=256893">OAuth2</a>

#### 参数签名支持表单方式
+ application/json || application/json;charset=UTF-8
+ application/x-www-form-urlencoded
+ multipart/form-data (签名验证暂不支持)

#### 签名公共参数
参数 | 说明 | 必填 | 类型 | 示例值 | 描述
----|------|-----|------|------|------
ApiKey | 应用API_KEY | 是 | String | 1552274783265 | 应用管理中获取
Nonce | 随机字符串  | 是 | String | nonPqUbYzl1yBpr1 | 随机字符串，不长于32位 
Timestamp | 时间戳  | 是 | String | 20190824015104 | 当前的时间:yyyyMMddHHmmss
SignType | 签名类型  | 是 | String | SHA256 | -  默认值为：SHA256，支持 SHA256 和 MD5
Sign | 签名  | 是 | String | 88dfa60c8a03ba52f305c37863eef635151258a72a3f28a14561de3ba61f66be | 签名规则

#### 签名规则
第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
```
  // 参数拼接方式 参数名按顺序 排序
  String  params ="ApiKey=1552274783265&Nonce=lcuwdteo5p0uyuzdw6vxz85mi&SignType=SHA256&Timestamp=20190824023746"; 
```
第二步：拼接API密钥： 
```
  // secretKey 为应用秘钥
  String temp = params + "&SecretKey=0osTIhce7uPvDKHz6aa67bhCukaKoYl4";
  // 生成签名,转大写
  String sign = md5(temp).toUpperCase(); 
  String sign = sha256(temp).toUpperCase(); 
  // SHA256签名结果
  // 88DFA60C8A03BA52F305C37863EEF635151258A72A3F28A14561DE3BA61F66BE 
```
