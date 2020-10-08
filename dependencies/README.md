### jar包版本定义
+ cloud-dependencies 所有jar版本管理
+ cloud-clients-dependencies 业务组件jar版本管理
管理项目中jar版本. 子项目无需关心jar包版本
#### parent项目pom中配置 
```
    <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>com.boxiaoyun</groupId>
                    <artifactId>cloud-dependencies</artifactId>
                    <version>1.0.0</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
     </dependencyManagement>
```

#### 子项目使用
```
    <dependencies>
        <dependency>
            <groupId>com.boxiaoyun</groupId>
            <artifactId>common-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.boxiaoyun</groupId>
            <artifactId>system-client</artifactId>
        </dependency>
    </dependencies>
```
