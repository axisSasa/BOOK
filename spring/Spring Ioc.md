## Spring Ioc Container

### 1. 定义及用途

* Ioc - Inversion of Control - 控制反转
  * 获取依赖对象的过程被控制反转了
    * 对于某个具体的对象而言，以前是它由应用程序负责创建&维护依赖，现在是所有对象都被spring容器控制，所以这叫**控制反转**。
    * 对于spring框架来说，就是由spring来负责控制对象的生命周期和对象间的关系
  * 获取依赖对象的过程：
    * 找到Ioc容器；容器返回对象；使用对象。
* DI - Dependency Injection - 依赖注入
  * 注入：完成对相应对象的成员变量的赋值行为
  * 依赖注入：Ioc容器在运行期间，动态的将某种依赖关系注入到对象中，称为依赖注入
  * 用于：实现Ioc
* 用途：创建对象&建立对象间的联系

### 2. 相对同类产品优势

* 

### 3. 核心知识点

#### 3.1. Spring Bean

##### 3.1.0 Java Bean & Spring Bean & POJO

* Java Bean定义：JavaBean只是遵循了特定编码规范的**Java类**
  * 所有属性为private
  * 提供默认构造方法
  * 提供getter和setter
  * 实现serializable接口
* Spring Bean：
  * 定义1：表示受到Spring管理的**对象**
  * 定义2：是被Spring框架容器初始化、配置和管理的对象
  * 定义3：Spring bean是在Spring的配置文件中定义（现在也可以通过annotation注解来定义），在Spring容器中初始化，然后注入到应用程序中的的对象
  * 得名缘由：最初，Spring是被设计用来管理JavaBean的，所以Spring管理的对象被称为“bean”，现在Spring 可以管理任何对象，不止于JavaBean的对象，只是沿用此称呼
* POJO:
  * Plain Ordinary Java Object
  * 普通Java类
  * 具有一部分getter/setter方法,
  * 只能装载数据，作为数据存储的载体，而不具有业务逻辑处理的能力

---

##### 3.1.1. Bean的配置项-简介

* id : Ioc容器中，一个Bean的唯一标识

* class ：当前Bean具体要实例化的哪一个类

* scope ：范围，作用域

* constructor-arg ：构造器参数

* property ：类的属性 `<property name="属性名" value="属性值" />`

* autowiring mode ：自动装配模式

* Lazy-initialization mode ：懒加载模式

* Initialization/destruction method ：初始化和销毁方法

---

##### 3.1.2. Bean的配置项-作用域scope

* 作用：确定哪种类型的 bean 实例应该从Spring容器中返回给调用者
* Spring中bean实例的类型：
  * singleton：单例，指一个Bean容器中只存在一份（**默认**）
  * prototype：每次请求（每次使用）创建新的实例，
    * 特点：destory方式不生效【对于具有prototype作用域的Bean，有一点很重要，即Spring不能对该Bean的整个生命周期负责。具有prototype作用域的Bean创建后交由调用者负责销毁对象回收资源。】
  * request：每次http请求创建一个实例且仅在当前request内生效（只能在web中使用）
  * session：同上，每次http请求创建一个实例，当前session内有效（只能在web中使用）
  * global session：类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义
* singleton 和设计模式的单例模式区别：
  * 标记为singleton的bean是由容器来保证这种类型的bean在同**一个容器** 内只存在一个共享实例，
  * 单例模式则是保证在同**一个Classloader**中只存在一个这种类型的实例
* 默认作用域为singleton，修改作用域3种方式
  * 基于XML:在Bean配置文件中的对应Bean定义中添加`scope="prototype"`
  * 基于注解：
    * 结合@Component使用：直接在类定义的源java文件添加注解@Component 和 @Scope
      * 要使@Component生效，需将类所在包添加到`<context:component-scan base-package="XXX">`中
    * 结合@Bean使用：在Javaconfig文件中的对应Bean添加`@Scope("prototype")`

---

##### 3.1.3. Bean的生命周期

* Bean生命周期约分为4个阶段；
  * 实例化-初始化-使用-销毁

* Bean的实例化阶段
  * bean实例化
    * 3种方式：构造器实例化、静态工厂方式实例化、实例工厂方式实例化
    * 构造器实例化：调用Bean的默认构造方法，生成bean实例
  * 对Bean进行属性和依赖注入
  * 通过实现XXXAwarw接口获取资源
    * 如果某个Bean实现了BeanNameAware接口，那么Spring将会将Bean实例的ID传递给setBeanName()方法，在Bean类中新增一个beanName字段，并实现setBeanName()方法
    * 如果某个Bean实现了BeanFactoryAware接口，那么Spring将会将创建Bean的BeanFactory传递给setBeanFactory()方法，在Bean类中新增了一个beanFactory字段用来保存BeanFactory的值，并实现setBeanFactory()方法
    * 如果某个Bean实现了ApplicationContextAware接口，那么Spring将会将该Bean所在的上下文环境ApplicationContext传递给setApplicationContext()方法，在Bean类中新增一个ApplicationContext字段用来保存ApplicationContext的值，并实现setApplicationContext()方法
