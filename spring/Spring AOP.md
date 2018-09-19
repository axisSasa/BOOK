## Spring AOP

### 1. 定义及用途

* AOP意为面向切面编程，是通过预编译方式和运行期动态代理来实现程序功能的统一维护的技术。

* Spring AOP模块会提供截取拦截应用程序的拦截器，例如，当执行方法时，可以在执行方法之前或之后添加额外的功能。

* **Spring中AOP代理由Spring的IOC容器负责生成、管理，其依赖关系也由IOC容器负责管理**。因此，AOP代理可以直接使用容器中的其它bean实例作为目标，这种关系可由IOC容器的依赖注入提供。Spring创建代理的规则为：

  1、**默认使用Java动态代理来创建AOP代理**，这样就可以为任何接口实例创建代理了

  2、**当需要代理的类不是代理接口的时候，Spring会切换为使用CGLIB代理**，也可强制使用CGLIB

* 主要运用的地方：日志记录、性能统计、安全控制、事务处理、异常处理等。

### 2. 相对同类产品优势

* Aspect-Oriented Programming (AOP) complements Object-Oriented Programming (OOP) by providing another way of thinking about program structure.
*  The key unit of modularity in OOP is the class, whereas in AOP the unit of modularity is the aspect. Aspects enable the modularization of concerns such as transaction management that cut across multiple types and objects. (Such concerns are often termed crosscutting concerns in AOP literature.)

### 3. 核心知识点

#### 3.1. Spring AOP 核心概念

* 横切关注点
  * 对哪些方法【地方】进行拦截，拦截后怎么处理，这些关注点称之为横切关注点

* Aspect/切面
  * 类是对物体特征的抽象，切面就是对横切关注点的抽象
* Join point/加入点/连接点
  * 被拦截到的点，因为Spring只支持方法类型的连接点，所以在Spring中连接点指的就是被拦截到的方法，实际上连接点还可以是字段或者构造器
  * 代表应用程序中可以插入AOP切面的一个点/位置
* pointcut/切入点
  * 对连接点进行拦截的定义
  * 匹配连接点的断言
* advice/通知
  * 所谓通知指的就是指拦截到连接点之后要执行的代码，通知分为前置、后置、异常、最终、环绕通知五类
* Target object/目标对象
  * 被一个或多个切面通知(`Advice`)，该对象将始终是代理的对象。也称为通知(`Advice`)对象
* weave/织入
  * 将切面应用到目标对象并导致代理对象创建的过程
  * 把切面连接到其他的应用程序类型或者对象上，并创建一个被通知的对象
  * 分为：编译时织入、类加载时织入、执行时织入
* introduction/引入
  * 在不修改代码的前提下，引入可以在**运行期**为类动态地添加一些方法或字段

---

#### 3.2. Spring AOP 通知类型

* before advice/前置通知
  * 在某个连接点（join point）之前执行的通知，但不能阻止连接点的执行（除非它抛出异常）
* after advice/ finally advice/后置通知
  - 当某个连接点退出的时候执行的通知（无论是正常返回还是异常退出）
* after returning advice/返回后通知
  * 在某个连接点（join point）正常完成后执行的通知
  * 只有方法成功完成后才能在方法执行后运行通知
* after throwing advice/抛出异常后通知
  * 只有在方法通过抛出异常而退出方法执行之后才能运行通知
* around advice/环绕通知
  * 包围一个连接点（join point）的通知
*  spring的环绕通知 和 前置通知&后置通知的区别
  * 目标方法的调用由环绕通知决定，即你可以决定是否调用目标方法，而前置和后置通知   是不能决定的，他们只是在方法的调用前后执行通知而已，即目标方法肯定是要执行的
  * 环绕通知可以控制返回对象，即你可以返回一个与目标对象完全不同的返回值，虽然这很危险，但是你却可以办到。而后置方法是无法办到的，因为他是在目标方法返回值后调用
  * 环绕通知的执行的advice方法的第一个参数的类型必须为：ProceedingJoinPoint
