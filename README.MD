# `JAVAEE`课设

## 为什么使用`SpringBoot`

+ 为所有 `Spring` 开发提供一个更快更广泛的入门体验。

+ 零配置。无冗余代码生成和`XML` 强制配置，遵循“约定大于配置” 。（只有在偏离习惯配置的时候才需要增加配置。典型的就是`java`的注解）

+ 集成了大量常用的第三方库的配置， `Spring Boot` 应用为这些第三方库提供了几乎可以零配置的开箱即用的能力。

+ 提供一系列大型项目常用的非功能性特征，如嵌入式服务器、安全性、度量、运行状况检查、外部化配置等。

+ `Spring Boot` 不是`Spring` 的替代者，`Spring` 框架是通过 `IOC` 机制来管理 `Bean` 的。`Spring Boot` 依赖 `Spring` 框架来管理对象的依赖。`Spring Boot`
  并不是`Spring`
  的精简版本，而是为使用 `Spring` 做好各种产品级准备

## 工作流程

&emsp;&emsp;用户向服务器发送请求，请求会到`DispatcherServlet`，`DispatcherServlet`
对请求URL进行解析，得到请求资源标识符（`URI`），然后根据该`URI`，调用`HandlerMapping`获得该`Handler`配置的所有相关的对象（包括一个`Handler`
处理器对象、多个`HandlerInterceptor`拦截器对象），最后以`HandlerExecutionChain`对象的形式返回.

&emsp;&emsp;`DispatcherServlet` 根据获得的`Handler`，选择一个合适的`HandlerAdapter`。提取`Request`中的模型数据，填充`Handler`入参，开始执行`Handler`
（`Controller`)。 在填充`Handler`的入参过程中，根据你的配置，`Spring`将帮你做一些额外的工作,(如`HttpMessageConverter` 将`JSON`转成一个对象;数据转换;数据格式化)

## 注解相关

### `@AutoWired`

+ 当 `Spring`遇到一个在 `setter` 方法中使用的 `@Autowired` 注释，它会在方法中视图执行 `byType` (搜索整个配置文件中类名为这个的)自动连接。

### `@ResponseBody`

+ `@ResponseBody`是作用在方法上的，`@ResponseBody` 表示该方法的返回结果直接写入 `HTTP response body` 中，一般在异步获取数据时使用【也就是`AJAX`】。 注意：在使用
  `@RequestMapping`后，返回值通常解析为跳转路径，但是加上 `@ResponseBody` 后返回结果不会被解析为跳转路径，而是直接写入 `HTTP response body` 中。 比如异步获取 json 数据，加上
  `@ResponseBody` 后，会直接返回 `json` 数据。`@RequestBody` 将 `HTTP` 请求正文插入方法中，使用适合的 `HttpMessageConverter` 将请求体写入某个对象。
+ 一般作用在`Controller`的方法上

### `@PostMapping`

+ 是`@RequestMapping(method = RequestMethod.POST)`的`alias`
+ 几个重要的参数
  + `headers`与`Content-Type`需一致。 使用例子 `headers = "content-type=text/*"`（具体请求中的媒体类型信息）
  + `@RequestParam`则是指定参数的类型
  + `produces`是指定返回的数据类型。`consumes`是该接口只接受指定的数据类型

### `@Service,@Controller,@Mapper,@Repository`

+ `@Service`与`@Controller`属于`Spring`框架的注解，注解在类上，需要被扫描才能创建实例化对象，并将对象放到Spring容器中。
+ `@Mapper`注解是`Mybatis`框架的注解，使用这个注解我们的接口就可以被`Mybatis`框架加载，然后动态代理生成实体类，然后就将实体类放到了Spring容器中。
+ `@Repository`（实现`dao`访问） 用于标注数据访问层，也可以说用于标注数据访问组件，即`DAO`组件

### `@ResultType`(`MyBatis`标签)

+ `resultType`是`sql`映射文件中定义返回值类型

### `@PostConstruct`

+ `Java`的原生标签，在修饰一个`void`方法时使用被`@PostConstruct`修饰的方法会在服务器加载`Servlet`的时候运行，并且只会被服务器执行一次。`PostConstruct`在`Servlet`
  构造函数之后执行，`init()`方法之前执行。

### `@Results`

+ `MyBatis`中使用`@Results`注解来映射查询结果集到实体类属性。