* Bean的初始化阶段
  * 进行预初始化处理：**BeanPostProcessor**的postProcessBeforeInitialization()方法
  * 进行初始化：
    * **InitializingBean**的afterPropertiesSet()方法
    * or 自定义的init-method指定的方法
  * 进行初始化后处理：**BeanPostProcessor**的postProcessorAfterInitialization()方法
* Bean的使用
* Bean的销毁阶段
  * 进行销毁前的处理
    * **DisposableBean**的destory()方法：
    * or 自定义的destory-method指定的方法
* 注意点：
  * BeanFactoryPostProcessor接口与BeanPostProcessor接口的作用范围是整个上下文环境中，使用方法是单独新增一个类来实现这些接口，那么在处理其他Bean的某些时刻就会回调响应的接口中的方法
  * XXXAware的作用范围的Bean范围，即仅仅对实现了该接口的指定Bean有效，所有其使用方法是在要使用该功能的Bean自己来实现该接口
  * 单个Bean配置了初始化方法和销毁方法，全局默认的初始化和销毁方法配置就不会生效

---

#### 3.2. Bean自动装配

* Spring auto-wiring 功能会自动装配Bean。要启用它，只需要在 <bean>定义“autowire”属性
* 例：`autowire="byName"`

##### 3.2.1. 5种自动装配模式

- no – **缺省**情况下，自动配置是通过“**ref**”属性手动设定
- byName – 根据**属性名称**自动装配。如果一个bean的名称和其他bean属性的名称是一样的，将会自装配它。
- byType – 按**数据类型**自动装配。如果一个bean的数据类型是用其它bean属性的数据类型，兼容并自动装配它。
- constructor – 在构造函数参数的byType方式。
- autodetect – 如果找到默认的构造函数，使用“自动装配用构造”; 否则，使用“按类型自动装配”

---

##### 3.2.2. 使用@Autowired注解自动装配

* 使用地方：
  * @Autowired可用于 字段 , Setter 方法，构造器
  * 用在字段上，那么就不需要再写setter方法。
* 装配类型：
  * @Autowired注解是按照类型（byType）装配依赖对象,若要按名字(byName)装配，则需配合@Qualifier使用
* 依赖检查：
  * @Autowired会执行相关检查，以确保属性已经装配正常。当Spring无法找到匹配的Bean装配，它会抛出异常
  * @Autowired(required=false)，不执行检查，无法装配不抛异常

---

##### 3.2.3. 使用@Qualifier注解自动装配

* 使用地方：控制bean在字段上自动装配
* 装配类型：辅助@Autowired使用，在有多个可装配对象的时候，根据@Qualifier确定选取特定名字的bean

---

##### 3.2.4.使用@Resource注解自动装配

* 来源：@Resource由J2EE提供，需要导入包javax.annotation.Resource
* 使用地方：字段 or setter方法(推荐)
* 装配类型：
  * 默认按照ByName自动注入
  * @Resource有两个重要的属性：name和type；如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略
* 依赖检查：
  * 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
  * 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
  * 如果指定了type，则从上下文中找到类似匹配的唯一bean进行装配，找不到或是找到多个，都会抛出异常
  * 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配

---

### 4. 常见面试题

* Spring中 注入与Bean注册的区别，联系?
  * Bean注册说的是把Bean实例化并交由IoC容器进行管理，
  * 注入说的是Ioc容器把它管理的Bean按照需要（比如说按名称、按类型）赋值给其它Bean，
  * 当然注入也是由IoC容器进行管理的，实现自动装配的作用，从而降低高层级对低层级在编码上的依赖，注意是编码上的依赖，进而提高项目的可维护性

### 5. 综述

* Bean什么时候开始实例化
  * 在ApplicationContext作为Spring Bean的工厂类 的情况下：
    * 如果bean的scope是singleton的
      * lazy-init为false（默认是false）：ApplicationContext启动的时候就实例化该Bean
      * lazy-init为true（or 添加@Lazy注解）：在第一次使用该Bean的时候进行实例化 
    * 如果bean的scope是prototype的：在第一次使用该Bean的时候进行实例化 
  * 在使用BeanFactory作为Spring Bean的工厂类 的情况下：在第一次使用该Bean的时候进行实例化

### 6. 参考资料

* [什么是spring beans](https://zhidao.baidu.com/question/1962462546262906820.html)
* [POJO百度百科](https://baike.baidu.com/item/POJO/3311958?fr=aladdin#1)
* [Spring中Bean的生命周期及其扩展点](https://www.cnblogs.com/V1haoge/p/6106456.html)
* [Spring学习](https://baike.baidu.com/item/POJO/3311958?fr=aladdin#1)



