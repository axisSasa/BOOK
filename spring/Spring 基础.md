## Spring 基础

### 1. 定义及用途

### 2. 相对同类产品优势

### 3. 核心知识点

#### 3.1. Spring 事务管理

##### 3.1.1. 事务分类

* 局部事务 & 全局事务
  * 局部事务：特定于一个单一的事务资源，如一个 JDBC 连接
  * 全局事务：
    * 可以跨多个事务资源事务，如在一个分布式系统中的事务
    * 需要在分布式计算环境中，所有的资源都分布在多个系统中
    * 事务管理需要同时在局部和全局范围内进行
* 编程式事务 & 声明式事务
  * 编程式事务：在编程的帮助下管理事务，需要手动编程实现事务
  * 声明式事务：从业务代码中分离事务管理，使用注解或XML配置来管理事务

---

##### 3.1.2. Spring 事务抽象

###### 3.1.2.1. 主要接口

* PlatformTransactionManager接口
  * 作用：获取/创建事务，控制事务提交/回滚
  * getTransaction(TransactionDefinition):根据指定的传播行为，该方法返回当前活动事务或创建一个新的事务
  * commit(): 提交事务
  * rollback():事务回滚
* TransactionDefinition接口
  * 作用：获取事务定义信息
  * getPropagationBehavior()：返回传播行为
  * getIsolationLevel()：返回事务隔离级别
  * getName() ：返回事务名
* TransactionStatus接口
  * 作用：控制事务的执行 和查询事务状态
  * setRollbackOnly(): 设置该事务为 rollback-only 标记
  * isRollbackOnly(): 返回该事务是否已标记为 rollback-only
  * isNewTransaction: 当前事务是新的，返回true
  * isCompleted: 返回该事务是否完成;是否已经提交或回滚
  * hasSavepoint: 返回该事务内部是否有一个保存点
---

###### 3.1.2.2.  事务隔离级别

  * 脏读 = 误读；虚读 = 幻读
  * **ISOLATION_DEFAULT**
    * 默认隔离级别
  * **ISOLATION_READ_UNCOMMITTED**
    - 可以发生误读、不可重复读和虚读
  * **ISOLATION_READ_COMMITTED** 
    * 能够阻止误读；可以发生不可重复读和虚读
  * **ISOLATION_REPEATABLE_READ**
    * 能够阻止误读和不可重复读；可以发生虚读
  * **ISOLATION_SERIALIZABLE**
    * 能够阻止误读、不可重复读和虚读
---

###### 3.1.2.3. 传播类型的可能值

  * **PROPAGATION_MANDATORY**
    * 强制；支持当前事务；如果不存在当前事务，则抛出一个异常
  * **PROPAGATION_NEVER**
    - 从不；不支持当前事务；如果存在当前事务，则抛出一个异常
  * **PROPAGATION_NESTED**
    * 嵌套；如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作
  * **PROPAGATION_REQUIRED**
    * 需要；支持当前事务；如果不存在事务，则创建一个新的事务。
  * **PROPAGATION_REQUIRES_NEW**
    * 需要新的；创建一个新事务，如果存在一个事务，则把当前事务挂起
  * **PROPAGATION_SUPPORTS**
    * 支持；支持当前事务；如果当前没有事务，就以非事务方式执行
  * **PROPAGATION_NOT_SUPPORTED**
    - 不支持；不支持当前事务；以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
  * **TIMEOUT_DEFAULT**
    - 超时默认；使用默认超时的底层事务系统，或者如果不支持超时则没有

---

##### 3.1.3. spring 编程式事务管理

* 作用：允许你在对你的源代码编程的帮助下管理事务
* 编程流程：
  * 获取事务管理器
    * 获取一个PlatformTransactionManager类型的对象
    * PlatformTransactionManager transactionManager
  * 获取事务定义
    * 获取一个TransactionDefinition类型的对象
    * `TransactionDefinition def = new DefaultTransactionDefinition();`
  * 获取一个TransactionStatus类型的对象
    * `TransactionStatus status = transactionManager.getTransaction(def);`
  * DML操作
  * 事务管理器执行提交或回滚操作
    * `transactionManager.commit/rollback(status);`

---

##### 3.1.4. spring 声明式事务管理

* 作用：使用配置，而不是源代码硬编程来管理事务

