## Spring MVC

### 1. 定义及用途

* Spring Web MVC is the original web framework built on the Servlet API and included in the Spring Framework from the very beginning. The formal name "Spring Web MVC" comes from the name of its source module [spring-webmvc](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc)but it is more commonly known as "Spring MVC".
* The `DispatcherServlet`, as any `Servlet`, needs to be declared and mapped according to the Servlet specification using Java configuration or in `web.xml`. In turn the `DispatcherServlet` uses Spring configuration to discover the delegate components it needs for request mapping, view resolution, exception handling, [and more](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-servlet-special-bean-types).

### 2. 相对同类产品优势

### 3. 核心知识点

#### 3.1. 使用Java配置替换xml配置

* pom中配置maven-war-plugin 插件
  * 删除web.xml后 ，避免Maven构建war包失败
* 建立配置类-替换DispatcherServlet.xml - web mvc config
  * 以@Configuration注解配置类
  * 以@EnableWebMvc注解配置类
    * 等同于<mvc:annotation-driven />
    * 为@RequestMapping 和 @Controller服务
    * 自动注册DefaultAnnotationHandlerMapping与AnnotationMethodHandlerAdapter 两个bean，是spring MVC为@Controllers分发请求所必须的
    * 提供了：数据绑定支持，@NumberFormatannotation支持，@DateTimeFormat支持，@Valid支持，读写XML的支持（JAXB），读写JSON的支持（Jackson）
  * 以@ComponentScan(basePackages = "XXXX")注解配置类
    * 等同于< context:component-scan base-package="...">
    * 告诉spring容器去哪里寻找要管理的bean
  * @Bean定义ViewResolver，添加视图解析器
* 建立初始化类-替换web.xml-servlet config
  * 实现WebApplicationInitializer接口
    * `WebApplicationInitializer` is an interface provided by Spring MVC that ensures your implementation is detected and automatically used to initialize any Servlet 3 container
  * or 继承AbstractAnnotationConfigDispatcherServletInitializer类
    * getServletMappings()方法，等同于<servlet-mapping><url-pattern>
    * getRootConfigClasses()方法，返回配置类

---

#### 3.2. DispatcherServlet

* Spring MVC框架，与其他很多web的MVC框架一样：请求驱动；所有设计都围绕着一个中央Servlet来展开，它负责把所有请求分发到控制器；同时提供其他web应用开发所需要的功能

##### 3.2.1 WebApplicationContext

  * 继承自`ApplicationContext`
  * 支持主题的解析
  * 持有一个自己关联到的servlet的引用：`ServletContext`引用
  * 可以使用Spring提供的工具类取出上下文对象：WebApplicationContextUtils.getWebApplicationContext(ServletContext);
  * 父子上下文：
    * 父上下文：Spring会创建一个WebApplicationContext上下文，称为父上下文（父容器） ，保存在 ServletContext中,key为WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
    * 子上下文：DispatcherServlet是一个Servlet,可以同时配置多个，每个 DispatcherServlet有一个自己的上下文对象（WebApplicationContext），称为子上下文（子容器）
    * 子上下文可以访问父上下文中的内容，但父上下文不能访问子上下文中的内容
    * 自上下文保存在 ServletContext中，key是"org.springframework.web.servlet.FrameworkServlet. CONTEXT" + Servlet名称
    * 当一个Request对象产生时，会把这个子上下文对象（WebApplicationContext）保存在Request对象中，key是DispatcherServlet.class.getName() + ".CONTEXT"。
    * 可以使用工具类取出子上下文对象：RequestContextUtils.getWebApplicationContext(request);

---

