### feign定义规范
 为了避免参数,返回值问题特做以下约定
 1. 返回值 必须使用 ResultBody进行包裹
 2. 返回值<泛型>.不能使用接口,必须使用具体实现类. 例如:分页 Result<Page<?>> 而不是Result<IPage<?>> 否则fegin无法解码
 3. 参数 @RequestParam(value="a") a 适用于基础类型参数，且必须指明参数名称
 4. 参数 @RequestBody Object obj 注意,服务提供者的Controller的接收参数前也需要加@RequestBody注解
 5. 参数 @RequestParam Map<String, Object> map 较为通用做法
 
 ### feign 异常处理
 查看com.boxiaoyun.autoconfigure.FeignAutoConfiguration.FeignErrorDecoder
 
 #### 示例
 ```
 public interface ISystemActionServiceClient {
 
     /**
      * 获取功能操作列表
      */
     @GetMapping("/action")
     ResultBody<Page<AuthorityAction>> getPage(@RequestParam Map<String, Object> map);
 
     /**
      * 获取功能按钮详情
      *
      * @param actionId
      * @return
      */
     @GetMapping("/action/info")
     ResultBody<AuthorityAction> get(@RequestParam("actionId") Long actionId);
 
     /**
      * 添加/修改功能按钮
      *
      * @param systemAction
      * @return
      */
     @PostMapping("/action/save")
     ResultBody<Long> save(@RequestBody SystemAction systemAction);
 
     /**
      * 移除功能按钮
      *
      * @param actionId
      * @return
      */
     @PostMapping("/action/remove")
     ResultBody remove(@RequestParam("actionId") Long actionId);
 }
 ```
