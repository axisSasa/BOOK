## NIO

### 1. 定义及用途:

* IO 指的是计算机与外部世界或者一个程序与计算机的其余部分的之间的接口

* NIO用来替代传统IO API的库类，所以名为New I/O
*  NIO可以实现更高速的IO处理，NIO 将最耗时的 I/O 操作(即填充和提取缓冲区)转移回操作系统

### 2. 相对同类产品的优势【对比Java IO】

*  最重要的区别是**数据打包**和**传输的方式** ；

* Java IO的各种流是阻塞的；NIO 可以是非阻塞IO
* Java IO是面向流的I/O；Java NIO是面向缓存的I/O方法，数据读入缓冲器，使用通道进一步处理数据；通道与流的不同之处在于通道是双向的
* Java NIO选择器用于使用单个线程处理多个通道，减少了为IO处理建立的线程

### 3.  核心知识点

* 数据写入与读取：数据总是从缓冲区写入通道，从通道读取到缓冲区

#### 3.1. Channel

 * 用于在实体和字节缓冲区之间有效传输数据的介质；是对原 I/O 包中的流的模拟

 * 实现是使用**本地代码**执行实际工作-通道接口允许以便携和受控的方式访问低级I/O服务

 * 常用操作：检查通道是否关闭-isclose() 打开关闭通道-close()

* 通道之间传递数据：

   * FileChannel.transferTo()：用来从`FileChannel`到其他WritableByteChannel通道的数据传输
   * FileChannel.transferFrom(): 从ReadableByteChannel源通道到`FileChannel`的数据传输

 * FileChannel

   - 文件通道

   - 是抽线类；只能通过FileInputStream-FileOutputStream-RandomAccessFile调用`getChannel()`方法来创建对象【该方法实际会调用FileChannelImpl 的静态方法-open()，并在open()中实例化一个通道对象】

 * DatagramChannel

    * 数据报通道
    * 可以通过UDP(用户数据报协议)通过网络读取和写入数据
    * 是抽象类；使用DatagramChannel的静态方法-open()-close()来打开关闭

 * SocketChannel

    * 数据报通道

    * 可以通过TCP(传输控制协议)通过网络读取和写入数据

    * 是抽象类；使用SocketChannel的静态方法-open()-close()来打开关闭

    * 创建：

       * 打开一个`SocketChannel`，并在网络上与服务器连接`SocketChannel.open();  ` `connect`
       * 传入连接到达`ServerSocketChannel`时，可以创建它`ServerSocketChannel.accept();`

 * ServerSocketChannel

    * 允许用户监听传入的TCP连接-`accept()`；每个传入连接，都会为连接创建一个`SocketChannel`
    * 是抽象类；使用ServerSocketChannel的静态方法-open()-close()来打开关闭

---

#### 3.2. Buffer

 * 用于与NIO通道进行交互；包含一些要写入或者刚读出的数据
 * 重要的缓冲区组件：
    * 状态变量: 限制-limit，容量capacity和当前位置-positon
    * 访问方法 :`get()` 和 `put()` 方法直接访问缓冲区中的数据;ByteBuffer有类型化的 get() 和 put() 方法:getInt(),putInt()等专用于处理其他类型
 * 每个原始类型，都有一个缓冲区类型
 * 缓冲区分配-创建：
    * 使用静态方法 `allocate(字节数)` 来分配缓冲区
    * 使用静态方法`wrap(数组)`将一个现有的数组转换为缓冲区
    * 使用`slice()` 方法根据现有的缓冲区创建一种 *子缓冲区*-缓冲区分片 【与原始缓冲区共享同一个底层数据数组】
 * 读写切换： `clear()` 和 `flip()` 方法用于让缓冲区在读和写之间切换
 * 各类缓冲区：
    * 子缓冲区
    * 只读缓冲区：调用缓冲区的 `asReadOnlyBuffer()` 方法，将任何常规缓冲区转换为只读缓冲区
    * 直接缓冲区：*Java 虚拟机将尽最大努力直接对它执行本机 I/O 操作* ；可用内存映射文件创建直接缓冲区-MappedByteBuffer；若在直接缓冲区上执行修改相当于直接修改磁盘上的文件

- 基本类型缓冲区：CharBuffer，DoubleBuffer，IntBuffer，LongBuffer，ByteBuffer，ShortBuffer，FloatBuffer

---

#### 3.3. Selector

* 用于监视通道的对象，可以register对通道对象中发生IO事件的四种不同类型的**兴趣**：

   * 读 : SelectionKey.OP_READ （1）

    * 写 : SelectionKey.OP_WRITE （4）

    * 连接 : SelectionKey.OP_CONNECT （8）

    * 接收 : SelectionKey.OP_ACCEPT （16）1 << 4
    * 举例：int key = SelectionKey.OP_READ | SelectionKey.OP_WRITE ; //表示同时监听读写操作
    * register()方法会返回一个SelectionKey对象【interest集合，ready 集合，Channel，selector，附加的对象】

* 为了接收连接,要监听的每一个端口都需要有一个 `ServerSocketChannel`

   * 需要利用`bind(InetSocketAddress)`绑定端口
   * 与Selector一起使用时，Channel必须处于非阻塞模式下
   * 不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式