+ `@Results`的基本用法。当数据库字段名与实体类对应的属性名不一致时，可以使用`@Results`映射来将其对应起来。`column`为数据库字段名，`property`为实体类属性名，`jdbcType`
  为数据库字段数据类型，`id`为是否为主键。`@One`代表查询返回的数据只能是一条否则会报错。`@Many`则是对应一对多查询

### `@Retention`

+ `RetentionPolicy.RUNTIME`：注解不仅被保存到`class`文件中，`jvm`加载`class`文件之后，仍然存在。主要控制注解的生命周期

### `@Target`

+ 主要声明注解所作用的对象。本项目中注解所作用的对象为注解类，类，类上的方法。

### `@Inherit`

+ 子类可以继承父类的注解

### `@Param`

+ 作用:使得`mapper.xml`中的参数与后台的参数对应上

+ 与`@RequestParam`的区别。
+ `@Param`是`MyBatis`的标签主要用于`Dao`层。`@RequestParam`则是用于`Controller`层

### `@Order`

+ 定义组件加载顺序

### `@Blame`

+ 自己实现的注解，用于谴责zky不好好干活的行为

### `@NoAuthority @AuthStudent @AuthAdmin @AuthTeacher`

+ 使用注解，当拦截器检测到权限校验注解的存在时，根据使用的注解与当前的登录状态判断是否应该进行服务

## 跨域

### 为什么会产生跨域

+ 请求协议`http`,`https`的不同
+ 域`domain`的不同
+ 端口`port`的不同

### 如何解决跨域

+ 配置一个过滤器，其中的几个重要参数做说明
+ `Access-Control-Allow-Origin` 就是允许指定的域名跨域。这里采用了直接把接受到的域名作为指定的域名
+ `Access-Control-Allow-Credentials:true` 是否允许后续请求携带认证信息(`cookies`),该值只能是`true`,否则不返回，表示`fetch`是否接收`cookies`和发送`cookies`

## 拦截器

### 为什么要使用拦截器

+ 主要做的是一个权限的认证

### 如何去使用拦截器

#### 实现`HandlerInterceptor`接口的方法

```mermaid
graph TB
  start[开始] --> input[获取当前Handler和role]
  input --> D{这个handler<br>是否为HandlerMethod<br>且其身上的注解不为<br>NoAuthority}
  D --yes-->E{这个handler<br>是否为异常类}
  E --No-->return
  E --yes--> F[根据其身上的注解和role进行比对] 
  F --> G{如果这个role为空}
  G --yes-->返回权限校验失败
  G --No -->进行权限验证 
  
 ```

#### 实现`WebMvcConfigurer`接口并添加拦截器

&emsp;&emsp;`WebMvcConfigurer`配置类其实是Spring内部的一种配置方式，采用`JavaBean`的形式来代替传统的`xml`配置文件形式进行针对框架个性化定制，可以自定义一些`Handler`
，`Interceptor`，`ViewResolver`，`MessageConverter`。基于`java-based`方式的`spring mvc`配置，需要创建一个配置类并实现`WebMvcConfigurer` 接口；

## 前后端分离

### 如何实现前后端分离

&emsp;&emsp;主要是两者之间通过`HTTP`通信，本项目前端采用的是`Vue`后端采用的是`Spring Boot`。前端通过调用后端的`API`来实现增删改查，前端通过使用`fetch`
方法来异步获取后端的资源。需要在`fetch`中指定请求的头和`body`。后端则提供类似`RESTful`风格的`URL`。部署到服务上的时候需要一层`Nginx`反向代理层

## 本项目的一些亮点

+ 安全性较高，对密码进行了加盐处理且存在着大量的权限验证。
+ 良好的用户体验，有着许多模糊查询，用户只需要输入少许的几个字就能获取到所需要的相关信息。并且存在这离线的模糊查询这个机制
+ 完善的邮件服务，在添加账号和重置密码时用户都会收到相应的邮件。
+ 为了模块化代码，并减少大量的冗余。前端中使用了Vue的组件特性。
+ 使用了`async/await`的语法糖，将异步变成同步，明确了函数执行的顺序。且简化了从服务器端获取数据。
+ 在部分地方使用了`Kotlin`。使代码更加简洁，安全。不会出现空指针。
+ 在服务器上部署时使用了`CDN`来提高用户体验。
+ 良好的编程习惯，在代码中加入了`javadoc`标签来使代码可读性更好，使用`junit`对每个`Mapper`和`Controller`分别进行了测试。
+ 专业的开发流程，使用了`git`等工具，并且使用了敏捷开发，提高了开发的效率。
+ 使用了过滤器来处理跨域的请求