##### 3.2.2 Special Bean Types

  * `DispatcherServlet`使用了特殊的bean来处理请求、渲染视图等，这些特定的bean是Spring MVC框架的一部分，是默认使用的bean，可根据需要替换
  * 包含：HandlerMapping，HandlerAdapter，HandlerExceptionResolver，ViewResolver，MultipartResolver等
  * MultipartResolver：解析multi-part的传输请求，比如支持通过HTML表单进行的文件上传等
  * HandlerMapping：处理器映射。它会根据某些规则将进入容器的请求映射到具体的处理器以及一系列前处理器和后处理器（即处理器拦截器）上
  * HandlerAdapter：处理器适配器。拿到请求所对应的处理器后，适配器将负责去调用该处理器

---

##### 3.2.3 DispatcherServlet处理流程

  * 搜索WebApplicationContext并将其绑定到请求request的一个属性，这样方便控制器controller等使用；绑定的时候属性默认的key为DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE
  * 将地区（locale）解析器绑定到请求上
  * 将主题（theme）解析器绑定到请求上
  * 如果你配置了multipart文件处理器，那么框架将查找该文件是不是multipart（分为多个部分连续上传）的。若是，则将该请求包装成一个`MultipartHttpServletRequest`对象，以便处理链中的其他组件对它做进一步的处理
  * 为该请求查找一个合适的处理器。如果可以找到对应的处理器，则与该处理器关联的整条执行链（前处理器、后处理器、控制器等）都会被执行
  * 如果处理器返回的是一个模型（model），那么框架将渲染相应的视图。若没有返回任何模型（可能是因为前后的处理器出于某些原因拦截了请求等，比如，安全问题），则框架不会渲染任何视图，此时认为对请求的处理可能已经由处理链完成了
  * 如果在处理请求的过程中抛出了异常，那么上下文`WebApplicationContext`对象中所定义的异常处理器将会负责捕获这些异常

---

##### 3.2.4 Interception拦截

  * 每个HandlerMapping可以有自己的拦截器
  * <mvc:interceptors/>会为每一个HandlerMapping，注入一个拦截器
    * 如果是REST风格的URL，静态资源也会被拦截
    * 在HandlerMapping的bean中写拦截器就不会拦截静态资源，但使用<mvc:interceptors>就没有机会指定拦截器了，
  * 拦截器在什么时候执行？
    * 一个请求交给一个HandlerMapping时，这个HandlerMapping先找有没有处理器来处理这个请求，如何找到了，就执行拦截器，执行完拦截后，交给目标处理器
    * 如果没有找到处理器，那么这个拦截器就不会被执行
  * 拦截器必须实现HandlerInterceptor，实现下面三个方法
    * preHandle()
      * 该方法在真实处理器执行前执行
      * 可以进行编码、安全控制等处理
      * 该方法返回一个布尔值
      * 返回true，则处理器的执行链继续执行
      * 返回false,不再继续向下执行
    * postHandle()
      * 该方法在真实处理器执行后执行/调用了Service并返回ModelAndView，但未进行页面渲染
      * 有机会修改ModelAndView
      * 很少使用，响应response在HandlerAdapter执行期间就确定了，这是在postHandle方法调用前发生的
    * afterCompletion()
      * 该方法在请求执行完成后执行/已经渲染了页面，可用于释放资源

---

#### 3.3. 控制器

* 控制器作为应用程序逻辑的处理入口，它会负责去调用你已经实现的一些服务。通常，一个控制器会接收并解析用户的请求，然后把它转换成一个模型交给视图，由视图渲染出页面最终呈现给用户
* `@Controller`注解
  * 表明了一个类是作为控制器的角色而存在的
  * 分派器（`DispatcherServlet`）会扫描所有注解了`@Controller`的类，检测其中通过`@RequestMapping`注解配置的方法
  * 需要在配置中加入组件扫描的配置代码来开启框架对注解控制器的自动检测
