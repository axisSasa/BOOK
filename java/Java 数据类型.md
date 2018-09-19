## Java 数据类型

### 1. 定义及用途

* 数据类型在计算机语言里面，是对内存位置的一个抽象表达方式，可以理解为针对内存的一种抽象的表达方式

* Java分为基本数据类型【8种】和引用类型
* 数据存储原理：
  * 基本数据类型：直接存储在内存中的内存栈上的，数据本身的值就是存储在栈空间里面
  * 引用类型：引用”是存储在有序的内存栈上的，而对象本身的值存储在内存堆上的

### 2. 相对同类产品优势

* Java是强类型语言；数据的类型都是在声明的时候确定的；相对于弱类型语言来说，更方便给数据分配内存大小；Java编译器也方便检查所有表达式和参数

### 3. 核心知识点

#### 3.1. 基本数据类型

##### 3.1.2. 整数：

* 用于整数有符号数字

* `byte`，`short`，`int`和`long`
* 占用内存：byte-1字节；short-2字节；int-4字节；long-8字节
* int-整形表示各种进制数：
  - 八进制数字格式
    - 以零开始并且至少有两个数字
    - `int octalInt = 026;`
  - 十六进制数字格式
    - 以`0x`或`0X`开头，它们必须至少包含一个十六进制数字
    - `int hexadecimalInt = 0X0123;`
  - 二进制数格式
    - 以`0b`或`0B`开头
    - `int binaryInt = 0b10101; `
* int 支持的最大值及最小值：`Integer.MAX_VALUE`和`Integer.MIN_VALUE`
* `long`类型的整数常数总是以大写`L`或小写`l`结尾
* long类型八进制，十六进制和二进制格式前缀与int无异

---

##### 3.1.2. 浮点数：

* 表示具有分数精度的数字

* `float`和`double`
* 占用内存：float-4字节；double-8字节
* 整型类型(`int`，`long`，`byte`，`short`和`char`)的值可以分配给`float`数据类型的变量，而不使用显式强制转换【将`int`和`long`赋值给`float`类型可能会导致精度损失 】
* 整型类型(`int`，`long`，`byte`，`short`，`char`)和`float`的值分配给`double`类型双精度数据类型的变量，而不使用显式强制转换
* 将`float`值/`double`值分配给任何整数数据类型必须强制转换
* Java浮点数默认类型为double，若要复制给float类型变量，须在数字后+f或F

---

##### 3.1.3. 字符：

* 表示字符集中的符号，如字母和数字

* 占用内存：2字节
* `16`位**无符号**Java基元数据类型-不能为负值
* 使用：字符需要用**单引号**包裹起来
* 应用地方：
  * 八个字符转义序列
  * `Unicode`转义序列

---

##### 3.1.4. 布尔：

* 表示 `true`/`false` 值的特殊类型

* 占用内存：
  * 单个boolean值变量编译过后,被int类型替换 - 为4字节
  * boolean的数组，编译过后，为byte数组，每个boolean值1个字节

---

##### 3.1.4. 下划线数字字面量

* 在数字字面量中的两位数字之间使用下划线
* 举例：int x2  = 2___014;   // 2014

---

#### 3.2. 基本数据类型对应的包装类

* char-Character;boolean-Boolean;int-Integer;short-Short;byte-Byte;long-Long;float-Float;double-Double
* 创建方法：
  - 使用构造函数
  - 使用静态方法`valueOf()`
* 继承自`Number`抽象类：
  * `Byte`, `Short`, `Integer`, `Long`, `Float` 和 `Double`
  * 可以调用xxxValue()的方法得到特定类型的值：`Integer.valueOf(100).byteValue()`
* 自动装箱和拆箱:
  * 自动装箱:原始数据类型到对应包装器对象
  * 自动拆箱：从包装器对象到其对应的原始数据类型



---

#### 3.3. 字符串

*  字符串比较
  * equals比较内存
  * == 比较内存地址【字符串字面量会进入字符串池，同时引用字符串池中相同字符串的变量自然内存地址相同】
  * compareTo比较`Unicode`值
* 修剪字符串：
  * `trim()`方法从字符串中删除所有前导和尾随空格和控制字符【Unicode值小于`\u0020`】
* 格式化输出：
* 正则表达式：
* 与Stringbuffer，StringBuilder差异
  * 可变与不可变：String一经创建不可再变，Stringbuffer,StringBuilder的对象是变量可更改
  * 拼接字符串执行效率：jvm会优化string的字符串拼接，所以较少的字符串拼接效率差异不会太大;但相对而言StringBuilder执行更快 
  * 线程安全：StringBuffer线程安全-其几乎所有方法都由synchronized关键字修饰，StringBuilder线程不安全

#### 3.4. 数组

* 数组赋值
  * 数字默认为0；布尔默认false;引用默认null
* 数组复制：
  * System.arraycopy()
  * 浅拷贝-clone()-克隆的数组将具有原始数组的真实副本。对于引用类型，只复制引用
* 可变长数组：
  * 数组声明后其长度就固定了，不能增加长度
  * 用`ArrayList`可创建可扩展数组【底层是数组】
  * `ArrayList`仅适用于对象，不适用于原始数据类型
  * `ArrayList`转数组：toArray()
* 数组参数：
  * 因为数组是一个对象，所以它的引用副本可传递给一个方法。
  * 如果方法只改变形参指向对实参无影响
  * 如果方法改变数组中的某个位置的值，则影响实参

### 4. 常见面试题

* 下面代码运行结果&说说原因
  * `short sh1 = 1;`  `sh1 = sh1 + 1;` `sh1 += 2;`
  * s1+1 运算时会自动提升表达式的类型为 int，所以将 int 赋予给 short 类型的变量 s1 会出现类型转换错误
  * `sh1 = (short) (sh1 + 1);`
  * += 是 java 语法规定的运算符，所以 java 编译器会对它进行转换特殊处理，不会报错
* java 中 char 类型变量能不能储存一个中文的汉字，为什么？
  * 能，char 类型变量是用来储存 Unicode 编码字符的，Unicode 字符集包含了汉字
  * 某个生僻汉字可能没有包含在 Unicode 编码字符集中，这种情况char 类型就不能存储
* java 的 Integer 和 int 有什么区别？
  * int 变量的默认值为 0，Integer 变量的默认值为 null
  * 一个是包装类一个是基本数据类型
* java 的 switch 语句能否作用在 byte 类型变量上，能否作用在 long 类型变量上，能否作用在 String 类型变量上？
  * 由于 byte 的存储范围小于 int，可以向 int 类型进行隐式转换，所以 switch 可以作用在 byte 类型变量上；
  * 由于 long 的存储范围大于 int，不能向 int 进行隐式转换，只能强制转换，所以 switch 不可以作用在 long 类型变量上
  * 对于 String 类型变量在 Java 1.7 版本之前不可以，1.7 版本之后是可以的。
* 能否在不进行强制转换的情况下将一个 double 值赋值给 long 类型的变量？
  * 不行，double 类型的范围比 long 类型更广，所以必须要进行强制转换。
* java 中 3\*0.1 == 0.3 将会返回什么？true 还是 false？
  * false，因为浮点数不能完全精确的表示出来，一般都会损失精度

### 5. 综述

* 数据类型是基础，基本大多语言特性都差不多

### 6.参考资料

* [Java数据类型教程](https://www.yiibai.com/java_data_type/)
* [【每日一题】Java 基本数据类型基础面试题](https://blog.csdn.net/bntx2jsqfehy7/article/details/78246071)