* select()方法:

   * 返回读事件已经就绪的那些通道

   * 阻塞唤醒：无参select()，若无通道就绪会一直阻塞，其他线程调用Selector.wakeup()

   * 关闭：用完Selector后调用其close()方法会关闭该Selector

---

#### 3.4. 文件锁定

   * 获取锁：调用一个**打开**的 `FileChannel` 上的 `lock()` 方法，返回一个FileLock对象
   * 释放锁：FileLock的release();
   * 确保文件锁定代码可移植性：
     * 只使用排它锁【要获取一个排它锁，必须以写方式打开文件`new RandomAccessFile( "X.txt", "rw" );`】

     * 将所有的锁**视为**劝告式的

---

#### 3.5. 管道

   * 用于在**两个线程**之间建立**单向数据连接**
   * 用于**按顺序**读取和写入数据；确保数据必须以写入管道的相同顺序读取
   * 槽通道和源通道：
     * 数据写入槽通道
     * 可以从源通道读取该数据
   * 创建管道:`Pipe pp = Pipe.open();`
   * 从管道读取数据:
     * 访问源通道:`Pipe.SourceChannel sc= pipe.source();`
     * 从源通道读取数据:`read()`
   * 写数据入管道：
     * 访问接收器通道：`Pipe.SinkChannel sc= pipe.sink();`

     * 将数据写入接收器通道: `write()`

---

#### 3.6 字符集Charset

   * 在给定的字符集和UNICODE之间的编码和解码
   * *Charset.displayName()* - 在Java NIO中用于返回规范名称的字符集
   * *Charset.encode()* - 在Java NIO中用于将UNICODE字符集的`charbuffer`编码为给定字符集的`CharBuffer`
   * *Charset.decode()* - 在Java NIO中用于将给定字符集的字符串解码为`Unicode`字符集的`CharBuffer`
   * `CharsetEncoder`
     * 用于将`Unicode`字符编码为字节序列
     * 创建：`Charset` `newDecoder()`
   * `CharsetDecoder`
     * 用于将数组或字节序列解码为`Unicode`字符

     * 创建：`Charset` `newEncoder()`

---

#### 3.7. 分散/聚集或向量I/O

   * 用于从通道读取和写入通道；`write()`函数将字节从一组缓冲区写入；`read()`函数将字节读取到一组缓冲区
   * 分散读取：读取数据到一个缓冲区数组中而不是读到单个缓冲区中-从通道读-通道依次填充每个缓冲区
   * 聚集写入：从缓冲区数组而不是从单个缓冲区写入数据-写到通道

### 4. 常见面试题

* 直接缓冲区 与 非直接缓冲区的 区别
* * 非直接缓冲区：通过allocate()分配缓冲区，将缓冲区建立在JVM的内存中
  * 直接缓冲区：通过allocateDirect()分配直接缓冲区，将缓冲区建立在物理内存中；通过 FileChannel 的 map() 方法【返回MappedByteBuffer】 将文件区域直接映射到内存中来创建
* Java NIO 适用情况举例
  * 会产生大量连接-高并发，但连接传输的数据量小-聊天服务器
* 用IO和NIO两种方式实现文件拷贝&说说两种方式处理文件的异同
  * 均为同步操作
  * IO是以流的方式处理数据的，面向流的IO一次一个字节地处理数据
  * NIO使用块IO的处理方式，每一个操作都在一步中读取或者写出一个数据块(缓存区)
* 为什么FileChannel 没有非阻塞模式
  * 源码：无设置非阻塞模式的方法

### 5.  综述：

* 同步阻塞相关概念：
  * 同步：使用同步IO时，Java自己处理IO读写。
  * 异步：使用异步IO时，Java将IO读写委托给OS处理，需要将数据缓冲区地址和大小传给OS，完成后OS通知Java处理（回调）。
  * 阻塞：使用阻塞IO时，Java调用会一直阻塞到读写完成才返回。
  * 非阻塞：使用非阻塞IO时，如果不能立马读写，Java调用会马上返回，当IO事件分发器通知可读写时在进行读写，不断循环直到读写完成。
* BIO：**同步并阻塞**，服务器的实现模式是一个连接一个线程，这样的模式很明显的一个缺陷是：由于客户端连接数与服务器线程数成正比关系，可能造成不必要的线程开销，严重的还将导致服务器内存溢出。当然，这种情况可以通过线程池机制改善，但并不能从本质上消除这个弊端。
* NIO：在JDK1.4以前，Java的IO模型一直是BIO，但从JDK1.4开始，JDK引入的新的IO模型NIO，它是**同步非阻塞**的。而服务器的实现模式是多个请求一个线程，即请求会注册到多路复用器Selector上，多路复用器轮询到连接有IO请求时才启动一个线程处理。
* AIO：JDK1.7发布了NIO2.0，这就是真正意义上的**异步非阻塞**，服务器的实 现模式为多个有效请求一个线程，客户端的IO请求都是由OS先完成再通知服务器应用去启动线程处理（回调）

### 6. 参考资料：

* [JavaNIO的总结](https://www.cnblogs.com/DreamDrive/p/7505474.html)
* [Java NIO教程](https://www.yiibai.com/java_nio/)
* [NIO 入门](https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html)

