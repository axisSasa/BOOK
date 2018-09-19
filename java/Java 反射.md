## 反射

### 1. 定义及用途

* Java 中 Class类与java.lang.reflect类库一起对反射进行了支持
* 反射：动态的从内存加载一个指定的类，并获取该类中的所有的内容
  * 在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；
  * 对于任意一个对象，都能够调用它的任意一个方法和属性；
  * 这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制。
* 好处：可以随时新建添加类**增强了程序的扩展性**

### 2. 相对同类产品优势

* Java在运行时获取**类型信息**的方式有两种

  * 如果在**编译时已知**了的类的类型，则可使用**RTTI**来获取类型信息
  * 如果在**编译期类型不存在**，则可使用**反射**来获取类型信息
* 区别
* - RTTI，编译器在编译时打开和检查.class文件
  - 反射，运行时打开和检查.class文件
* RTTI

  * Runtime Type Information-运行时类型信息
  * 实现RTTI的三种方式/表现形式

    * 类型转换
    * 查询Class对象，获取对象的相关信息
    * 使用关键字**instanceof**,可判断一个对象是不是某个特定类型的实例

### 3. 核心知识点

#### 3.1.Class对象

##### 3.1.1. Class对象简介：

- class文件：一个类A编译后会产生一个以.class结尾的文件【A.class】，A.Class文件保存了该类的Class对象，所以该A.class/Class对象 对应着该类的定义信息。
- class对象的加载：当程序需要使用到A类的时候，会先检查A类的Class对象是否已经加载到虚拟机内存中，如果未加载，类加载器会根据类名A去查找 A.class文件，找到后进行类加载，加载后，A类的Class对象就可以被程序使用了。
- Class对象：保存了与类有关的信息，Java就是使用Class对象来执行RTTI的
  - 类型转换，**instanceof** 都依赖于Class对象
- `Class` 类的实例【Class对象】表示正在运行的 Java 应用程序中的类和接口。也就是jvm中有N多的实例每个类都有该Class对象。（包括基本数据类型）
  - 但一个类型只有一个，如数组若维度和类型都一样，便只会有一个Class对象

---

##### 3.1.2. 获取Class对象的引用的四种方式

- 没有A类的实例，可以通过 Class类的static方法-forName() 获取
- 有A类的实例，可通过，A 类实例对象的 getClass方法获取，该方法继承自Object
- 通过类字面常量获取 Class 对象 `A.class;`
- 如果有继承关系 还可以利用子类的Class对象去调用getSuperclass()方法来获取其父类的Class对象

---

##### 3.1.3. 获取类的Class对象 对 类的初始化 的影响

- 通过.class的方式获取类的Class对象 不会触发类的初始化
- 使用forName()可以设置参数决定是否触发类的初始化
- 类的初始化会在访问 静态方法 或者 非 final的静态域 时才会触发【类的初始化只有5种情况触发】
  - 访问 final的静态域 之所以不会触发初始化，是因为在编译期，类为 final静态域已经进入常量池了，实际在程序代码中访问的时候，final静态域的时候相当于访问常量
  - A类类常量 `final static String runtimeConstantPool = "runtimeConstantPool"`
    - 访问A.runtimeConstantPool 等价于访问 "runtimeConstantPool"

---

#### 3.2. 反射

##### 3.2.1 使用反射的基本步骤

* 获得Class对象
  * 就是获取到指定的名称的字节码文件对象
* 创建指定类对象
  * 实例化对象，获得类的属性、方法或构造函数
* 访问属性、调用方法、调用构造函数创建对象。

---

##### 3.2.2 通过反射创建Java对象实例

* 调用**空参数**的构造函数

  * 使用Class类中的newInstance()方法
  * 只能调用无参构造函数
  * 只能调用public类型的构造函数，private类型的构造函数，Class.newInstance无法调用，但可以通过调用setAccessible来完成私有构造函数的调用
  * Class的newInstance方法内部调用Constructor的newInstance方法
  * `Class.forName("").newInstance()`

