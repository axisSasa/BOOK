## 注解

### 1. 定义及用途

* 在代码中通过 `@interface` 关键字进行定义
* 注解是一系列元数据，它提供数据用来解释程序代码，但是注解并非是所解释的代码本身的一部分
* 用途
  * 编译器可以利用注解来探测错误和警告信息 
  * 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。
  * 某些注解可以在程序运行的时候接受代码的提取

### 2. 相对同类产品优势

* 无

### 3. 核心知识点

#### 3.1. APT

* 中英文：Annotation Processing Tool；注解处理工具

* Annotation 不会自己生效，必须由开发者提供相应的代码来提取并处理 Annotation 信息。这些处理提取和处理 Annotation 的代码统称为 APT
* 通过反射机制获取被检查方法上的注解信息，然后根据注解元素的值进行特定的处理

---

#### 3.2. 元注解

* 一种基本的注解，是可以注解到注解上的注解
* 元注解介绍：
  * @Retention：声明注解的生存时间，可用取值：
    * RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视
    * RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中
    * RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们
  * @Documented：够将注解中的元素包含到 Javadoc 中
  * @Target：指定了注解运用的地方：
  * - ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
    - ElementType.CONSTRUCTOR 可以给构造方法进行注解
    - ElementType.FIELD 可以给属性进行注解
    - ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
    - ElementType.METHOD 可以给方法进行注解
    - ElementType.PACKAGE 可以给一个包进行注解
    - ElementType.PARAMETER 可以给一个方法内的参数进行注解
    - **ElementType.TYPE** 可以给一个类型进行注解，比如类、接口、枚举
  * @Inherited：如果一个超类被 @Inherited 注解过的注解进行注解的话，且它的子类没有被任何注解应用，那么这个子类就继承了超类的注解
  * @Repeatable ：可多次运用

---

#### 3.3. 注解的属性

* 注解的属性也叫做成员变量。注解只有成员变量，没有方法。
* 以“无形参的方法”形式来声明
* `int id() default -1;`:表明该属性的名字为id，该成员变量的类型为int,默认值为-1
* 属性的类型：必须是 8 种基本数据类型 + 类 + 接口 + 注解 及它们的数组
* 一个注解可以没有任何属性

---

#### 3.4. 注解的提取

* 检测是否用了注解：
  * 以通过 Class 对象的 isAnnotationPresent() 方法判断是否应用了某个注解
* 获取使用的注解：
  * getAnnotation or getAnnotations
* 通过属性方法的调用获取属性值
  * 注解对象.属性() `anno.id()`

### 4. 常见面试题

* 注解是什么？
  * 注解（Annotation），也叫元数据。一种代码级别的说明
  * 是代码中的特殊标记，且这些标记可以在编译、类加载、运行时被读取，并执行相对应的处理
* JDK内置了哪些注解：
  * @Overried是告诉编译器要检查该方法是实现父类的方法
  * @SuppressWarnings用于消除一些警告信息
  * @FunctionalInterface是JDK8中的注解，用来指定该接口是函数式接口
  * @SafeVarargs是JDK 7中的注解，主要目的是处理可变长参数中的泛型，此注解告诉编译器：在可变长参数中的泛型是类型安全的。
  * @Deprecated用于标记一些过时的代码
* 怎么自定义一个注解？
* 如何获取注解中的值？
* 工作中常接触到的注解？
* 注解的使用场景？
  * 生成文档
  * 替代配置文件
  * ORM框架标志表名，字段名
  * 对API进行权限控制
* 注解怎么实现对API进行权限控制？
  * 给需要拦截的接口加特定注解，将所有增加了特定注解的接口信息保存起来，让过滤器判断
  * 用AOP,以特定注解做切入点，使用before

### 5. 综述

* 注解在代码中常见，但较少手动编写，理解即可

### 6. 参考资料

* [秒懂，Java 注解 （Annotation）你可以这样学](https://blog.csdn.net/briblue/article/details/73824058/)
* [注解面试题-请了解下](https://blog.csdn.net/u010889990/article/details/80333100)