* 实现方式

  * 通过使用Spring的<tx:advice>定义事务通知与AOP相关配置实现

    * 需要定义切入点

      ```
      <aop:config>
            <aop:pointcut id="createOperation" 
            expression="execution表达式"/>
            <aop:advisor advice-ref="txAdvice" pointcut-ref="createOperation"/>
         </aop:config>
      ```

    * 需要定义事务通知

      ```
      <tx:advice id="txAdvice"  transaction-manager="XXX">
            <tx:attributes>
                <tx:method name="create"/>
            </tx:attributes>
         </tx:advice>
         解析：这里匹配name的方法都会被事务管理
         传播方式，隔离性，超时设定等，配置方式同name属性
      ```

  * 通过@Transactional实现事务管理实现

    * 配置
      * `<tx:annotation-driven transaction-manager="事务管理器"/> //开启事务注解`
    * 实质：
      * @Transaction注解事务管理，内部是利用环绕通知实现事务的开启及关闭
    * 优先级：
      * 在接口、实现类或方法上都指定了@Transactional 注解，则优先级顺序为方法>实现类>接口

---

#### 3.2. spring 体系结构

##### 3.2.1. 核心容器

* 英文：core container

* 组成：spring-core，spring-beans，spring-context，spring-context-support和spring-expression等
* core: 供了框架的基本组成部分，包括 IoC 和依赖注入功能
* beans:
* context: Context模块继承自Bean模块，并且添加了国际化（比如，使用资源束）、事件传播、资源加载和透明地创建上下文（比如，通过Servelet容器）等功能
* spring-context-support: 供了对第三方库集成到Spring上下文的支持,如：缓存，邮件，调度，模板引擎等库类
* spring-expression：提供表达式语言

---

##### 3.2.2. 数据访问/集成

* 英文：Data Access/Integration
* 组成：JDBC，ORM，OXM，JMS 和事务处理模块
* JDBC：
  * Java Data Base Connectivity
  * 提供JDBC抽象层，它消除了冗长的JDBC编码和对数据库供应商特定错误代码的解析
* ORM：
  * Object Relational Mapping
  * 提供了对流行的对象关系映射API的集成，如JPA,Hibernate等
* OXM：
  * Object XML Mapping
  * 提供了对OXM实现的支持，比如JAXB
* JMS：
  * Java Message Service
  * 包含生产（produce）和消费（consume）消息的功能
* **事务**模块：
  * 为实现特殊接口类及所有的 POJO 支持编程式和声明式事务管理

---

##### 3.2.3. Web

* 组成：Web，Web-MVC，Web-Socket 和 Web-Portlet
* Web：
  * 作用：提供面向web的基本功能和面向web的应用上下文；
  * 功能举例：多部分（multipart）文件上传功能、使用Servlet监听器初始化IoC容器等
* Web-MVC：
  * 作用：为web应用提供了模型视图控制（MVC）和REST Web服务的实现
* Web-Socket ：
  * 作用：为 WebSocket-based 提供了支持；在 web 应用程序中提供了客户端和服务器端之间通信的两种方式
  * 可用于实现消息的实时推送
* Web-Portlet：
  * 作用：提供了用于Portlet环境的MVC实现
* web-servlet：
  * 作用：提供了web应用的model-view-controller(MVC)实现。
  * spring mvc框架提供了基于注解的请求资源注入、更简单的数据绑定、数据验证等及一套非常易用的JSP标签，完全无缝与spring其他技术协作。

---

##### 3.2.4. AOP & 测试

* AOP
  * 作用：提供了面向切面的编程实现
* Aspects
  * 提供了与 **AspectJ** 的集成；**AspectJ**也是一个面向切面编程（AOP）框架
* Instrumentation
  * 在一定的应用服务器中提供了类 instrumentation 的支持和类加载器的实现【不太明白】
* 测试
  * 支持对具有 JUnit 或 TestNG 框架的 Spring 组件的测试

### 4. 常见面试题

* REQUIRED,REQUIRES_NEW,NESTED异同
  * NESTED和REQUIRED修饰的内部方法都属于外围方法事务，如果外围方法抛出异常，这两种方法的事务都会被回滚。
  * 但是REQUIRED是加入外围方法事务，所以和外围事务同属于一个事务，一旦REQUIRED事务抛出异常被回滚，外围方法事务也将被回滚。而NESTED是外围方法的子事务，有单独的保存点，所以NESTED方法抛出异常被回滚，不会影响到外围方法的事务。
  * NESTED和REQUIRES_NEW都可以做到内部方法事务回滚而不影响外围方法事务。
  * 但是因为NESTED是嵌套事务，所以外围方法回滚之后，作为外围方法事务的子事务也会被回滚。
  * 而REQUIRES_NEW是通过开启新的事务实现的，内部事务和外围事务是两个事务，外围事务回滚不会影响内部事务。

### 5. 综述

### 6. 参考资料

* [Spring 事务管理](https://www.w3cschool.cn/wkspring/by1r1ha2.html)
* [Spring事务传播行为详解](https://segmentfault.com/a/1190000013341344)
* [Spring事务管理实现方式之编程式事务与声明式事务详解](https://blog.csdn.net/liaohaojian/article/details/70139151)
* [Spring 体系结构](https://www.w3cschool.cn/wkspring/dcu91icn.html)