* 调用**带参数**的构造函数

  * 先要获取指定参数的**构造器对象**

  * 然后通过构造器对象【Constructor类】的newInstance(实际参数) 进行对象的初始化

  * `Class.forName("").getConstructor().newInstance()`

  * `Class.forName("").getConstructor(new Class[]{String.class,int.class})).newInstance()`

---

##### 3.2.3 通过Class对象获取类中的方法，属性，构造函数

* 获取方法
  * `clazz.getMethods();` 获取所有的公有方法，包含继承和实现的方法
  * `clazz.getDeclaredMethods();` 获取所有方法，包含私有方法，但不包含继承的方法
* 获取属性
  * `getField()` `getFields()` `getDeclaredField` `getDeclaredFields` 
* 获取构造函数
  * Constructor<T> getConstructor(Class<?>... parameterTypes)
  * Constructor<?>[] getConstructors() 
* 执行方法
  * 方法.invoke(对象，其他参数)

### 4. 常见面试题

- Java反射机制的作用？
  - 反射可以在运行时判断任意一个对象所属的类；
  - 在运行时判断任意一个类所具有的成员变量和方法；
  - 在运行时调用任意一个对象的方法；
  - 在运行时构造任意一个类的对象；
  - 生成动态代理操作
- 什么是反射机制？
  - 反射就是动态加载对象，并对对象进行剖析
  - 在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法
  - 对于任意一个对象，都能够调用它的任意一个方法，
  - 这种动态获取信息以及动态调用对象方法的功能成为Java反射机制
- 哪里用到反射机制？
  - jdbc中，获取数据库驱动对象实例的代码，很多框架都用到反射机制，如hibernate、struts、spring。 
- 怎样提高反射效率？反射机制的优缺点？
  - 提高效率：
    - 首先保证反射 API 最小化，譬如尽量使用 getMethod 直接获取而不是 getMethods 遍历查找获取；
    - 其次需要多次动态创建一个类的实例时尽可能的使用缓存。
  - 优点：能够运行时动态获取类的实例，大大提高系统的灵活性和扩展性
  - 缺点：性能相对较低，此外使用反射相对来说不安全，破坏了类的封装性
- 如何反射获取内部类和调用内部类的方法&如何通过反射获取匿名内部类？
  - 非静态内部类：
    - 反射内部类名字需要使用$分隔：`外部类$内部类`
    - 内部类newInstance()的第一个参数必须是外部类实例的引用
  - 静态内部类：
    - 反射内部类名字需要使用$分隔：`外部类$内部类`
    - 内部类newInstance()和普通类一样，无特殊要求
  - 匿名内部类：
    - 通过外部类的getField拿到匿名内部类的对应属性
    - 再利用Field的get()方法返回 外部类实例/对象 的匿名内部类属性值

### 5. 综述

* 反射内部不多，日常自身使用不多
* 框架运用反射非常多，按道理说自动化程度越高得框架反射会运用得更多
* 因为Java调用都是基于类得，程序员自身不生产类，就得框架生产

### 6. 参考资料

* 《Java编程思想》
* [Java怎么在运行时知道对象和类的类型信息？知道了具体的类型信息有什么好处？](https://www.jianshu.com/p/c082a5251351)
* [深入理解Java反射](https://www.cnblogs.com/luoxn28/p/5686794.html)
* [Class.newInstance与Constructor.newInstance对比](https://blog.csdn.net/john1337/article/details/53500857)
* [new class[] = {int.class}](https://blog.csdn.net/u012572815/article/details/77196849)
* [java反射技术详解](https://www.cnblogs.com/makaruila/p/4852554.html)
* [关于反射的面试题](https://blog.csdn.net/laowang2915/article/details/73549373)
* [Java基础之反射相关面试题总结](https://blog.csdn.net/xiao6gui/article/details/81098100)