* spring 通知的处理顺序：
  * spring 处理顺序是按照xml配置顺序依次处理通知，以队列的方式存放前置通知，以压栈的方式存放后置通知。
  * 所以是前置通知依次执行，后置通知到切入点执行完之后，从栈里 在后进先出的形式把后通知执行。

---

#### 3.3. 基于注解的Spring AOP

##### 3.3.0. 切入点

* 使用@Pointcut()定义切入点，可以使用表达式或模式指定切入点

* @Pointcut()的参数匹配的方法就是切入点关联到的连接点，

* 针对这个切入点设置的通知将会被应用到关联的连接点上

* 配置切入点;pointcut表达式：

  * 语法结构： execution(   方法修饰符  方法返回值  方法所属类 匹配方法名 (  方法中的形参表 )  方法申明抛出的异常  )
  * execution(public * *(..))  切入点为所有public方法

  * execution(* set*(..))  切入点为所有set开始的方法

  * execution(* com.xyz.service.AccountService.*(..))  切入点为AccountService类中所有方法

  * execution(* com.xyz.service..(..))  切入点为com.xyz.service包下的所有方法

  * execution(* com.xyz.service...(..))  切入点为com.xyz.service包及其子包下的所有方法

  * within(com.xyz.service.*) (only in Spring AOP) 在service包中的任意连接点

  * within(com.xyz.service..*) (only in Spring AOP) 在service包或其子包中的任意连接点

  * this(com.xyz.service.AccountService) (only in Spring AOP) 表示匹配了AccountService接口的代理对象的所有连接点
  * target（com.xyz.service.AccountService）实现AccountService接口的目标对象的任意连接点
  * args(java.io.Serializable)   (only in Spring AOP)        args 用于匹配当前执行的方法传入的参数 为指定类型的执行方法

* 几种表达式的区别：

  * execution可以匹配包、类、方法，而within只能匹配包、类
  * execution针对的是方法签名，而args针对的是运行时的实际参数类型。

* 组合切入点：

  * 支持 **&&、 || 、！**
  * &&：要求连接点同时匹配两个切点表达式
  * ||：要求连接点匹配至少一个切入点表达式
  * !：要求连接点不匹配指定的切入点表达式

##### 3.3.1. 前置通知

* 使用`@Before` 
  * 将函数A标记为在 切入点覆盖的方法Bs 之前执行的通知
  * 可以保证函数A在方法Bs前执行

---

##### 3.3.2. 后置通知

* 使用@After(pointcut=pointcut签名/表达式)
  * 将函数标记为在切入点覆盖的方法之后执行的通知

---

##### 3.3.3. 返回通知

* 使用@AfterReturning(pointcut=pointcut签名/表达式, returning=返回变量的名称)
  * 确保方法执行成功后运行通知

---

##### 3.3.4. 抛出异常后通知

* 使用@AfterThrowing(pointcut=pointcut签名/表达式, throwing=返回异常的名称)
  * 确保在方法抛出异常时运行一个通知

---

##### 3.3.5. 环绕通知

* 使用@Around(pointcut=pointcut签名/表达式)
  * 可确保方法执行前后的通知可以运行

---

#### 3.4.  introduction/引入

* 使用目的：对于一个已有的类引入新的接口
* 适用情况：A类复杂，但想在A类中添加新的方法，
  * 编程思路：A就是原目标类，B就是新添加的方法所在的类，通过建立一个代理类同时代理A和B，调用者调用该代理时，就可以同时A和B中的方法了。 
* 对引入的理解：在一个类A不好改变的情况下又想使用新的方法，可以创建一个新类B来实现新方法，再给A**引入** 这个B做兄弟，再让A认B的爸爸做爸爸

### 4. 常见面试题

* 各种通知执行顺序？
  * 目标方法无环绕通知：前置通知--目标方法--后置通知--返回通知
  * 目标方法含环绕通知：环绕通知(目标方法执行前部分)--前置通知--目标方法--环绕通知(目标方法执行后部分)--后置通知--返回通知

### 5. 综述

### 6. 参考资料

* Spring AOP 官方文档

* [Spring3：AOP](https://www.cnblogs.com/xrq730/p/4919025.html)