* `@RequestMapping`注解
  * 将请求URL，如`/appointments`等，映射到整个类上或某个特定的处理器方法上
  * URI模板：
    * 一个类似于URI的字符串，只不过其中包含了一个或多个的变量名
    * 在Spring MVC中你可以在方法参数上使用`@PathVariable`注解，将其与URI模板中的参数绑定起来
    * 举例：`RequestMapping(path="/owners/{ownerId}" ` `@PathVariable String ownerId,`
  * 后缀模式匹配
    * Spring MVC默认采用`".*"`的后缀模式匹配来进行路径匹配，
    * 一个映射到`/person`路径的控制器也会隐式地被映射到`/person.*`
    * RFD(Reflected file download)攻击:禁用后缀模式匹配是防范RFD攻击的有效方式
* 控制器的返回值
  * 返回ModelAndView
    * 需要方法结束时，定义ModelAndView，将model和view分别进行设置
  * 返回String
    * 表示返回逻辑视图名。真正视图(jsp路径)=前缀+逻辑视图名+后缀
    * redirect重定向：返回字符串格式为："redirect:queryItem.action"
    * forward页面转发：返回字符串格式为：“forward:queryItem.action”
  * 返回void
    * 在controller方法形参上可以定义request和response，使用request或response指定响应结果
    * 使用request转向页面，如下：request.getRequestDispatcher("页面路径").forward(request, response)
    * 通过response页面重定向：response.sendRedirect("url")
    * 通过response指定响应结果，例如响应json数据:response.getWriter().write("json串");

---

#### 3.4. 处理映射



---

#### 3.5. Spring MVC的请求流程

    第一步：发起请求到**前端控制器**(DispatcherServlet)

    第二步：前端控制器请求**HandlerMapping**查找Handler可以根据xml配置、注解进行查找

    第三步：处理器映射器HandlerMapping向前端控制器返回**Handler**

    第四步：前端控制器调用**处理器适配器**去执行Handler

    第五步：处理器适配器去执行Handler

    第六步：Handler执行完成给适配器返回**ModelAndView**

    第七步：处理器适配器向前端控制器返回ModelAndView。ModelAndView是springmvc框架的一个底层对象，包括 Model和view

    第八步：前端控制器请求**视图解析器**去进行视图解析，根据逻辑视图名解析成真正的视图(jsp)

    第九步：视图解析器向前端控制器**返回View**

    第十步：前端控制器进行**视图渲染**。视图渲染将模型数据(在ModelAndView对象中)填充到request域

    第十一步：前端控制器向用户响应结果

---

#### 3.6. 视图解析器

* 视图：
  * 作用：渲染模型数据，将模型里的数据以某种形式呈现给客户
  * 举例:InternalResourceView：将jsp封装为一个视图
* 视图解析器：
  * 作用：将逻辑视图转为物理视图，所有的视图解析器都必须实现ViewResolver接口
  * 举例：InternalResourceViewResolver：用于将提供的URI解析为实际URI
* SpringMVC的视图解析流程
  * 调用目标方法，SpringMVC将目标方法返回的String、View、ModelMap或是ModelAndView都转换为一个ModelAndView对象
  * 通过视图解析器（ViewResolver）对ModelAndView对象中的View对象进行解析，将该逻辑视图View对象解析为一个物理视图View对象
  * 调用物理视图View对象的render()方法进行视图渲染，得到响应结果

---

#### 3.7. 集成其他服务

##### 3.7.1. 生成XML



---

##### 3.7.2. 生成JSON

---

##### 3.7.3. 生成Excel

---

##### 3.7.4. 生成PDF

---

##### 3.7.5. 集成Log4j

---

##### 3.7.6. 集成Hibernate验证器

---

#### 3.8 处理文件上传

---



### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [Spring MVC教程](https://www.yiibai.com/spring_mvc/)
* spring mvc 官方文档
* [深入理解Spring MVC 思想](https://www.cnblogs.com/baiduligang/p/4247164.html)
* [Spring MVC 和 Spring 总结](https://www.cnblogs.com/doudouxiaoye/p/5693399.html)
* [SpringMVC中的视图和视图解析器](https://blog.csdn.net/xiangwanpeng/article/details/53144002)